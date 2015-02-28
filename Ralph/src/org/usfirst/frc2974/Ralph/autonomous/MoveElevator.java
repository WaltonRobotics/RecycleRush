package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevator extends Command {

	public final double TIMEOUT = 2.0; 
	private Forklift forklift;	
	private double height;
	private int timesAtPosition;
	private final int MAX_COUNT = 3;
	
    public MoveElevator(double height) 
    {
    	forklift = Robot.forklift;
    	requires(forklift);
    	this.height = height;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	timesAtPosition = 0;
    	forklift.setElevatorPosition(height);
    	Preferences prefs = Preferences.getInstance();
    	setTimeout(prefs.getDouble("E_Timeout", 2.0));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	if(forklift.isAtPosition())
    		timesAtPosition++;
    	else
    		timesAtPosition=0;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // return finished;
    	return timesAtPosition > MAX_COUNT || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
