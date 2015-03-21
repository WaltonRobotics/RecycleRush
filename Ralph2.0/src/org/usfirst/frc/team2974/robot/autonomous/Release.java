package org.usfirst.frc.team2974.robot.autonomous;

import org.usfirst.frc.team2974.robot.Robot;
import org.usfirst.frc.team2974.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Releases tote
 */
public class Release extends Command {

	private Grabber grabber = Robot.grabber;
	private boolean isDone;
	
    public Release() {
    	requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {//TODO make release work
//    	System.out.println("Release Init");
    	//grabber.setGrabberPower(0,false);
    	isDone = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	grabber.setGrabberPower(1,false);
    	//if (Math.abs(grabber.readCurrent()) > 13)
		if (Math.abs(grabber.readCurrent()) > 6) 
			isDone = true;
		
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	System.out.println("Release END");
    	grabber.setGrabberPower(0,false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
