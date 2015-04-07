package org.usfirst.frc2974.Ralph.autonCommandGroups;

import org.usfirst.frc2974.Ralph.autonomous.InitElevator;
import org.usfirst.frc2974.Ralph.autonomous.MoveLinear;

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
