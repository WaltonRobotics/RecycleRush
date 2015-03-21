package org.usfirst.frc.team2974.robot.autonCommandGroups;

import org.usfirst.frc.team2974.robot.autonomous.GrabAndHold;
import org.usfirst.frc.team2974.robot.autonomous.InitElevator;
import org.usfirst.frc.team2974.robot.autonomous.MoveElevator;
import org.usfirst.frc.team2974.robot.autonomous.MoveLinear_Dynamic;
import org.usfirst.frc.team2974.robot.autonomous.Release;
import org.usfirst.frc.team2974.robot.autonomous.Turn;
import org.usfirst.frc.team2974.robot.autonomous.Wait;

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
