package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;
import org.usfirst.frc2974.Ralph.subsystems.Grabber;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Opens Claw all the way at the beginning of the match
 */
public class ClawOpen extends Command 
{
	private Grabber grabber;
	private boolean finished;
    public ClawOpen() 
    {
    	grabber = Robot.grabber;
    	requires(grabber);    	
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	grabber.setPowerMode();
    	finished = false;
    	grabber.setClawPower(.1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(!grabber.isOpen())
    	{
    		grabber.setClawPower(0);
    		finished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
