package org.usfirst.frc.team2974.robot.autonCommandGroups;

import org.usfirst.frc.team2974.robot.autonomous.InitElevator;
import org.usfirst.frc.team2974.robot.autonomous.MoveLinear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Forward_NoStep extends CommandGroup {
    
    public  Forward_NoStep() {
    	addSequential(new InitElevator());
    	addSequential(new MoveLinear(1.6,1));

    }
}
