package frc.team2974.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.RobotMap;
import frc.team2974.robot.commands.UpDownTeleop;

//Forklift subsystem
//Robot will have:
//Potentiometer to see lift's position on robot
//switch to see bottom
//2 CAN Talons - 1 for elevator, 1 for claw
//-possibly- a brake on elevator(PID controlled), need to turn on/off;


public final class Forklift extends Subsystem {
//	private static int timesIncremented=0;

  public final double NEUTRAL_POS = 6;
  public final double LEVEL_MULTIPLIER = 12;
  //	public final double HEIGHT_CONSTANT = 1;
  public final double MAX_POSITION = 55;//to be calibrated
  private final int profile1 = 1;
  //after elevator is zeroed, raises to this for safe driving
  private final double LEVEL_DEADBAND = 1;
  //TODO determine by how much the level # (1, 2, 3) must be multiplied to get postiion to raise the arm
  //pos is in rotations: LEVEL_MULTIPLIERS = # rotations to raise the tote 1 level
  private final double HEIGHT_CONSTANT = -12.1635; // Calibrated against
  private final double[] LEVELS = {0, 12, 24, 36, 48, 60};
  private final double ACCEL_OVERSHOOT = .15;
  private final double BUFFER_LENGTH = 4;
  private final boolean POWER_RESPECTS_LIMITS = true;
  private final TalonSRX elevatorTalon;
  private final double holdPower = .05;
  private final double f = 0;
  private double zeroPosition = 0;
  private double speed = 0;
  private double lastSetPower = 0;
  //TODO get input from limit switch
  private double rampRate;
  private int izone;
  private double p = 10;
  private double i = 0;
  private double d = 0;
  private double deadband = 0.25;
  private int softLimitFwd;
  private int softLimitRvs;
  private boolean isInPositionMode = false;
  private Mode mode;
  private ControlMode controlMode = ControlMode.Position;
  //These are methods used in ForkliftZero command
//	public void increment(final double amount){
//		elevatorTalon.set(elevatorTalon.getSetpoint()+amount);
//		if(amount<0){
//			timesIncremented--;
//			return;
//		}
//		timesIncremented++;
//	}
//	
//	public int timesIncremented(){
//		return timesIncremented;
//	}

