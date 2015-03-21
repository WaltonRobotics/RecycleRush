package org.usfirst.frc.team2974.robot.autonCommandGroups;

import org.usfirst.frc.team2974.robot.autonomous.InitElevator;
import org.usfirst.frc.team2974.robot.autonomous.MoveLinear;
import org.usfirst.frc.team2974.robot.autonomous.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Forward_Step extends CommandGroup {
    
    public  Forward_Step() {
    	addSequential(new InitElevator());
    	addSequential(new MoveLinear(6,.4));
    	addSequential(new Turn(.75,-1));

    }
}
