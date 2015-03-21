package org.usfirst.frc.team2974.robot.autonomous;

import org.usfirst.frc.team2974.robot.Robot;
import org.usfirst.frc.team2974.robot.subsystems.Forklift;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the elevator 
 */
public class MoveElevator extends Command {

	/**
	 * The forklift subsystem
	 */
	private Forklift forklift;	
	
	/**
	 * The height the command is told to go to
	 */
	private double height;
	
	/**
	 * The amount of times it reaches the position
	 */
	private int timesAtHeight;
	
	/**
	 * The number of times we want it to reach the position before we say we are there
	 */
	private final int MAX_COUNT = 3;
	
    
	/**
	 * Creates a MoveElevator command that goes to the desired height
	 * @param height the desired height in inches
	 */
	public MoveElevator(double height) 
    {
    	forklift = Robot.forklift;
    	requires(forklift);
    	this.height = height;
    }

    
    /**
     * Initializes the command by setting the timeout and the height
     */
    protected void initialize() 
    {
    	timesAtHeight = 0;
    	forklift.setElevatorPosition(height);
    	Preferences prefs = Preferences.getInstance();
    	setTimeout(prefs.getDouble("E_Timeout", 2.0));
    	forklift.setMode(Forklift.Mode.position);
    }

    
    /** 
     * Every time the forklift is at the desired position it increments the timesAtPoisition variable
     */
    protected void execute()
    {
    	if(forklift.isAtPosition())
    		timesAtHeight++;
    	else
    		timesAtHeight=0;
    }

    
    /** 
     * Returns whether or not the elevator has reached the desired position the desired number of times
     */
    protected boolean isFinished() {
    	return timesAtHeight > MAX_COUNT || isTimedOut();
    }

    
    protected void end() {
    }
    
    protected void interrupted() {
    }
}
