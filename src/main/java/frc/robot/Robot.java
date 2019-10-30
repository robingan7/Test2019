/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.OI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CameraServer;
import frc.robot.Until.Modes.LifterMode;
import frc.robot.commands.GrabHatch;
import frc.robot.commands.MoveArmTo;

public class Robot extends TimedRobot {
  public static OI m_oi;
  public static DriveBase drivebase;
  public static Manipulator manipulator;
  public static Lift lift;
  public static FultonExtractor fultonExtractor;
  public static Compressor compressor;
  public static Joystick joy;
  public static Joystick joy2;
  public static Joystick thrustLeft;
  public static Joystick thrustRight;
  public static int actualZero;
  public static LimeLight limelight;
  public static LimeLight2 limelight2;
  public static Arduino arduino;
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  @Override
  public void robotInit() {
    joy=new Joystick(0);
    joy2=new Joystick(1);
    thrustLeft=new Joystick(2);
    thrustRight=new Joystick(3);
    lift= new Lift();
    manipulator=new Manipulator();
    drivebase=new DriveBase();
    fultonExtractor= new FultonExtractor();
    Robot.lift.liftTurnOn();
    Robot.manipulator.manipulatorTurnOn();
    Robot.fultonExtractor.turnOn();    
    compressor=new Compressor();
    limelight=new LimeLight();
    limelight2=new LimeLight2();
    m_oi = new OI();
    arduino=new Arduino();
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);
    System.out.println("The absoulte start is the" + lift.absoluteStart());
    limelight.turnOffLed();
    limelight2.turnOffLed();    
    m_chooser.setDefaultOption("Default Auto", "right:rocket");
    m_chooser.addOption("right:ship", "right:ship");
    m_chooser.addOption("center:rocket", "center:rocket");
    m_chooser.addOption("center:ship", "center:ship");
    m_chooser.addOption("left:rocket", "left:rocket");
    m_chooser.addOption("left:ship", "left:ship");
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    limelight.turnOffLed();
    limelight2.turnOffLed();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    Robot.lift.resetGoal();
    Robot.manipulator.reset();
    Robot.fultonExtractor.reset();
    (new MoveArmTo(LifterMode.PickUp)).start();
    m_autoSelected = m_chooser.getSelected();
  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    
    // if(!Robot.joy.getRawButton(6)){
    //   Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(1), -1*Robot.joy.getRawAxis(2));
    // }
    
    // Robot.drivebase.linearTankDrive(thrustLeft.getRawAxis(1), thrustRight.getRawAxis(1));
    
    //System.out.println(Robot.joy.getRawAxis(1)+" "+ -1*Robot.joy.getRawAxis(2));
      
    Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(1), Robot.joy.getRawAxis(2));
    
   if(Robot.joy.getRawButton(8)){
    Robot.drivebase.shiftGears(true);
   }else{
    Robot.drivebase.shiftGears(false);
   }
     
  }

  @Override
  public void teleopInit() {
    Robot.lift.resetGoal();
    Robot.manipulator.reset();
    Robot.fultonExtractor.reset();
    limelight.turnOffLed();
    limelight2.turnOffLed();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    // arduino.writeMessage(new byte[]{0x12});
    // System.out.println(Robot.limelight2.getX());
    // if(!Robot.joy.getRawButton(6)){
    //   Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(1), -1*Robot.joy.getRawAxis(2));
    // }
    
    // Robot.drivebase.linearTankDrive(thrustLeft.getRawAxis(1), thrustRight.getRawAxis(1));
    Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(1)*0.8, Robot.joy.getRawAxis(2)*-0.7);
    SmartDashboard.putNumber("fwd bwd inputs: ", Robot.joy.getRawAxis(1));

  //   if(joy.getRawButton(2)){
  //     drivebase.liftDB(true); 
  //   }else{
  //     drivebase.liftDB(false);
  //   }
    
  //   if(Robot.joy.getRawButton(3)){
  //     drivebase.liftDBFront(true); 
  //   }else{
  //     drivebase.liftDBFront(false);
  //  }

   if(Robot.joy.getRawButton(8)){
    Robot.drivebase.shiftGears(true);
   }else{
    Robot.drivebase.shiftGears(false);
   }
     
}
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
  }
}
