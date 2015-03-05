package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveLinear extends Command {
	
	/**
	 * The amount of time the robot must travel
	 */
	private double timeToTravel;
	/**
	 * The speed at which the robot must travel
	 */
	private double power;
	
	private final double C_ACCEL = 4;
	private final double C_DECEL = -4;
	
    /**
     * Creates a MoveStraightByTime command that goes for the desired time and with desired speed
     * @param timeToTravel the amount of time to travel
     * @param power the speed to travel with    pos for forward   neg for backward 
     */
    public MoveLinear(double timeToTravel, double power) {
    	requires(Robot.driveTrain);
    	this.timeToTravel = SmartDashboard.getNumber("AutonMoveForwardTime");
    	this.power = -power;
    	}

    
    /**
     * Tells drivetrain to stop moving before it starts doing stuff
     */
    protected void initialize() {
    	Robot.driveTrain.setSpeeds(0,0,0);
    	this.timeToTravel = SmartDashboard.getNumber("AutonMoveForwardTime");
    }

    
    /** 
     * Mathy stuff to make robot go in a straight line
     */
    protected void execute() {
    	double time = timeSinceInitialized();
    	
    	double powerSteady = Math.abs(power);
    	double powerAccel = C_ACCEL * time;
    	double powerDecel = C_DECEL * (time - timeToTravel);
    	double powerOut = Math.min(Math.min( powerAccel, powerDecel), powerSteady);

    	Robot.driveTrain.setSpeeds(Math.signum(power)*powerOut, 0, 0);
    }

    
    /**
     * Returns whether or not the robot has traveled the desired distance or not
     */
    protected boolean isFinished() {
    	return timeSinceInitialized()>timeToTravel;
    }

    /** 
     * Tells robot to stop moving after the command has finished
     */
    protected void end() {
    	Robot.driveTrain.setSpeeds(0, 0, 0);
    }
    
    /**
     * Ends it self if the command is interrupted
     */
    protected void interrupted() {
    	end();
    }
}
