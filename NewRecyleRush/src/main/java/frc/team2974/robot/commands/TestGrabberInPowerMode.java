package frc.team2974.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2974.robot.Robot;
import frc.team2974.robot.subsystems.Grabber;

/**
 *
 */
class TestGrabberInPowerMode extends Command {

  private final Grabber grabber;
  private final double timeToRun;
  private final double power;
  private boolean finished;

  public TestGrabberInPowerMode(double timeToRun, double power) {

    grabber = Robot.grabber;
    requires(grabber);
    this.timeToRun = timeToRun;
    this.power = power;
  }

  // Called just before this Command runs the first time
  protected void initialize() {

    grabber.setPowerMode();
    grabber.setGrabberPower(power, false);
    finished = false;
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    if (timeSinceInitialized() > timeToRun) {
      finished = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return finished;
  }

  // Called once after isFinished returns true
  protected void end() {
    grabber.setGrabberPower(0, false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    end();
  }
}
