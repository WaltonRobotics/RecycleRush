package org.usfirst.frc2974.Ralph.autonCommandGroups;

import org.usfirst.frc2974.Ralph.autonomous.GrabAndHold;
import org.usfirst.frc2974.Ralph.autonomous.InitElevator;
import org.usfirst.frc2974.Ralph.autonomous.MoveElevator;
import org.usfirst.frc2974.Ralph.autonomous.MoveLinear_Dynamic;
import org.usfirst.frc2974.Ralph.autonomous.Release;
import org.usfirst.frc2974.Ralph.autonomous.Turn;
import org.usfirst.frc2974.Ralph.autonomous.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Forward_Dynamic extends CommandGroup {
    
    public  Forward_Dynamic() {
    	addSequential(new InitElevator());
    	addParallel(new GrabAndHold());
    	addSequential(new Wait(2));
    	addParallel(new MoveElevator(20));
    	addSequential(new Wait(2));
    	addSequential(new MoveLinear_Dynamic(1.5,.4));
    	addSequential(new Turn(.75,-1));
    	addSequential(new MoveElevator(6)); 
    	addParallel(new MoveElevator(1));
    	addSequential(new Release());
    }
}
