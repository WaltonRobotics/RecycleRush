package org.usfirst.frc2974.Ralph.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;
import org.usfirst.frc2974.Ralph.subsystems.Forklift.Mode;

/**
 * Sets default position for Forklift (at beginning of match) Fully lowers elevator sets encoder to 0 fully opens
 * claw(not implemented yet) switches elevator to position mode raises elevator to neutral position(just a little above
 * the ground for driving)
 */
public class ForkliftZero extends Command {//TODO this command needs to open claw as well

	private Forklift forklift;
	private boolean finished;

	public ForkliftZero() {
		forklift = Robot.forklift;
		requires(forklift);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		finished = false;
		forklift.setMode(Mode.power);
		forklift.move(-0.1, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (forklift.isZero()) {
			forklift.move(0.0, 0);
			forklift.resetPot();
			forklift.setMode(Mode.position);
			forklift.setElevatorPosition(forklift.NEUTRAL_POS);
			finished = true;
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
