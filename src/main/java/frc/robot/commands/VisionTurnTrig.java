package frc.robot.commands;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.RobotMap;
import frc.robot.RobotMap;

public class VisionTurnTrig extends PIDCommand {
  public VisionTurnTrig() {
    super("VisionTurn", 0.23, 0.001, 0.0);
    //p for white =.23
    //kI for white =.001
    setInputRange(-27.0, 27.0);

    requires(Robot.drivebase);
    requires(Robot.limelight);
    requires(Robot.limelight2);
    requires(Robot.lift);
    requires(Robot.arduino);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {   
      System.out.println("Start turning");
      if(Robot.lift.getEncoderPos()>Robot.lift.vertex_from_lvl1)
        Robot.limelight.turnOnLed();
      else
        Robot.limelight2.turnOnLed();
  }

  protected double returnPIDInput() {
    
    if(Robot.lift.getEncoderPos()>Robot.lift.vertex_from_lvl1){
      double distance=RobotMap.heightDifference/ Math.tan(RobotMap.angle_difference_horizon+Robot.limelight.getY());
      double horizontalAngle = Math.PI / 2 - Math.toRadians(Robot.limelight.getX());
      double f = Math.sqrt(distance * distance + Math.pow(RobotMap.cameraHorizontalOffset, 2) - 2 * distance * RobotMap.cameraHorizontalOffset * Math.cos(horizontalAngle));
      double c = Math.asin(RobotMap.cameraHorizontalOffset * Math.sin(horizontalAngle) / f);
      double b = Math.PI - horizontalAngle - c;
      double angle = (Math.PI / 2 - b) * 180.0 / Math.PI;
      
      return angle;
    }
    else{
      double distance=RobotMap.heightDifference/ Math.tan(RobotMap.angle_difference_horizon+Robot.limelight2.getY());
      double horizontalAngle = Math.PI / 2 - Math.toRadians(Robot.limelight.getX());
      double f = Math.sqrt(distance * distance + Math.pow(RobotMap.cameraHorizontalOffset, 2) - 2 * distance * RobotMap.cameraHorizontalOffset * Math.cos(horizontalAngle));
      double c = Math.asin(RobotMap.cameraHorizontalOffset * Math.sin(horizontalAngle) / f);
      double b = Math.PI - horizontalAngle - c;
      double angle = (Math.PI / 2 - b) * 180.0 / Math.PI;
      
      return angle;
    }    
  }

  protected void usePIDOutput(double output) {
    System.out.println(output);
    //i want to change things from arcadeDrive to tank drive, might get one stick and adds to both sides
    Robot.drivebase.visionDrive(Robot.joy.getRawAxis(1), output+Robot.joy.getRawAxis(2));
  }

  @Override
  protected void execute() {
    System.out.println("turn exe");
    //Robot.arduino.write(3,3,3);
  }

  //Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //Robot.arduino.write(1,1,1);
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.limelight.turnOffLed();
    Robot.limelight2.turnOffLed();
    //Robot.arduino.write(1,1,1);
    System.out.println("turn Found");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("Turn interrupt"); 
    Robot.limelight.turnOffLed();
    Robot.limelight2.turnOffLed();
    //Robot.arduino.write(1,1,1);
  }
}
