package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RaiseLowerTeleop extends Command {
	private Forklift forklift;		
	private double speed = 1;//in/sec
	private double lastTime;
	
    public RaiseLowerTeleop() 
    {
    	forklift = Robot.forklift;
    	requires(forklift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastTime = 0.0;
    	forklift.setPositionMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("elevatorTarget", Robot.forklift.currentTarget());
		SmartDashboard.putNumber("elevatorError", Robot.forklift.currentError());
		SmartDashboard.putNumber("elevatorPosition", Robot.forklift.currentPosition());
		
    	double time = timeSinceInitialized();
    	if(Robot.oi.right.getRawButton(4))
    	{
    		forklift.incrementElevatorPos(speed*(time-lastTime));
    	}
    	else if(Robot.oi.right.getRawButton(2))
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
    	forklift.setPowerMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
