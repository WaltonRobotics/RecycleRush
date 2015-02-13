package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.*;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;
import org.usfirst.frc2974.Ralph.subsystems.Grabber;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RaiseLowerTeleop extends Command {
	private Forklift forklift;	
	private Grabber grabber;
	private double speed = 10;//in/sec
	private double lastTime;
	
    public RaiseLowerTeleop() 
    {
    	forklift = Robot.forklift;
    	grabber = Robot.grabber;
    	requires(forklift);
    	requires(grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastTime = 0.0;
    	forklift.resetPot();
    	forklift.setPositionMode();
    	grabber.setPowerMode();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	SmartDashboard.putNumber("elevatorTarget", Robot.forklift.currentTarget());
		SmartDashboard.putNumber("elevatorError", Robot.forklift.currentError());
		SmartDashboard.putNumber("elevatorPosition", Robot.forklift.currentPosition());
    	double time = timeSinceInitialized();
    	if(Robot.oi.xbox.getPOVButton(Gamepad.POV.N))
    	{
    		SmartDashboard.putString("Debug","Going up");
    		forklift.incrementElevatorPos(speed*(time-lastTime));
    	}
    	else if(Robot.oi.xbox.getPOVButton(Gamepad.POV.S))
    	{
    		SmartDashboard.putString("Debug","Going down");
    		forklift.incrementElevatorPos(-speed*(time-lastTime));
    	}
    	
    	else if(Math.abs(Robot.oi.xbox.getLeftY()) > .1)
    	{
    		double joyIn = Robot.oi.xbox.getLeftY();
    		double move= Math.max(1.2*Math.abs(joyIn)-.2,0)* Math.signum(joyIn);

    		SmartDashboard.putString("Debug","Going up "+move);
    		forklift.incrementElevatorPos(-move*speed*(time-lastTime));
    		
    	}
    	else
    	{
    		SmartDashboard.putString("Debug","Nothing's happening");
    	}
    	
    	if(Math.abs(Robot.oi.xbox.getLeftTrigger()) > .1)
    	{
    		double joyIn = Robot.oi.xbox.getLeftTrigger();
    		double move= Math.max(1.2*Math.abs(joyIn)-.2,0)* Math.signum(joyIn);

    		SmartDashboard.putString("Debug","Opening "+move);
    		grabber.setClawPower(move);   		
    	}
    	else if(Math.abs(Robot.oi.xbox.getRightTrigger()) > .1)
    	{
    		double joyIn = Robot.oi.xbox.getRightTrigger();
    		double move= Math.max(1.2*Math.abs(joyIn)-.2,0)* Math.signum(joyIn);
    		double current = grabber.readCurrent();
    		if(Math.abs(current)>1)
    		{
    			move = Math.max(move,.25);
    		}
    			
    		SmartDashboard.putString("Debug","Closing "+move);
    		grabber.setClawPower(-move);   		
    	}
    	
    	lastTime = time;
    	SmartDashboard.putNumber("Grabber current", grabber.readCurrent());
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
