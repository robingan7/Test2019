/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Until.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Manipulator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private boolean position;
  private WPI_TalonSRX tiltMotor;
  private double encoderPos=24;  //will change
  private DoubleSolenoid grabber;
  private boolean grabberB;
  private double goal;
  private int facefront;
  private int faceback;

  public Manipulator(){
    tiltMotor = new WPI_TalonSRX(RobotMap.tiltMotor);  
    grabber = new DoubleSolenoid(RobotMap.grabberF, RobotMap.grabberR);
    grabberB=false;
    
    goal=tiltMotor.getSelectedSensorPosition();
    
    tiltMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
		
    tiltMotor.setInverted(true);
    
    tiltMotor.configNominalOutputForward(0.1, Constants.kTimeoutMs);
		tiltMotor.configNominalOutputReverse(0.1, Constants.kTimeoutMs);
		tiltMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		tiltMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		tiltMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    
    tiltMotor.config_kF(0,0,10);
		tiltMotor.config_kP(0,0.7,10);
		tiltMotor.config_kI(0,0.0001,10);
    tiltMotor.config_kD(0,0,10);
    
		tiltMotor.configMotionCruiseVelocity(2000, Constants.kTimeoutMs);
		tiltMotor.configMotionAcceleration(3000, Constants.kTimeoutMs);
    
    tiltMotor.configContinuousCurrentLimit(20);
    tiltMotor.configPeakCurrentDuration(20);
  }

  //rotating the manipulator
  public void turnWrist(double g){
    goal=g;
  }

  public double getEncoderPos(){
    return encoderPos;

  }
  public void reset(){
    goal=tiltMotor.getSelectedSensorPosition();
  }
  
  public void manipulatorTurnOn(){
    facefront=tiltMotor.getSelectedSensorPosition();
    faceback=facefront+8376; //7528
    goal=facefront;
    // System.out.println(faceback);
    // tiltMotor.configReverseSoftLimitEnable(true);
    // tiltMotor.configReverseSoftLimitThreshold(facefront-50);
    // tiltMotor.configForwardSoftLimitEnable(true);
    // tiltMotor.configForwardSoftLimitThreshold(faceback+50);
  }

  public boolean isExtended(){
    return position;
  }

  public void tiltWristPercent(double value){
    tiltMotor.set(ControlMode.PercentOutput,value);
  }

  public boolean getGrabState(){
    return grabberB;
  }
  public double getWristState(){
    return tiltMotor.getSelectedSensorPosition();
  }
  public void grabHatch(boolean state){
    grabberB=state;
  }
  
  public void moveManipulator(){
    if(Robot.lift.willBeForward()){
      goal=facefront;
    }else{
      goal=faceback;
    }
  }
  
  public void periodic(){
    moveManipulator();
    
    if(Math.abs(Robot.joy2.getRawAxis(1))>0.1 && Robot.joy2.getRawButton(10)){
      if(Robot.lift.willBeForward()){
          tiltMotor.set(ControlMode.PercentOutput, Robot.joy2.getRawAxis(1)*.2*RobotMap.reverse);
          facefront=tiltMotor.getSelectedSensorPosition();
          faceback=facefront+8376;
        }
      }else{      
      tiltMotor.set(ControlMode.MotionMagic, goal);      
    }
    //System.out.println("Goal from subsystem " + goal);
    SmartDashboard.putNumber("Wrist Value", getWristState());
    SmartDashboard.putNumber("Wrist Current:", tiltMotor.getOutputCurrent());
    SmartDashboard.putNumber("Wrist Error: ", tiltMotor.getClosedLoopError());
    SmartDashboard.putNumber("Wrist start Value", facefront);

    if(grabberB){
      grabber.set(Value.kForward);
    }else{
      grabber.set(Value.kReverse);
    }
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