  public Forklift() {

    elevatorTalon = RobotMap.elevatorTalon;

//    elevatorTalon.setFeedbackDevice(TalonSRX.FeedbackDevice.AnalogPot);//potentiometer gives feedback
    elevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);//potentiometer gives feedback
    elevatorTalon.setSensorPhase(true);
//    elevatorTalon.setInverted(false);
    elevatorTalon.setInverted(true);

//    Preferences prefs = Preferences.getInstance();

//    softLimitFwd = prefs.getInt("E_SoftLimitForward", 1000);
//    softLimitRvs = prefs.getInt("E_SoftLimitReverse", 508);
    softLimitFwd = 1020;
    softLimitRvs = 550;

//    elevatorTalon.setForwardSoftLimit(softLimitFwd);
    elevatorTalon.configForwardSoftLimitEnable(true, 0);
    elevatorTalon.configReverseSoftLimitEnable(true, 0);
    elevatorTalon.configForwardSoftLimitThreshold(softLimitFwd, 0);
//    elevatorTalon.getStickyFaults();
    elevatorTalon.configReverseSoftLimitThreshold(softLimitRvs, 0);
//    elevatorTalon.setReverseSoftLimit(softLimitRvs);
    //elevatorTalon.enableForwardSoftLimit(true);
    //elevatorTalon.enableReverseSoftLimit(true);

//    setPositionMode();
    setPowerMode();
  }

  public void initDefaultCommand() {
    setDefaultCommand(new UpDownTeleop());
  }

  /**
   * sets elevator to powermode
   */
  private void setPowerMode() {

    controlMode = ControlMode.PercentOutput;
//    elevatorTalon.changeControlMode(TalonSRX.TalonControlMode.PercentVbus);
    if (!POWER_RESPECTS_LIMITS) {
//      elevatorTalon.enableForwardSoftLimit(false);
//      elevatorTalon.enableReverseSoftLimit(false);

      elevatorTalon.configForwardSoftLimitEnable(false, 0);
      elevatorTalon.configReverseSoftLimitEnable(false, 0);
    }
    //will there be brake functionality?
//    elevatorTalon.enableControl();
    isInPositionMode = false;
  }

  /**
   * sets elevtor to positionmode
   */
  private void setPositionMode() {
    Preferences prefs = Preferences.getInstance();
    p = prefs.getDouble("E_P", 3);
    i = prefs.getDouble("E_I", 0.02);
    d = prefs.getDouble("E_D", 0.0);
    izone = prefs.getInt("E_Izone", 5);
    deadband = Math.abs(prefs.getDouble("E_Deadband", 0.25) * HEIGHT_CONSTANT);
//    rampRate = Math.abs(prefs.getDouble("E_RampRate", 10));
//    elevatorTalon.setPID(p, i, d, f, izone, rampRate, profile1);

    elevatorTalon.config_kP(profile1, p, 0);
    elevatorTalon.config_kI(profile1, i, 0);
    elevatorTalon.config_kD(profile1, d, 0);
    elevatorTalon.config_kF(profile1, f, 0);
    elevatorTalon.config_IntegralZone(izone, 0, 0);
//    elevatorTalon.config_IntegralZone(izone, 0, 0);
    elevatorTalon.selectProfileSlot(profile1, 0);
//    elevatorTalon.con

    softLimitFwd = prefs.getInt("E_SoftLimitForward", -35);
    softLimitRvs = prefs.getInt("E_SoftLimitReverse", -750);

    speed = prefs.getDouble("E_Speed", 1);

//    elevatorTalon.setForwardSoftLimit(softLimitFwd);
//    elevatorTalon.setReverseSoftLimit(softLimitRvs);
//    elevatorTalon.enableForwardSoftLimit(true);
//    elevatorTalon.enableReverseSoftLimit(true);

    elevatorTalon.configForwardSoftLimitEnable(true, 0);
    elevatorTalon.configReverseSoftLimitEnable(true, 0);
    elevatorTalon.configForwardSoftLimitThreshold(softLimitFwd, 0);
    elevatorTalon.configReverseSoftLimitThreshold(softLimitRvs, 0);

    controlMode = ControlMode.Position;

    double speed = currentSpeed();
    double overshoot = (speed * speed) * Math.signum(speed) * ACCEL_OVERSHOOT / 2;
    SmartDashboard.putNumber("Debug_Overshoot", overshoot);
    SmartDashboard.putNumber("Debug_Position", currentPosition());

    elevatorTalon.set(controlMode, elevatorTalon.getSelectedSensorPosition(0) + overshoot * HEIGHT_CONSTANT);
//    elevatorTalon.enableControl();

    isInPositionMode = true;
  }

  /**
   * sets mode for elevator
   *
   * @param mode the mode to be in power or position
   */
  public void setMode(Mode mode) {
    if (mode == Mode.power && !(this.mode == Mode.power)) {
      setPowerMode();
      this.mode = mode;
    }
//    else if (mode == Mode.position && !(this.mode == Mode.position)) {
//      setPositionMode();
//      this.mode = mode;
//    } else if (mode == Mode.switcheroo && !(this.mode == Mode.switcheroo)) {
//      setPositionMode();
//      this.mode = mode;
//    }
  }


  /**
   * returns if the forklift is at the bottom and has closed the limit switch
   *
   * @return true if the elevator has triggered proximity switch
   */
  public boolean isZero() {
    return RobotMap.digital0.get();
  }


  /**
   * resets potentiometer to such that position zero is current position
   */
  public void resetPot() {
    zeroPosition = elevatorTalon.getSelectedSensorPosition(0);
  }

  /**
   * moves elevator in position mode
   *
   * @param height - target height in inches relative to last zero position
   */
  public void setElevatorPosition(double height) {
    double pos = HEIGHT_CONSTANT * height + zeroPosition;
    pos = Math.min(softLimitFwd, Math.max(softLimitRvs, pos));
    elevatorTalon.set(controlMode, pos);

  }

  /**
   * moves elevator in power mode
   *
   * power is cutoff outside of softlimits as defined by preferences power is reduced as limits are approached
   *
   * @param power - power of motor
   */
  public void setElevatorPower(double power) {
//    double position = elevatorTalon.getSelectedSensorPosition(0);
//    System.out.println("Position: " + elevatorTalon.getSelectedSensorPosition(0));
//    System.out.println("Active Position: " + elevatorTalon.getActiveTrajectoryPosition());
//    System.out.println("Heading: " + elevatorTalon.getActiveTrajectoryHeading());
//    System.out.println("Velocity: " + elevatorTalon.getActiveTrajectoryVelocity());

//    double maxPower;
//    if (power < 0) {
//      if (position < softLimitRvs) {
//        SmartDashboard.putString("Debug Buffer", "hitUpperLimit");
//        elevatorTalon.set(controlMode, 0);
//        lastSetPower = 0;
//        return;
//      }
//      maxPower = Math.min(Math.abs(power),
//          (position - softLimitRvs) / Math.abs(BUFFER_LENGTH * HEIGHT_CONSTANT));
//      SmartDashboard.putString("Debug Buffer", "hitNearUpperLimit" + (position - softLimitRvs));
//      elevatorTalon.set(controlMode, Math.signum(power) * maxPower);
//      lastSetPower = Math.signum(power) * maxPower;
//      return;
//    }
//    if (position > softLimitFwd) {
//      SmartDashboard.putString("Debug Buffer", "hitLowerLimit");
//      elevatorTalon.set(controlMode, 0);
//      lastSetPower = 0;
//      return;
//    }
//    maxPower = Math.min(Math.abs(power),
//        (softLimitFwd - position) / Math.abs(BUFFER_LENGTH * HEIGHT_CONSTANT));
//    SmartDashboard.putString("Debug Buffer", "hitNearLowerLimit" + (position - softLimitFwd));
//    elevatorTalon.set(controlMode, Math.signum(power) * maxPower);
//    lastSetPower = Math.signum(power) * maxPower;

    elevatorTalon.set(controlMode, power);
  }

  /**
   * checks if elevator is close to current target
   *
   * @return true if currentposition is within deadband
   */
  //TODO check if still implemented
  public boolean isAtPosition() {
    return Math.abs(elevatorTalon.getClosedLoopError(0)) < deadband;
  }

  /**
   * @param rate value between -1 and 1 that represents the speed to which the elevator is set
   * @param dTime value in seconds
   */
  public void move(double rate, double dTime) {
    switch (mode) {
      case power:
      case switcheroo:
        if (isInPositionMode) {
          setPowerMode();
        }
        rate = Math.min(Math.max(rate, -1), 1);
        setElevatorPower(rate);
        break;
      case position:
        if (!isInPositionMode) {
          setPositionMode();
        }
        double dHeight = rate * speed * dTime;

        double height = currentTarget() + dHeight;
        setElevatorPosition(height);
        break;
    }

  }

  /**
   * Tries to make this forklift stay put (not move up and down)
   */
  public void hold(boolean driverPosMode) {
    switch (mode) {
      case power:
        if (isInPositionMode) {
          setPowerMode();
        }
        setElevatorPower(-holdPower);
        break;
      case switcheroo:
      case position:
        if (!isInPositionMode) {
          setPositionMode();
        }
    }

  }

  /**
   * moves elevator up or down by an amount of levels
   *
   * @param isUp - true=up, false=down
   */
  public void changeLevel(boolean isUp) {
    double currentPos = currentPosition();
    double target = currentPos;

    if (isUp) {
      for (double iLvl : LEVELS) {
        if (currentPos < iLvl - LEVEL_DEADBAND / 2) {
          target = iLvl;
          break;
        }
      }
    } else {
      for (int i = LEVELS.length - 1; i >= 0; i--) {
        if (currentPos > LEVELS[i] + LEVEL_DEADBAND / 2) {
          target = LEVELS[i];
          break;
        }
      }
    }
    setElevatorPosition(target);
  }

  /**
   * finds current error as reported by talon controls
   *
   * @return current error in inches
   */
  private double currentError() {
    return elevatorTalon.getClosedLoopError(0) / HEIGHT_CONSTANT;
  }

  /**
   * finds current target position of elevator
   *
   * @return current target in inches
   */
  private double currentTarget() {
//    return (elevatorTalon.getActiveTrajectoryPosition() - zeroPosition) / HEIGHT_CONSTANT;
    return 0;
  }

  /**
   * finds current position
   *
   * @return current position in inches
   */
  private double currentPosition() {
    SmartDashboard.putNumber("EMERGENCY RAW POT VALUE", elevatorTalon.getSelectedSensorPosition(0));
    return (elevatorTalon.getSelectedSensorPosition(0) - zeroPosition) / HEIGHT_CONSTANT;
  }

  /**
   * finds current speed
   *
   * @return speed in in/sec
   */
  private double currentSpeed() {
    return elevatorTalon.getSelectedSensorVelocity(0) / HEIGHT_CONSTANT;
  }

  public Mode currentMode() {
    return mode;
  }

  /**
   * updates smartdashboard
   */
  public void updateSmartDashboard() {
    SmartDashboard.putString("MODE", isInPositionMode ? "PositionMode" : "PowerMode");
    SmartDashboard.putNumber("RawPotValue", elevatorTalon.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Last setPower()", lastSetPower);
    SmartDashboard.putNumber("CurrentPosition", currentPosition());
    SmartDashboard.putNumber("CurrentTarget", currentTarget());
    SmartDashboard.putNumber("CurrentError", currentError());
    SmartDashboard.putNumber("ZeroPosition", zeroPosition);
  }

  public enum Mode {
    power, position, switcheroo
  }
}
