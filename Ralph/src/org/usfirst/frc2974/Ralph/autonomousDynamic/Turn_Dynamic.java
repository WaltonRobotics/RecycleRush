package org.usfirst.frc2974.Ralph.autonomousDynamic;

import org.usfirst.frc2974.Ralph.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turn_Dynamic extends Command {

	private double timeToTravel;
	private double power;
	private final double C_ACCEL = 4;
	private final double C_DECEL = -4;

	public Turn_Dynamic(double defaultTimeToTravel, double defaultPower) {
		requires(Robot.driveTrain);
		timeToTravel = defaultTimeToTravel;
		power = -defaultPower;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timeToTravel = SmartDashboard.getNumber("AutonTurnTime",timeToTravel);
		power = -Math.signum(SmartDashboard.getNumber("AutonTurnTime",timeToTravel));
		Robot.driveTrain.setSpeeds(0, 0, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double time = timeSinceInitialized();
		double powerSteady = Math.abs(power);
		double powerAccel = C_ACCEL * time;
		double powerDecel = C_DECEL * (time - timeToTravel);
		double powerOut = Math.min(Math.min(powerAccel, powerDecel),
				powerSteady);
		Robot.driveTrain.setSpeeds(0, Math.signum(power) * powerOut, 0);
		// Robot.driveTrain.setSpeeds(1, 1, 1);
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
