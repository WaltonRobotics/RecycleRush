package org.usfirst.frc2974.Ralph.autonomous;

import org.usfirst.frc2974.Ralph.Robot;
import org.usfirst.frc2974.Ralph.subsystems.Grabber;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Grabs and hold the object that is in the grabber (tote or container)
 */
public class GrabAndHold extends Command {

	/**
	 * The grabber subsystem
	 */
	private Grabber grabber = Robot.grabber;
	
    /**
     * Creates the grab and Hold command
     */
    public GrabAndHold() {
    	requires(Robot.grabber);
    }

    protected void initialize() {
    }

     /**
     * Closes the grabber until the current is greater than 1 and then it holds it
     */
    protected void execute() {
    	
    	double move = 1;
    	
		if (Math.abs(grabber.readCurrent()) > 1) 
			move = .25;
		
		grabber.setGrabberPower(move);
    }
    
    /** 
     * It is never finished
     */
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
