package frc.team2974.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2974.robot.Robot;
import frc.team2974.robot.subsystems.Grabber;

/**
 * Grabs and hold the object that is in the grabber (tote or container)
 */
public class GrabAndHold extends Command {

  /**
   * The grabber subsystem
   */
  private final Grabber grabber = Robot.grabber;

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

    double move = -1;

//		if (Math.abs(grabber.readCurrent()) > 1) 
//			move = .25;

    grabber.setGrabberPower(move, false);

  }

  /**
   * It is never finished
   */
  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    grabber.setGrabberPower(0, false);
  }

  protected void interrupted() {
    end();
  }
}
