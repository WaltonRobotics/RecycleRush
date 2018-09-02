package frc.team2974.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2974.robot.Robot;
import frc.team2974.robot.subsystems.Forklift;
import frc.team2974.robot.subsystems.Forklift.Mode;

/**
 *
 */
public class InitElevator extends Command {

  private final Forklift forklift = Robot.forklift;
  private boolean done;

  public InitElevator() {
    requires(forklift);
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    forklift.setMode(Mode.position);
    forklift.resetPot();
    done = false;
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    forklift.updateSmartDashboard();
    done = true;
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return done;
  }

  // Called once after isFinished returns true
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
  }
}
