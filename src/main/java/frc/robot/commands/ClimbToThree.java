/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.FultonExtractor;

public class ClimbToThree extends Command {
  public ClimbToThree() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.fultonExtractor);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.fultonExtractor.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.fultonExtractor.moveUntilDone();
    Robot.fultonExtractor.drive(Robot.thrustLeft.getRawAxis(1));
    System.out.println("lifting right now");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.fultonExtractor.getSpiderLimit() && Robot.fultonExtractor.getElevatorLimit();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(Robot.thrustLeft.getRawButton(1)){
    Robot.fultonExtractor.pauseElevator();
    Robot.fultonExtractor.pauseSpider();
    System.out.println("paused Fulton Extractor");
    }
    System.out.println("Slowly going down");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
