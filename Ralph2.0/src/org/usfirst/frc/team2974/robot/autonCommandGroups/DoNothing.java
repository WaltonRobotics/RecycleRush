package org.usfirst.frc.team2974.robot.autonCommandGroups;

import org.usfirst.frc.team2974.robot.autonomous.InitElevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This autonomous command is used when we want our autonomous to do absolutely nothing
 */
public class DoNothing extends CommandGroup {
    public  DoNothing() {
    	addSequential(new InitElevator());
    }
}