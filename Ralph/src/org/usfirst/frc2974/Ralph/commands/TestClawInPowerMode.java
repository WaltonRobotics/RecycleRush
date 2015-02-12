package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestClawInPowerMode extends Command {

	private Forklift forklift;
	private double timeToRun;
	private double power;
	private boolean finished;
	
    public TestClawInPowerMode(double timeToRun, double power) {
        
    	forklift = Robot.forklift;
        requires(forklift);
    	this.timeToRun = timeToRun;
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	forklift.setPowerMode();
    	forklift.setClawMotor(power);
    	finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {    	
    	if (timeSinceInitialized() > timeToRun)
    		finished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	forklift.setClawMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
