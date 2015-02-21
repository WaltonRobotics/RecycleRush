package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Gamepad;
import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Push button, move forklift
 */
public class UpDownTeleop extends Command {

	private Forklift forklift;
	private double lastTime;
	
    public UpDownTeleop() {
        // Use requires() here to declare subsystem dependencies
        forklift = Robot.forklift;
        requires(forklift);        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	forklift.setPowerMode();
    	lastTime = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = timeSinceInitialized();
    	
    	if(Robot.oi.xbox.getPOVButton(Gamepad.POV.N))
    	{
    		forklift.move(-.1, time-lastTime);
    	}
    	else if(Robot.oi.xbox.getPOVButton(Gamepad.POV.S))
    	{
    		forklift.move(.1,time-lastTime);
    	}
    	else
    		forklift.hold();//not do anything
    	
    	lastTime = time;
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	forklift.hold();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
