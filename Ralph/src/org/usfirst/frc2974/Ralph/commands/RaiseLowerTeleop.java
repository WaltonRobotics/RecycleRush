package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.*;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseLowerTeleop extends Command {
	private Forklift forklift;		
	private double speed = 1;//in/sec
	private double lastTime;
	private Timer timer;
	
    public RaiseLowerTeleop() 
    {
    	forklift = Robot.forklift;
    	requires(forklift);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	lastTime = timer.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = timer.get();
    	if(Robot.oi.xbox.getPOVButton(Gamepad.POV.N))
    	{
    		forklift.incrementElevatorPos(speed*(time-lastTime));
    	}
    	else if(Robot.oi.xbox.getPOVButton(Gamepad.POV.S))
    	{
    		forklift.incrementElevatorPos(-speed*(time-lastTime));
    	}
    	lastTime = time;
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
