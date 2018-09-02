package frc.team2974.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2974.robot.Robot;

/**
 *
 */
public class MoveLinear extends Command {

  private final double C_ACCEL = 4;
  private final double C_DECEL = -4;
  /**
   * The amount of time the robot must travel
   */
  private final double timeToTravel;
  /**
   * The speed at which the robot must travel
   */
  private final double power;

  /**
   * Creates a MoveStraightByTime command that goes for the desired time and with desired speed
   *
   * @param timeToTravel the amount of time to travel
   * @param power the speed to travel with    pos for forward   neg for backward
   */
  public MoveLinear(double timeToTravel, double power) {
    requires(Robot.driveTrain);
    this.timeToTravel = timeToTravel;
    this.power = -power;
  }


  /**
   * Tells drivetrain to stop moving before it starts doing stuff
   */
  protected void initialize() {
    Robot.driveTrain.setSpeeds(0, 0, 0);
  }


  /**
   * Mathy stuff to make robot go in a straight line
   */
  protected void execute() {
    double time = timeSinceInitialized();

    double powerSteady = Math.abs(power);
    double powerAccel = C_ACCEL * time;
    double powerDecel = C_DECEL * (time - timeToTravel);
    double powerOut = Math.min(Math.min(powerAccel, powerDecel), powerSteady);

    Robot.driveTrain.setSpeeds(Math.signum(power) * powerOut, 0, 0);
  }


  /**
   * Returns whether or not the robot has traveled the desired distance or not
   */
  protected boolean isFinished() {
    return timeSinceInitialized() > timeToTravel;
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
