/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.concurrent.TimeUnit;		// Delay
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Until.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
public class Lift extends Subsystem {
  // create public doubles that are finals that you can use as arguments in IO
  // we are using 2 775 motors 1:625 gearbox for the arm
  // we are using 1 talonsrx motorcontroller for 
  private double encoderPos;
  private WPI_TalonSRX liftMotor;
  private WPI_VictorSPX liftMotor2;
  private Solenoid discBrake;
  private boolean brakeB;
  private int startMatch; // 
  private double goal;
  //ARM VALUES 
  public static int level1;//18 9/16 inches RECALIBRATE FREQUENTLY 3600
  public static int vertex_from_lvl1;
  public static int reverselimit;
  public static int forwardlimit;
  
  
  
  public Lift(){    
    brakeB=false;
    discBrake=new Solenoid(RobotMap.disbrake);
    liftMotor=new WPI_TalonSRX(RobotMap.liftMotor);
    liftMotor2=new WPI_VictorSPX(RobotMap.liftFollower);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    goal=liftMotor.getSelectedSensorPosition();
    liftMotor.selectProfileSlot(0, 0);
    liftMotor.configForwardSoftLimitEnable(true, Constants.kTimeoutMs);
    liftMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		liftMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		liftMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
    liftMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    liftMotor.configContinuousCurrentLimit(RobotMap.armMaxAmp);
    liftMotor.configPeakCurrentDuration(RobotMap.armMaxAmp);
    //mid is the only one changed
		liftMotor.config_kF(0, 0, 10);
		liftMotor.config_kP(0, 0.4, 10);
    liftMotor.config_kI(0, 0.0001, 10);
    liftMotor.config_IntegralZone(0, 400);
		liftMotor.config_kD(0, 0, 10);
		liftMotor.configMotionCruiseVelocity(3000, 10);
    liftMotor.configMotionAcceleration(3000, 10);
    liftMotor2.follow(liftMotor);
    //---------------values------------- 
    //3736, 5175 // differene is 1439 between lowest possible and lvl1   
  }
  
  public void moveTo(double input){
    goal=input;
  }

   public void liftTurnOn(){
    startMatch=liftMotor.getSelectedSensorPosition();
    level1=startMatch+1407;
    vertex_from_lvl1=level1+6235;
    reverselimit=level1-1205;
    forwardlimit=level1+13174;
    goal=level1;
    liftMotor.configReverseSoftLimitEnable(true);
    liftMotor.configReverseSoftLimitThreshold(startMatch);
    liftMotor.configForwardSoftLimitEnable(true);
    liftMotor.configForwardSoftLimitThreshold(startMatch+14510);//limit on the back of the arm

   }
    public int absoluteStart(){
     return startMatch;
   }
  /*
  public void moveTo(final LifterMode mode){
    goal=mode.getValue();
  }*/
  public void moveToPercent(double input){
    liftMotor.set(ControlMode.PercentOutput,input);
    liftMotor2.set(ControlMode.PercentOutput, input*RobotMap.reverse);
  }
  public void resetGoal(){
    goal=liftMotor.getSelectedSensorPosition();
  }
  public void engageBrake(){
    discBrake.set(true);    
    //make sure that no power is going to the arm currently
    // is based on the error of the PID loop

  }

  public void releaseBrake(){
    discBrake.set(false);
    //is dependent upon the position of the PID loop error
    //
  }
  public double getEncoderPos(){
    return liftMotor.getSelectedSensorPosition();// for now because it got angry
  }

  public void fineAdjustUp(){
    if(goal<vertex_from_lvl1){      
    goal+=150;
    }else if(goal>vertex_from_lvl1){
      goal-=150;
    }
  }

  public void fineAdjustDown(){
    if(goal<vertex_from_lvl1){      
      goal-=150;
      }else if(goal>vertex_from_lvl1){
        goal+=150;
      }
  }

  public boolean willBeForward(){
    //RobotMap.level1+5687,RobotMap.level1+7150
    if(goal>vertex_from_lvl1){
      return false;
    }else {
      return true;
    }
  }    

  public void periodic(){
    if(Math.abs(Robot.joy2.getRawAxis(1))>0.1 && !Robot.joy2.getRawButton(10)){
      if(goal>vertex_from_lvl1){
          liftMotor.set(ControlMode.PercentOutput, Robot.joy2.getRawAxis(1)*.2);
          resetGoal();
      }else if(goal<vertex_from_lvl1){
          liftMotor.set(ControlMode.PercentOutput, Robot.joy2.getRawAxis(1)*-.2); 
          resetGoal();     
    }
    }else{
      liftMotor.set(ControlMode.MotionMagic, goal);
      
    }
   // discBrake.set(brakeB);

  // System.out.println("Goal from subsystem " + goal);
   
   SmartDashboard.putNumber("Arm Value", getEncoderPos());
   SmartDashboard.putNumber("Arm Error",liftMotor.getClosedLoopError());
   SmartDashboard.putNumber("Arm Current:", liftMotor.getOutputCurrent());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
