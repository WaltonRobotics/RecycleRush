package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Grabber;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Releases tote
 */
public class Release extends Command {

	private Grabber grabber = Robot.grabber;
	private boolean isDone;
	private int timesExecuted;
    public Release() {
    	requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isDone = false;
    	timesExecuted =0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	grabber.setGrabberPower(-1);
    	
    	timesExecuted +=1;
		if (Math.abs(grabber.readCurrent()) > 1) 
			isDone = true;
		
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
