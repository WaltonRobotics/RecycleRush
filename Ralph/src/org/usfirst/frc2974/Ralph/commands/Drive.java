// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robot builder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating auto generated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2974.Ralph.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2974.Ralph.Robot;

/**
 *
 */
public class Drive extends Command {

	public Drive() {

		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SmartDashboard.putBoolean("Cheesy", true);
		SmartDashboard.putNumber("Turn Factor", .5);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double throttle;
		double forward;
		double strafe;
		double turn;
    	if(SmartDashboard.getBoolean("Cheesy")){
    		//double leftY = (-Robot.oi.left.getY() +1)/2; use left joy instead of throttle on right joy
    		throttle = ((-Robot.oi.right.getZ() +1)/2);
    		forward = Robot.oi.right.getY();
    		strafe = -Robot.oi.right.getX();
    		turn = -Robot.oi.right.getRawAxis(3);
    	}
    		
    	else{
    		throttle = .5; //Above 2 will limit top speed, Default is 2
    		forward = (Robot.oi.left.getY() + Robot.oi.right.getY()) ;
            turn = (Robot.oi.left.getY() - Robot.oi.right.getY()) ;
            strafe = -(Robot.oi.left.getX() + Robot.oi.right.getX()) ;

    	}
    	
    	SmartDashboard.putNumber("Drive forward", forward);
		SmartDashboard.putNumber("Drive sideways", strafe);
		SmartDashboard.putNumber("ZRotate", turn);
		SmartDashboard.putNumber("throttle", throttle);
		
    	Robot.driveTrain.setSpeeds(throttle*forward, SmartDashboard.getNumber("Turn Factor")*throttle*turn, throttle*strafe);
		Robot.driveTrain.setHalfSpeed(Robot.oi.right.getTrigger()); //Sets half speed when trigger pressed
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
