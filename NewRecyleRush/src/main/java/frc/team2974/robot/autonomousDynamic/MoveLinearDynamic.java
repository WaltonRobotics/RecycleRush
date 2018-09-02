package frc.team2974.robot.autonomousDynamic;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.Robot;

/**
 *
 */
public class MoveLinearDynamic extends Command {

  private final double C_ACCEL = 4;
  private final double C_DECEL = -4;
  /**
   * The amount of time the robot must travel
   */
  private double timeToTravel;
  /**
   * The speed at which the robot must travel
   */
  private double power;

  /**
   * Creates a MoveStraightByTime command that goes for the desired time and with desired speed
   */
  public MoveLinearDynamic(double defaultTimeToTravel, double defaultPower) {
    requires(Robot.driveTrain);
    timeToTravel = defaultTimeToTravel;
    power = -defaultPower;
  }


  /**
   * Tells drivetrain to stop moving before it starts doing stuff
   */
  protected void initialize() {
    Robot.driveTrain.setSpeeds(0, 0, 0);
    timeToTravel = SmartDashboard.getNumber("AutonMoveForwardTime", timeToTravel);
    power = -SmartDashboard.getNumber("AutonMoveForwardSpeed", power);
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
