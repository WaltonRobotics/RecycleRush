package org.usfirst.frc2974.Ralph.commands;

import org.usfirst.frc2974.Ralph.*;
import org.usfirst.frc2974.Ralph.subsystems.Forklift;
import org.usfirst.frc2974.Ralph.subsystems.Grabber;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveElevator extends Command {
	private Forklift forklift;
	private Grabber grabber;
	private double speed = 10;// in/sec
	private double lastTime;

	public MoveElevator() {
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
	//uses edge detection (falling edge) to raise/lower elevator when N and S buttons released
	boolean ifNPressed = false;
	boolean ifSPressed = false;
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
//		SmartDashboard.putData(Scheduler.getInstance());
		
		double incrementElevatorPos;
		// forklift.setPositionMode();
		SmartDashboard.putNumber("elevatorTarget",
				Robot.forklift.currentTarget());
		SmartDashboard
				.putNumber("elevatorError", Robot.forklift.currentError());
		SmartDashboard.putNumber("elevatorPosition",
				Robot.forklift.currentPosition());
		double time = timeSinceInitialized();
		
		if (!Robot.oi.xbox.getPOVButton(Gamepad.POV.N) && ifNPressed) {
			incrementElevatorPos = speed;
			//SmartDashboard.putString("Debug", "Going up" +incrementElevatorPos);
			//forklift.incrementElevatorPos(incrementElevatorPos);//(time - lastTime));
			//forklift.setElevatorPosition(-6);
			//forklift.increment(5);
		} 
		else if (!Robot.oi.xbox.getPOVButton(Gamepad.POV.S) && ifSPressed) {
			//SmartDashboard.putString("Debug", "Going down" + -speed);
			incrementElevatorPos = -speed;
			//forklift.incrementElevatorPos(incrementElevatorPos); //*(time-lastTime));
			//forklift.incrementElevatorPos(-1);
//			forklift.setElevatorPosition(3);
			//forklift.increment(-5);
			
		}

		else if (Math.abs(Robot.oi.xbox.getLeftY()) > .1) {
			double joyIn = Robot.oi.xbox.getLeftY();
			double move = Math.max(1.2 * Math.abs(joyIn) - .2, 0);
			//SmartDashboard.putString("Debug", "Going up " + move);
			incrementElevatorPos = -move * speed * (time - lastTime);
			//forklift.incrementElevatorPos(incrementElevatorPos);
			//forklift.setElevatorPosition(0);
			//forklift.increment(0);
			
			//SmartDashboard.putString("Debug", "Going up" +incrementAmount);
			
		}
		// try setting move = 1
		// double move = 1;

		else {
			//SmartDashboard.putString("Debug", "Nothing's happening");
			incrementElevatorPos =0;
		}



		lastTime = time;
		
		
		ifNPressed = Robot.oi.xbox.getPOVButton(Gamepad.POV.N);
		ifSPressed = Robot.oi.xbox.getPOVButton(Gamepad.POV.S);
		
//		SmartDashboard.putString("Debug", "" +incrementElevatorPos);
		//SmartDashboard.putNumber("Times Incremented", forklift.timesIncremented());
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
