/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
/**
 * Add your docs here.
 */
public class LimeLight extends Subsystem {
  private NetworkTable table;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public LimeLight(){
    table = NetworkTableInstance.getDefault().getTable("limelight-back");
    //table.getEntry("pipeline").setNumber(1);
  }

  public void turnOffLed(){
    table.getEntry("ledMode").setNumber(1);
  }
  public void turnOnLed(){
    table.getEntry("ledMode").setNumber(0);
  }
  public double getX(){
    return table.getEntry("tx").getDouble(0.0);
  }
  public double getY(){
    return table.getEntry("ty").getDouble(0.0);
  }
  public double getA(){
    return table.getEntry("ta").getDouble(0.0);
  }
  public double getV(){
    return table.getEntry("tv").getDouble(0.0);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
