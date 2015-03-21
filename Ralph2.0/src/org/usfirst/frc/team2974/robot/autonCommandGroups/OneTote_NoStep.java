package org.usfirst.frc.team2974.robot.autonCommandGroups;

import org.usfirst.frc.team2974.robot.autonomous.GrabAndHold;
import org.usfirst.frc.team2974.robot.autonomous.InitElevator;
import org.usfirst.frc.team2974.robot.autonomous.MoveElevator;
import org.usfirst.frc.team2974.robot.autonomous.MoveLinear;
import org.usfirst.frc.team2974.robot.autonomous.Release;
import org.usfirst.frc.team2974.robot.autonomous.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OneTote_NoStep extends CommandGroup{
	public OneTote_NoStep() {
    	addSequential(new InitElevator());
    	addParallel(new GrabAndHold());
    	addSequential(new Wait(2));
    	addParallel(new MoveElevator(20));
    	addSequential(new Wait(2));
    	addSequential(new MoveLinear(1.6,1));
    	addSequential(new MoveElevator(6)); 
    	addParallel(new MoveElevator(1));
    	addSequential(new Release());
	}
}
