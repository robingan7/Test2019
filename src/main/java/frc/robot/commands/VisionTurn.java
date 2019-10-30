package frc.robot.commands;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.RobotMap;

public class VisionTurn extends PIDCommand {
  public VisionTurn() {
    super("VisionTurn", 0.23, 0.0001, 0.0);
    //p for white =.23
    //kI for white =.001
    setInputRange(-27.0, 27.0);

    requires(Robot.drivebase);
    requires(Robot.limelight);
    //requires(Robot.limelight2);
    requires(Robot.lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {   

    Robot.limelight.turnOnLed();
    Robot.limelight2.turnOnLed();
      System.out.println("Start Ajusting");
      if(Robot.lift.getEncoderPos()>Robot.lift.vertex_from_lvl1)
        Robot.limelight.turnOnLed();
      else
        Robot.limelight2.turnOnLed();
  }

  protected double returnPIDInput() {

    return Robot.limelight2.getX();  
    // if(Robot.lift.getEncoderPos()>Robot.lift.vertex_from_lvl1)
    //     return Robot.limelight.getX();
    // else
    //   return Robot.limelight2.getX();    
  }

  protected void usePIDOutput(double output) {
    System.out.println(output);
    //i want to change things from arcadeDrive to tank drive, might get one stick and adds to both sides
     Robot.drivebase.visionDrive(Robot.joy.getRawAxis(1), output); //(output)
  }

  @Override
  protected void execute() {

  }

  //Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.joy.setRumble(RumbleType.kRightRumble, 0);
    Robot.joy.setRumble(RumbleType.kLeftRumble, 0);
    Robot.limelight.turnOffLed();
    Robot.limelight2.turnOffLed();
    System.out.println("Target Found");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.joy.setRumble(RumbleType.kRightRumble, 0);
    Robot.joy.setRumble(RumbleType.kLeftRumble, 0);
    System.out.println("Vision interrupt"); 
   Robot.limelight.turnOffLed();
  Robot.limelight2.turnOffLed();
  }
}
