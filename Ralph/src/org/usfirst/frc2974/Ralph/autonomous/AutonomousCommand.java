package org.usfirst.frc2974.Ralph.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2974.Ralph.commands.*;
/**
 *
 */
public class AutonomousCommand extends CommandGroup {
    
    public  AutonomousCommand() {

    	addSequential(new InitElevator());
    	addParallel(new GrabAndHold());
    	addSequential(new Wait(2));
    	addParallel(new MoveElevator(.2));
    	addSequential(new Wait(2));
    	addSequential(new MoveLinear(2,2));
    	addSequential(new MoveElevator(6)); 
    	addParallel(new MoveElevator(0));
    	addSequential(new Release());
    		
    	
    }
}
