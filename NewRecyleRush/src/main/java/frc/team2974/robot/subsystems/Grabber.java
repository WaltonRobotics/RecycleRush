package frc.team2974.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.RobotMap;
import frc.team2974.robot.commands.Grab;

/**
 *
 */
public class Grabber extends Subsystem {

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private final TalonSRX grabberTalon;
  private final ControlMode controlMode = ControlMode.PercentOutput;
  private double maxSteadyCurrentClose = 3;
  private double steadyPower = 0.2;
  private double maxSteadyCurrentOpen = 3;

  public Grabber() {
    grabberTalon = RobotMap.grabberTalon;

//    grabberTalon.ConfigRevLimitSwitchNormallyOpen(true);
//    grabberTalon.ConfigFwdLimitSwitchNormallyOpen(true);
//    grabberTalon.setFeedbackDevice(TalonSRX.FeedbackDevice.AnalogPot);
    grabberTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);//potentiometer gives feedback

//    grabberTalon.changeControlMode(TalonSRX.TalonControlMode.PercentVbus);
//    grabberTalon.enableControl();
//    grabberTalon.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.Analog, 0);
  }

  public void initDefaultCommand() {
setDefaultCommand(new Grab());
  }

  public void setPowerMode() {
    Preferences prefs = Preferences.getInstance();
    maxSteadyCurrentClose = prefs.getDouble("G_MaxSteadyCurrentClose", maxSteadyCurrentClose);
    maxSteadyCurrentOpen = prefs.getDouble("G_MaxSteadyCurrentOpen", maxSteadyCurrentOpen);
    steadyPower = prefs.getDouble("G_SteadyPower", steadyPower);
//    grabberTalon.changeControlMode(TalonSRX.TalonControlMode.PercentVbus);
    //will there be brake functionality?
//    grabberTalon.enableControl();

  }

  //open/close a variable amount
  //positive variable = close
  //negative = open
  //power- value between -1 and 1(DO NOT USE 1!!!!! PLEASE!!!!)
  public void setGrabberPower(double power, boolean overrideLimit) {
    double current = Math.abs(readCurrent());
    if ((power < 0 && current < maxSteadyCurrentOpen)
        || (power > 0 && current < maxSteadyCurrentClose)
        || overrideLimit) {
      power = Math.max(Math.min(power, 1), -1);
    } else {
      power = Math.max(Math.min(power, steadyPower), -steadyPower);
    }

    grabberTalon.set(controlMode, power);

  }

  public double readCurrent() {
    SmartDashboard.putNumber("GrabberCurrent", grabberTalon.getOutputCurrent());
    return grabberTalon.getOutputCurrent();
  }

  /**
   * updates smartdashboard
   */
  public void updateSmartDashboard() {
    SmartDashboard.putString("GrabberMode", "PowerMode");
    SmartDashboard.putNumber("GrabberCurrent", readCurrent());
  }

}

