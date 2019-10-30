/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
    public static int dbFrontRight =11;
    public static int dbBackRight =13;
    public static int dbMidRight =15;

    public static int dbFrontLeft =12;
    public static int dbMidLeft =16;
    public static int dbBackLeft =14;
    
    public static int tiltMotor =17;
    //problem retrieving firmware
    public static int liftMotor = 18;
    public static int liftFollower = 19;
    public static int elevator = 20;
    public static int elevatorFollower = 21;
    public static int spider = 22;
    public static int spiderFollower = 23;
    public static int crawlMotor = 24;
    // 10, 11, 13
    public static int grabberF = 0;
    public static int grabberR = 7;
    public static int drivebaselifter=5;
    public static int drivebaselifterFront=3;
    public static int shifter=4;
    public static int disbrake=6;
    //-----Joystick1------
    public static int forward_backward=1;
    public static int turn=4;
    public static int liftup_drivebase;
    public static int turn180;
    public static int centervision;
    public static int driveduringvision;
    //-------Joystick2--------
    public static int pickup=3;
    public static int hatchlvl2=2;
    public static int hatchlvl3=1;
    public static int back=5;
    public static int backlvl2;
    public static int backlvl3;
    public static int turn_wrist=8;
    public static int gethatch=3;
    public static int hatchOpenClose=7;
    //version with snes controller
    //public static int extend;
    //public static int push=7;

    public static int mannualtilt=3;//axis
    public static int mannuallift=2;//axis

    public static int reverse=-1;


    //--------------amperage------------
    public static final int armMaxAmp = 14; 
    //------------arm differences with hatch on--------
    public static final int twoMinusOne = 3238,
      threeMinusOne = 5562,
      vertexMinusOne = 6235,
      threebMinusOne = 7285,
      twobMinusOne = 9992,
      onebMinusOne = 12608;

      //stow 2829
      //portal 4145
      //second 6860

    //highest recorded 11 amps
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  //-----lime light ------
  public static final double heightDifference=0;
  public static final double angle_difference_horizon=0;
  public static final double cameraHorizontalOffset=0;
  
}
