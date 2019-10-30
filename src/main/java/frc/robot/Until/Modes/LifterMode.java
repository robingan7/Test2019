package frc.robot.Until.Modes;
import frc.robot.RobotMap;
import frc.robot.Robot;
public enum LifterMode{
    
    PickUp(new int[] {Robot.lift.level1,Robot.lift.level1+12550}),//0,  +12556
    Hatchlvl2(new int[]{Robot.lift.level1+2468,Robot.lift.level1+10146}), //+2594 , +10012
    Hatchlvl3(new int[]{Robot.lift.level1+5518,Robot.lift.level1+7250}),
    Defense(new int[]{Robot.lift.level1 - 1700,Robot.lift.level1 - 1700}); //+5687, +7150//  CO 4-12-19, 7189
   


    //   twoMinusOne = 3238,
    //   threeMinusOne = 5562,
    //   vertexMinusOne = 6235,
    //   threebMinusOne = 7285,
    //   twobMinusOne = 9992,
    //   onebMinusOne = 12608;

    //   //stow 2829
    //   //portal 4145
    //   //second 6860

    
    private int values[];
    private int current;
    LifterMode(int output[]){
        values=output;
    }
    public double getValue(boolean b){
        if(!b){
            current=values[0];
            return values[0];
        }
        else{
            current=values[1];
            return values[1];  
        }  
    }
    public double getValue(){
        return current;
    }
}