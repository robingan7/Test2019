/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static WPI_TalonSRX frontRight, frontLeft;
  public static WPI_VictorSPX backRight, backLeft, midRight, midLeft;
  //private double leftOutput, rightOutput;
  //private Solenoid shifterSolenoid;
  private Solenoid dbLifter;
  private Solenoid shifter;
  private Solenoid dblifterFront;
  private boolean solenoidPos;
  private boolean solenoidPosFront;
  private int direction=1;
  private double turnMultiplier=1;
  private boolean gearB;
  //private double goal;
  private DifferentialDrive m_Drive;
  
   public DriveBase(){
     dbLifter= new Solenoid(RobotMap.drivebaselifter);
     dblifterFront= new Solenoid(RobotMap.drivebaselifterFront);
     shifter = new Solenoid(4);
     solenoidPos=false;
     
    //
    frontRight = new WPI_TalonSRX(RobotMap.dbFrontRight);
    midRight = new WPI_VictorSPX(RobotMap.dbMidRight);
    backRight = new WPI_VictorSPX(RobotMap.dbBackRight);
      midRight.follow(frontRight);
      backRight.follow(frontRight);
    
    frontLeft = new WPI_TalonSRX(RobotMap.dbFrontLeft);
    midLeft = new WPI_VictorSPX(RobotMap.dbMidLeft);  
    backLeft = new WPI_VictorSPX(RobotMap.dbBackLeft);
      midLeft.follow(frontLeft);
      backLeft.follow(frontLeft);
    
      SpeedControllerGroup m_Right = new SpeedControllerGroup(frontRight, midRight, backRight);
      SpeedControllerGroup m_Left = new SpeedControllerGroup(frontLeft, midLeft, backLeft);

      m_Drive = new DifferentialDrive(m_Left, m_Right);
      
    // frontLeft.configContinuousCurrentLimit(RobotMap.armMaxAmp);
    // frontLeft.configPeakCurrentDuration(RobotMap.armMaxAmp); also for all the other ones
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
       frontLeft.setNeutralMode(NeutralMode.Brake);
       frontRight.setNeutralMode(NeutralMode.Brake);

    frontLeft.configOpenloopRamp(.5);
    frontRight.configOpenloopRamp(.5);

  }

  public void arcadeDrive(double forward, double rotate){
    if(Robot.joy.getRawAxis(1)> 0){
      forward=forward*forward;   
    }else if (Robot.joy.getRawAxis(1)<0){
      forward=forward*forward*-1;
    }
    m_Drive.arcadeDrive(forward*direction, turnMultiplier*0.67*rotate);
  }

  public void visionDrive(double forward, double rotate){
    m_Drive.arcadeDrive(forward*direction, turnMultiplier*0.65*rotate);
  }

  public void tankDrive(double left, double right){
    m_Drive.tankDrive(left, right);
    //scaling, first 60% of joystick values takes care of first 40% of power, then the rest takes care of 40->100
  } 

  public void linearTankDrive(double left, double right){
    if(!Robot.thrustLeft.getRawButton(2)){
      if(!torqueMode()){
        percentLeftDrive(left);
        percentRightDrive(right*RobotMap.reverse);
      }

      if (isTurning()){
        percentLeftDrive(left*0.5);
        percentRightDrive(right*0.5*RobotMap.reverse);
        System.out.println("currently turning");
      }
      if(torqueMode()){
        percentLeftDrive(left);
        percentRightDrive(right*RobotMap.reverse);
      }
    }
  }

  public void percentLeftDrive(double input){
    frontLeft.set(ControlMode.PercentOutput, input);
    midLeft.set(ControlMode.PercentOutput, input);
    backLeft.set(ControlMode.PercentOutput, input);
  }
  
  public void percentRightDrive(double input){
    frontRight.set(ControlMode.PercentOutput, input);
    midRight.set(ControlMode.PercentOutput, input);
    backRight.set(ControlMode.PercentOutput, input);
  }
  
  public boolean isTurning(){
    if(Math.abs((Robot.thrustLeft.getRawAxis(1))-(Robot.thrustRight.getRawAxis(1)))>0.6){
      return true;
    } else{
      return false;
    }
  }
  
  public void switchDirection(){
    direction = direction*-1;
  }

  public void slowTurn(){
    turnMultiplier=1;
  }

  public void driveDistance(double g){
  }

  public void liftDB(boolean state){
    solenoidPos = state;
  }

  public void liftDBFront(boolean state){
    solenoidPosFront = state;
  }
  public boolean getLiftDB(){
    return solenoidPos;
  }
  public void shiftGears(boolean s){
    gearB=s;
  }
  
  public boolean torqueMode(){
    if(gearB){
      return true;
    }else {
      return false;
    }
  }
  
  @Override
  public void periodic() {
    dbLifter.set(solenoidPos);
    dblifterFront.set(solenoidPosFront);
    shifter.set(gearB);
    //super.periodic();
    SmartDashboard.putNumber("Drivebase Amps", frontRight.getOutputCurrent());
    SmartDashboard.putNumber("DB Encoder left", frontLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("DB Encoder right", frontRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("DB velocity", frontRight.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Joystick errors", Math.abs((Robot.thrustLeft.getRawAxis(1))-(Robot.thrustRight.getRawAxis(1))));
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new MySpecialCommand());
  }
}
