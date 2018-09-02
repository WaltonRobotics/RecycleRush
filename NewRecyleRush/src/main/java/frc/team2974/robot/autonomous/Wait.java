package frc.team2974.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Waits a desired amount of sconds
 */
public class Wait extends Command {

  private final double timeToWait;

  public Wait(double timeToWait) {
    this.timeToWait = timeToWait;
  }

  // Called just before this Command runs the first time
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return timeSinceInitialized() > timeToWait;
  }

  // Called once after isFinished returns true
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
  }
}
