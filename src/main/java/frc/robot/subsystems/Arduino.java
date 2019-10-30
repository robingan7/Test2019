/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SerialPort;
/**
 * Add your docs here.
 */
public class Arduino extends Subsystem {
  private SerialPort arduino_uno;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public Arduino(){
    try{
      arduino_uno=new SerialPort(9600, SerialPort.Port.kUSB);
      System.out.println("kUSB");
    }
    catch(Exception e){
      System.out.println("kUSB failed");
    }

    try{
      arduino_uno=new SerialPort(9600, SerialPort.Port.kUSB1);
      System.out.println("kUSB1");
    }
    catch(Exception e1){
      System.out.println("kUSB1 failed");
    }

    try{
      arduino_uno=new SerialPort(9600, SerialPort.Port.kUSB2);
      System.out.println("kUSB2");
    }
    catch(Exception e2){
      System.out.println("kUSB2 failed");
    }
  }

  public void writeMessage(byte[] stuff){
    arduino_uno.write(stuff, 1);
    System.out.println("Sending");
    if(arduino_uno.getBytesReceived()>0){
      System.out.println(arduino_uno.readString());
    }
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
