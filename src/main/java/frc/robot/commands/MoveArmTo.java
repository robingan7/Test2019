/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Until.Modes.LifterMode;


public class MoveArmTo extends Command {
  private LifterMode goal;
  private boolean isPressed;
  public MoveArmTo(LifterMode mode) {
    goal = mode;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.lift);
    requires(Robot.manipulator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isPressed = Robot.joy2.getRawButton(RobotMap.back);
    // Robot.lift.releaseBrake();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("execute...");
    System.out.println(Robot.lift.getEncoderPos());
    Robot.lift.moveTo(goal.getValue(isPressed));

}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("move arm finish");
    return false;
    //return Math.abs(Robot.lift.getEncoderPos()-goal.getValue(isPressed))<100;
    
    
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("move arm end");
     // engageDiscBrake on = new engageDiscBrake();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
      System.out.println("move arm interrupt");
  }
}
