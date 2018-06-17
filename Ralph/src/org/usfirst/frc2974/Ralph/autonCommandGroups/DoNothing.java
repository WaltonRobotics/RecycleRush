package org.usfirst.frc2974.Ralph.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2974.Ralph.autonomous.InitElevator;

/**
 * This autonomous command is used when we want our autonomous to do absolutely nothing
 */
public class DoNothing extends CommandGroup {

	public DoNothing() {
		addSequential(new InitElevator());
	}
}