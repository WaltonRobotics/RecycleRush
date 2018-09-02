package frc.team2974.robot.autonomousDynamic;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Waits a desired amount of sconds
 */
public class WaitDynamic extends Command {

  private final String key;
  private double timeToWait;

  public WaitDynamic(double defaultTimeToWait, String key) {
    this.timeToWait = defaultTimeToWait;
    this.key = key;
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    timeToWait = SmartDashboard.getNumber(key, timeToWait);
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
