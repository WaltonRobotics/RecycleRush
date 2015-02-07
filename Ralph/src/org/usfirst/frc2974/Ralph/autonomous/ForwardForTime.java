package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer; 

/**
 *
 */
public class ForwardForTime extends Command {
	private Timer timer;
	private double timeToTravel;
	private double forwardOrBackward;
    public ForwardForTime(double timeToTravel, double forwardOrBackward) {
    	requires(Robot.driveTrain);
    	this.timeToTravel = timeToTravel;
    	this.forwardOrBackward = forwardOrBackward;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = new Timer();
    	timer.reset();
    	timer.start();
    	Robot.driveTrain.setSpeeds(100,0,0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.setSpeeds(-forwardOrBackward,0,0);
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
