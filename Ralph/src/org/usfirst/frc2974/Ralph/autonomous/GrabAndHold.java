package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Grabber;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Grabs and hold the tote
 */
public class GrabAndHold extends Command {

	private Grabber grabber = Robot.grabber;
	
    public GrabAndHold() {
    	requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double move = 1;
    	
		if (Math.abs(grabber.readCurrent()) > 1) 
			move = .25;
		
		grabber.setGrabberPower(-move);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
