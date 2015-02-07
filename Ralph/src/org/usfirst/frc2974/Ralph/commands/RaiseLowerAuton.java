package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseLowerAuton extends Command {

	private Forklift forklift;	
	private double height;
	private int positionCount;
	private final int MAX_COUNT = 3;
	
    public RaiseLowerAuton(double height) 
    {
    	forklift = Robot.forklift;
    	requires(forklift);
    	this.height = height;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	//finished = false;
    	positionCount = 0;
    	forklift.setElevatorPosition(height);    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	if(forklift.isAtPosition())
    	{
    		positionCount++;
    		//finished = true;
    	}
    	else
    	{
    		positionCount = 0;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // return finished;
    	return positionCount > MAX_COUNT;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
