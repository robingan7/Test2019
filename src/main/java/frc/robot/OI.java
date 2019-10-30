/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Until.Modes.LifterMode;
import frc.robot.commands.*;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);
    public OI(){
      //----------Joystick--------------
      // Button turnSlow = new JoystickButton(Robot.joy, 5); //makes the robot turn slower for lining up
      // turnSlow.whileHeld(new SlowTurn());
      //----------Joystick2-------------
      Button lvl2=new JoystickButton(Robot.joy2, 2);
      lvl2.whileHeld(new MoveArmTo(LifterMode.Hatchlvl2));

      Button pickup=new JoystickButton(Robot.joy2, 3);
      pickup.whileHeld(new MoveArmTo(LifterMode.PickUp));

      Button lvl3=new JoystickButton(Robot.joy2, 1);
      lvl3.whileHeld(new MoveArmTo(LifterMode.Hatchlvl3));

      Button defense = new JoystickButton(Robot.joy, 1);
      defense.whileHeld(new Defense());

      Button hatch = new JoystickButton (Robot.joy2, 7);
      hatch.whileHeld(new GrabHatch());
      
      // POVButton fineAdjsutment = new POVButton(Robot.joy2, 0); //should the arm up a little bit once every time the POV Button is pressed
      // fineAdjsutment.whenPressed(new FineAdjustment());

      // Button visionturn=new JoystickButton(Robot.joy, 6);
      // visionturn.whileHeld(new VisionTurn());

      Button climbToThree=new JoystickButton(Robot.thrustLeft, 4);
      climbToThree.whileHeld(new ClimbToThree());
      
      Button retractElevator=new JoystickButton(Robot.thrustRight, 4);
      climbToThree.whileHeld(new RetractElevator());
      /*
      Button grab = new JoystickButton(Robot.joy,6);
      grab.whileHeld(new CloseOpenMani(true));
      grab.whenReleased(new CloseOpenMani(false));
*/
     
  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
}
