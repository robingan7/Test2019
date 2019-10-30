/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Until.Constants;

/**
 * Add your docs here.
 */
public class FultonExtractor extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  //i have not yet set the acutal PID values, those need to be changed, by now there will be a start and turn on thing
  WPI_TalonSRX elevator, spider;
  WPI_VictorSPX elevatorFollower, crawlMotor;
  private int elevatorGoal, spiderGoal;
  private int elevatorStartMatch, spiderStartMatch;
  private int elevatorPowerConstant;
  private double spiderPowerConstant;
  DigitalInput elevatorForwardLimit, elevatorReverseLimit, spiderLimit;
  public FultonExtractor(){
    elevatorForwardLimit = new DigitalInput(0);
    elevatorReverseLimit = new DigitalInput(1);
    spiderLimit = new DigitalInput(2);
    crawlMotor = new WPI_VictorSPX(RobotMap.crawlMotor);
    elevator = new WPI_TalonSRX(RobotMap.elevator);
    elevatorFollower = new WPI_VictorSPX(RobotMap.elevatorFollower);
      elevatorFollower.follow(elevator);
      elevatorFollower.setInverted(true);
    spider = new WPI_TalonSRX(RobotMap.spider);
    
    elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    elevatorGoal=elevator.getSelectedSensorPosition();
    elevator.selectProfileSlot(0, 0);
    elevator.configForwardSoftLimitEnable(true, Constants.kTimeoutMs);
    elevator.configNominalOutputForward(0, Constants.kTimeoutMs);
		elevator.configNominalOutputReverse(0, Constants.kTimeoutMs);
		elevator.configPeakOutputForward(1, Constants.kTimeoutMs);
    elevator.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    elevator.configContinuousCurrentLimit(RobotMap.armMaxAmp);
    elevator.configPeakCurrentDuration(RobotMap.armMaxAmp);
    //DO NOT USE THESE VALUES
		elevator.config_kF(0, 0, 10);
		elevator.config_kP(0, 0.2, 10);
    elevator.config_kI(0, 0, 10);
    elevator.config_IntegralZone(0, 400);
		elevator.config_kD(0, 0, 10);
		elevator.configMotionCruiseVelocity(1000, 10);
    elevator.configMotionAcceleration(3000, 10);
    elevatorFollower.follow(elevator);
    
    spider.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    spiderGoal=spider.getSelectedSensorPosition();
    spider.selectProfileSlot(0, 0);
    spider.configForwardSoftLimitEnable(true, Constants.kTimeoutMs);
    spider.configNominalOutputForward(0, Constants.kTimeoutMs);
		spider.configNominalOutputReverse(0, Constants.kTimeoutMs);
		spider.configPeakOutputForward(1, Constants.kTimeoutMs);
    spider.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    spider.configContinuousCurrentLimit(RobotMap.armMaxAmp);
    spider.configPeakCurrentDuration(RobotMap.armMaxAmp);
    //DO NOT USE THESE VALUES
		spider.config_kF(0, 0, 10);
		spider.config_kP(0, 0.2, 10);
    spider.config_kI(0, 0, 10);
    spider.config_IntegralZone(0, 400);
		spider.config_kD(0, 0, 10);
		spider.configMotionCruiseVelocity(1000, 10);
    spider.configMotionAcceleration(3000, 10);

    elevatorPowerConstant =1;
    spiderPowerConstant =0.75;
    elevator.configContinuousCurrentLimit(30);
    elevator.configContinuousCurrentLimit(30, 10);
    
    spider.configContinuousCurrentLimit(30);
    spider.configContinuousCurrentLimit(30, 10);
  }

  public void turnOn(){
    elevatorStartMatch=elevator.getSelectedSensorPosition();
    spiderStartMatch=spider.getSelectedSensorPosition();
  }

  public void reset(){
    elevatorGoal=elevator.getSelectedSensorPosition();
    spiderGoal=spider.getSelectedSensorPosition();
  }

  public void spiderStart(){
    spider.set(ControlMode.MotionMagic, spiderGoal);
  }

  public void elevatorStart(){
    elevator.set(ControlMode.MotionMagic, elevatorGoal);
  }

  public void pauseSpider(){
    spider.set(ControlMode.MotionMagic, spider.getSelectedSensorPosition());
  }

  public void pauseElevator(){
    elevator.set(ControlMode.MotionMagic, elevator.getSelectedSensorPosition());
  }

  public void moveUntilDone(){
    if(elevatorForwardLimit.get()==false && spiderLimit.get()==false){
      elevator.set(ControlMode.PercentOutput, elevatorPowerConstant);
      spider.set(ControlMode.PercentOutput, spiderPowerConstant);
    }else{
      pauseElevator();
      pauseSpider();
    }
  }
  
  public void retractElevator(){
    if(elevatorReverseLimit.get()==false){
      elevator.set(ControlMode.PercentOutput, 1);
      elevatorFollower.set(ControlMode.PercentOutput, 1*RobotMap.reverse);
    }else{      
      elevator.set(ControlMode.PercentOutput, 0);
      elevatorFollower.set(ControlMode.PercentOutput, 0);
    }
  }
  public void drive(double input){
    crawlMotor.set(ControlMode.PercentOutput, input);
  }

  public boolean getElevatorLimit(){
    return elevatorForwardLimit.get();
  }

  public boolean getSpiderLimit(){
    return spiderLimit.get();
  }

  public boolean getElevatorReverseLimit(){
    return elevatorReverseLimit.get();
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
