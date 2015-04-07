package org.usfirst.frc2974.Ralph.autonCommandGroups;

import org.usfirst.frc2974.Ralph.autonomous.InitElevator;
import org.usfirst.frc2974.Ralph.autonomous.MoveLinear;
import org.usfirst.frc2974.Ralph.autonomous.Turn;

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
