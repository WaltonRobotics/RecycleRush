package frc.team2974.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.Gamepad;
import frc.team2974.robot.Robot;
import frc.team2974.robot.subsystems.Forklift;

/**
 * Push button, move forklift
 */
public class UpDownTeleop extends Command {

  private final Forklift forklift;
  private double lastTime;
  private boolean driverPosMode;

  public UpDownTeleop() {
    // Use requires() here to declare subsystem dependencies
    forklift = Robot.forklift;
    requires(forklift);
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    lastTime = 0;
    forklift.setMode(Forklift.Mode.power);
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    forklift.updateSmartDashboard();
    double time = timeSinceInitialized();

//    System.out.println(driverPosMode);
//
//    if (Robot.oi.xbox.getButton(Gamepad.Button.START) && !driverPosMode) {
//      SmartDashboard.putString("Debug", "going to position mode");
//      driverPosMode = true;
//      forklift.setMode(Forklift.Mode.position);
//    } else if (Robot.oi.xbox.getButton(Gamepad.Button.BACK) && driverPosMode) {
//      SmartDashboard.putString("Debug", "going to power mode");
//      driverPosMode = false;
//      forklift.setMode(Forklift.Mode.power);
//    }

//    if (Robot.oi.xbox.getPOVButton(Gamepad.POV.N) && driverPosMode) {
//      //forklift.move(.75, time-lastTime);
//      forklift.changeLevel(true);
//    } else if (Robot.oi.xbox.getPOVButton(Gamepad.POV.S) && !driverPosMode) {
//      //forklift.move(-.75,time-lastTime);
//      forklift.changeLevel(false);
//    } else
      if (Math.abs(Robot.oi.xbox.getLeftY()) > .1
        || Math.abs(Robot.oi.xbox.getRightY()) > .1) {
//      double rightJoystickMultiplier = SmartDashboard.getNumber("Right Gamepad Joystick Multiplier", .5);
//      double joyIn = Robot.oi.xbox.getLeftY() + rightJoystickMultiplier * Robot.oi.xbox.getRightY();
//      double move = Math.max(1.2 * Math.abs(joyIn) - .2, 0) * Math.signum(joyIn);
//
//      System.out.println(-move);
//      forklift.move(-move, time - lastTime);
      double value = -Robot.oi.xbox.getLeftY(); //Invert joystick sense
      SmartDashboard.putNumber("Left Y", value);
      forklift.move(value, time - lastTime);
    } else if (Robot.oi.xbox.getButton(Gamepad.Button.Y)) {
      forklift.resetPot();
    }
//    else {
//      forklift.hold(driverPosMode);//not do anything
//    }

    lastTime = time;

  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    forklift.hold(driverPosMode);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    end();
  }
}
