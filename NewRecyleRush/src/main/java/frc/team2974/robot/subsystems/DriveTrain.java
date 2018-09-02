// TODO [Insert Javadoc Comment Somewhere]
package frc.team2974.robot.subsystems;


import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team2974.robot.RobotMap;
import frc.team2974.robot.commands.Drive;

/**
 * Makes the robot go.
 */
public class DriveTrain extends Subsystem {

  private final double ACCELERATION_CONSTANT = .2;////in/s^2
  private final double halfSpeedMod = 0.5; //Value that output speed is multiplied by in "Half Speed"
  private final VictorSP frontRight = RobotMap.driveTrainFrontRight;
  private final VictorSP backRight = RobotMap.driveTrainBackRight;
  private final VictorSP frontLeft = RobotMap.driveTrainFrontLeft;
  private final VictorSP backLeft = RobotMap.driveTrainBackLeft;
  // BEGIN variables and stuff
  private boolean halfSpeed = false;
  private double previousFrontRight = 0;
  private double previousFrontLeft = 0;
  private double previousBackRight = 0;
  private double previousBackLeft = 0;
  // END variables and stuff

  public void initDefaultCommand() {
    setDefaultCommand(new Drive());
  }

  private double fixAccel(double previousPower, double desiredPower) {
    if (Math.abs(previousPower - desiredPower) > ACCELERATION_CONSTANT) {
      if (previousPower > desiredPower) {
        desiredPower = previousPower - ACCELERATION_CONSTANT;//limit the speed on deceleration
      } else {
        desiredPower = previousPower + ACCELERATION_CONSTANT;//limit the speed on acceleration
      }
    } else { //desired remains the same
    }
    return desiredPower;
  }

  public void setHalfSpeed(boolean half) {
    halfSpeed = half;
  }

  public void setSpeeds(double forward, double rotate, double straif) {
    straif = -straif;
    double backL = forward + rotate + straif;
    double backR = forward - rotate - straif;
    double frontL = forward + rotate - straif;
    double frontR = forward - rotate + straif;

    if (halfSpeed) { //sets half speed
      setTalonSpeeds(halfSpeedMod * backR, halfSpeedMod * -backL,
          halfSpeedMod * -frontL, halfSpeedMod * frontR);
    } else {
      setTalonSpeeds(backR, -backL, -frontL, frontR);
    }
  }

  public void setSpeedsManual(double left, double right) {
    setTalonSpeeds(right, left, right, left);
  }

  private void setTalonSpeeds(double backR, double backL, double frontL, double frontR) {
    backRight.set(fixAccel(previousBackRight, backR));
    backLeft.set(fixAccel(previousBackLeft, backL));
    frontLeft.set(fixAccel(previousFrontLeft, frontL));
    frontRight.set(fixAccel(previousFrontRight, frontR));
    previousBackRight = -backR;
    previousBackLeft = -backL;
    previousFrontLeft = -frontL;
    previousFrontRight = -frontR;
  }
}

