package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer; 

/**
 *
 */
public class StrafeForTime extends Command {
	private Timer timer;
	private double timeToTravel;
	private double leftOrRight;
    public StrafeForTime(double timeToTravel, double leftOrRight) {
    	requires(Robot.driveTrain);
    	this.timeToTravel = timeToTravel;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = new Timer();
    	timer.start();
    	timer.reset();
    	
    	Robot.driveTrain.setSpeeds(0,0,leftOrRight);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.setSpeeds(0,0,leftOrRight);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get()>timeToTravel;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
