/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.PIDSource; 
import edu.wpi.first.wpilibj.PIDSourceType; 
import edu.wpi.first.wpilibj.PIDOutput; 

public class VisionDrive extends PIDCommand {
  private final double TARGET_AREA = 16.0;
  private PIDController turnController; 

  private final PIDSource m_source = new PIDSource() {
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    @Override
    public PIDSourceType getPIDSourceType() {
      return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
      if(Robot.lift.getEncoderPos()>Robot.lift.vertex_from_lvl1)
        return Robot.limelight.getX();
      else
        return Robot.limelight2.getX();
    }
  };
  private double steering_adjust=0;
  private final PIDOutput m_output = new PIDOutput(){
  
    @Override
    public void pidWrite(double output) {
      steering_adjust=output;
    }
  };
  
  public VisionDrive() {
    super("VisionDrive",0.22,0.0,0.0);
    turnController=new PIDController(0.23, 0.001, 0.0,m_source,m_output);
    setInputRange(0.0,70.0);
    turnController.setInputRange(-20.5, -20.5);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivebase);
    requires(Robot.limelight);
    requires(Robot.limelight2);
    requires(Robot.lift);
    //requires(Robot.arduino);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Start Vision Driving");
    turnController.enable();
      if(Robot.lift.getEncoderPos()>Robot.lift.vertex_from_lvl1)
        Robot.limelight.turnOnLed();
      else
        Robot.limelight2.turnOnLed();
  }

  private double map(double x, double in_min,double in_max,double out_min,double out_max){
    return (x-in_min)*(out_max-out_min)/(in_max-in_min)+out_min;
  }

  protected double returnPIDInput() {
    if(Robot.lift.getEncoderPos()>Robot.lift.vertex_from_lvl1)
        return TARGET_AREA-Robot.limelight.getA();
    else
        return Robot.limelight2.getA()-TARGET_AREA;    
  }

  protected void usePIDOutput(double output) {
    
    System.out.println(steering_adjust);
    //i want to change things from arcadeDrive to tank drive, might get one stick and adds to both sides
    //Got it --Robin
    Robot.drivebase.visionDrive(-output*0.65+Robot.joy.getRawAxis(1), Robot.joy.getRawAxis(2)+steering_adjust);
    //Robot.drivebase.visionDrive(output,0);
  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.arduino.write(3,3,3);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //Robot.arduino.write(1,1,1);
    return Robot.limelight.getA()>=TARGET_AREA;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    turnController.disable();
    //Robot.arduino.write(1,1,1);
    Robot.limelight.turnOffLed();
    Robot.limelight2.turnOffLed();
    System.out.println("Target Found");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    turnController.disable();
    //Robot.arduino.write(1,1,1);
    System.out.println("Vision interrupt");
   Robot.limelight.turnOffLed();
   Robot.limelight2.turnOffLed();
  }
}
