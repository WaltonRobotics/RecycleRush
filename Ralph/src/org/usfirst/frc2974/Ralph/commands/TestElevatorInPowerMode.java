package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to test the elevator in the simplest way
 * 
 * Uses 'Power' mode which is implemented via the %VBus mode.
 */
public class TestElevatorInPowerMode extends Command {

	private Forklift forklift;
	private double timeToRun;
	private double power;
	private boolean finished;
	
    /**
     * Constructor
     * 
     * @param timeToRun The time period for the command to run. (s)
     * @param power The power to set the motor. Should be between -1 and 1 (%Vbus/100)
     */
    public TestElevatorInPowerMode(double timeToRun, double power) {
        forklift = Robot.forklift;
        requires(forklift);
    	this.timeToRun = timeToRun;
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	forklift.setPowerMode();
    	forklift.setElevatorPower(power);
    	finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (timeSinceInitialized() > timeToRun){
    		finished = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	forklift.setElevatorPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
