package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.Gamepad;
import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	forklift.updateSmartDashboard();
    	double time = timeSinceInitialized();
    	
    	if(Robot.oi.xbox.getButton(Gamepad.Button.START) && !forklift.isPositionMode())
    	{
    		SmartDashboard.putString("Debug", "going to position mode");
    		forklift.setPositionMode();
    	}
    	else if(Robot.oi.xbox.getButton(Gamepad.Button.BACK) && forklift.isPositionMode())
    	{
    		SmartDashboard.putString("Debug", "going to power mode");
    		forklift.setPowerMode();
    	}
    	
    	if(Robot.oi.xbox.getPOVButton(Gamepad.POV.N))
    	{
    		//forklift.move(.75, time-lastTime);
    		forklift.changeLevel(true);
    	}
    	else if(Robot.oi.xbox.getPOVButton(Gamepad.POV.S))
    	{
    		//forklift.move(-.75,time-lastTime);
    		forklift.changeLevel(false);
    	}
    	else if (Math.abs(Robot.oi.xbox.getLeftY()) > .1
    			|| Math.abs(Robot.oi.xbox.getRightY()) > .1) {
    		double rightJoystickMultiplier = SmartDashboard.getNumber("Right Gamepad Joystick Multiplier",.5);
 			double joyIn = Robot.oi.xbox.getLeftY() + rightJoystickMultiplier*Robot.oi.xbox.getRightY();
 			double move = Math.max(1.2 * Math.abs(joyIn) - .2, 0) * Math.signum(joyIn);
 			forklift.move(-move, time-lastTime);
    	} 
    	else if(Robot.oi.xbox.getButton(Gamepad.Button.Y)) {
    		forklift.resetPot();
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
