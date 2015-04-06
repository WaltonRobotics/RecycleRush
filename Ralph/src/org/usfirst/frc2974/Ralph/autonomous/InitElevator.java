package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;
import org.usfirst.frc2974.Ralph.subsystems.Forklift.Mode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class InitElevator extends Command {

	Forklift forklift = Robot.forklift;
	boolean done;
    public InitElevator() {
        requires(forklift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	forklift.setMode(Mode.position);
    	forklift.resetPot();
    	done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	forklift.updateSmartDashboard();
    	done = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
