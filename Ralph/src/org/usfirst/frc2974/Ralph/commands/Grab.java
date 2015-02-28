package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Grabber;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Grab extends Command {

	private Grabber grabber = Robot.grabber;
	
    public Grab() {
        requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if (Math.abs(Robot.oi.xbox.getLeftTrigger()) > .1) {
			double joyIn = Robot.oi.xbox.getLeftTrigger();
			double move = Math.max(1.2 * Math.abs(joyIn) - .2, 0)
					* Math.signum(joyIn);

			SmartDashboard.putString("Debug", "Opening " + move);
			grabber.setClawPower(move);
		} else if (Math.abs(Robot.oi.xbox.getRightTrigger()) > .1) {
			double joyIn = Robot.oi.xbox.getRightTrigger();
			double move = Math.max(1.2 * Math.abs(joyIn) - .2, 0)
					* Math.signum(joyIn);
			double current = grabber.readCurrent();
			if (Math.abs(current) > 1) {
				move = Math.min(move, .25);
			}

			SmartDashboard.putString("Debug", "Closing " + move);
			grabber.setClawPower(-move);
		}
		else
		{
			grabber.setClawPower(0);
		}
		SmartDashboard.putNumber("Grabber current", grabber.readCurrent());
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
