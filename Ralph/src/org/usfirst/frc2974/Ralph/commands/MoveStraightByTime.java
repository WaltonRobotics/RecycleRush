package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveStraightByTime extends Command {
	
	private Timer timer;
	private double timeToTravel;
	private double power;
	private final double C_ACCEL = 4;
	private final double C_DECEL = 4;
	
    public MoveStraightByTime(double time, double power) {
    	requires(Robot.driveTrain);
    	timeToTravel = time;
    	this.power = power;
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	Robot.driveTrain.setSpeeds(0,0,0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = timer.get();
    	double powerSteady = Math.abs(power);
    	double powerAccel = C_ACCEL * time;
    	double powerDecel = C_DECEL * (time - timeToTravel);
    	double powerOut = Math.min(Math.min( powerAccel, powerDecel), powerSteady);
    	SmartDashboard.putNumber("Motor Power", powerOut);
    	Robot.driveTrain.setSpeeds(Math.signum(power)*powerOut, 0, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get()>timeToTravel;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	Robot.driveTrain.setSpeeds(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
