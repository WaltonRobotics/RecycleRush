package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Strafe extends Command {

	private double timeToTravel;
	private double power;
	private final double C_ACCEL = 4;
	private final double C_DECEL = -4;
	
    /*public StrafeByTime(double timeToTravel, double power){
    requires(Robot.driveTrain);
    	this.timeToTravel = timeToTravel;
    	this.power = power;
    	}*/

	public Strafe(double timeToTravel, double power) {
		requires(Robot.driveTrain);
    	this.timeToTravel = timeToTravel;
    	this.power = power;
	}

	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.setSpeeds(0,0,0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = timeSinceInitialized();
    	double powerSteady = Math.abs(power);
    	double powerAccel = C_ACCEL * time;
    	double powerDecel = C_DECEL * (time - timeToTravel);
    	double powerOut = Math.min(Math.min( powerAccel, powerDecel), powerSteady);
    	SmartDashboard.putNumber("Motor Power", powerOut);
    	Robot.driveTrain.setSpeeds(0, 0, Math.signum(power)*powerOut);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timeSinceInitialized()>timeToTravel;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setSpeeds(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
