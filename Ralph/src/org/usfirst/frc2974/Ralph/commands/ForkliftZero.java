package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Sets default position for Forklift (at beginning of match)
 *Fully lowers elevator
 *sets encoder to 0
 *fully opens claw(not implemented yet)
 *switches elevator to position mode
 *raises elevator to neutral position(just a little above the ground for driving)
 */
public class ForkliftZero extends Command {

	private Forklift forklift;
	private boolean finished;
	
    public ForkliftZero() 
    {        
    	forklift = Robot.forklift;
    	requires(forklift); 
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	finished = false;
    	forklift.setPowerMode();
    	forklift.raiseLowVar(-0.1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(forklift.isZero())
    	{
    		forklift.raiseLowVar(0.0);
    		forklift.resetEncoder();
    		forklift.setPositionMode();
    		forklift.raiseToPosition(forklift.NEUTRAL_POS);
    		finished = true; 
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
