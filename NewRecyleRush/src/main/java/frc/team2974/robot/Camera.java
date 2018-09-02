package frc.team2974.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Piyush Kancharlawar
 */
class Camera extends Command {

  private final NetworkTable networkTable;
  private final Relay light = RobotMap.alignmentIndicatorLightSpike;

  private double threshold = 170000;

  /**
   *
   */
  public Camera() {

    networkTable = NetworkTableInstance.getDefault().getTable("roborealm");

    SmartDashboard.putBoolean("is aligned", false);

//		SmartDashboard.putNumber("green threshold", 0);
//		SmartDashboard.putNumber("blue threshold", 0);
//		SmartDashboard.putNumber("red threshold", 0);
  }

  protected void initialize() {
    Preferences prefs = Preferences.getInstance();
    threshold = prefs.getDouble("CAM_Threshold", threshold);
  }

  protected void execute() {
    if (Robot.oi.xbox.getButton(Gamepad.Button.A)) {
      light.set(Value.kOn);
    } else {
      light.set(Value.kOff);
    }

//    System.out.println("LIGHT IS SET TO " + light.get().name());
  }

  protected void end() {
    light.free();
  }

  protected void interrupted() {
//		end();
  }

  protected boolean isFinished() {
    return false;
  }

  public void updateSmartDashboard() {
    double r, g, b;

    r = networkTable.getEntry("SUM_R").getDouble(0);
    g = networkTable.getEntry("SUM_G").getDouble(0);
    b = networkTable.getEntry("SUM_B").getDouble(0);

//		System.out.println(r + ", " + g + ", " + b);

//		double redThreshold = SmartDashboard.getNumber("red threshold");
//		double greenThreshold = SmartDashboard.getNumber("green threshold");
//		double blueThreshold = SmartDashboard.getNumber("blue threshold");

    if ((b + g + r) / 3.0 >= threshold) {
      SmartDashboard.putBoolean("is aligned", true);
    } else {
      SmartDashboard.putBoolean("is aligned", false);
    }
  }
}
