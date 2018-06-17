package org.usfirst.frc2974.Ralph.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2974.Ralph.Robot;

/**
 *
 */
public class Turn extends Command {

	private final double C_ACCEL = 4;
	private final double C_DECEL = -4;
	private double timeToTravel;
	private double power;

	public Turn(double timeToTravel, double power) {
		requires(Robot.driveTrain);
		this.timeToTravel = timeToTravel;
		this.power = power;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.setSpeeds(0, 0, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double time = timeSinceInitialized();
		double powerSteady = Math.abs(power);
		double powerAccel = C_ACCEL * time;
		double powerDecel = C_DECEL * (time - timeToTravel);
		double powerOut = Math.min(Math.min(powerAccel, powerDecel), powerSteady);
		Robot.driveTrain.setSpeeds(0, Math.signum(power) * powerOut, 0);
		//Robot.driveTrain.setSpeeds(1, 1, 1);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timeSinceInitialized() > timeToTravel;
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
