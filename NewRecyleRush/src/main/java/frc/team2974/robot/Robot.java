// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.team2974.robot;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.autonCommandGroups.DoNothing;
import frc.team2974.robot.autonCommandGroups.ForwardDynamic;
import frc.team2974.robot.autonCommandGroups.ForwardNoStep;
import frc.team2974.robot.autonCommandGroups.ForwardStep;
import frc.team2974.robot.autonCommandGroups.OneToteDynamic;
import frc.team2974.robot.autonCommandGroups.OneToteNoStep;
import frc.team2974.robot.autonCommandGroups.OneToteStep;
import frc.team2974.robot.commands.Drive;
import frc.team2974.robot.commands.Grab;
import frc.team2974.robot.commands.Stop;
import frc.team2974.robot.commands.UpDownTeleop;
import frc.team2974.robot.subsystems.DriveTrain;
import frc.team2974.robot.subsystems.Forklift;
import frc.team2974.robot.subsystems.Grabber;
import frc.team2974.robot.subsystems.Inputs;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the IterativeRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {

  public static OI oi;
  public static DriveTrain driveTrain;
  public static Forklift forklift;
  public static Inputs inputs;
  public static Grabber grabber;
  private static Camera cam;
  private Command autonomousCommand;
  private SendableChooser<Command> autoChooser;

  /**
   * This function is run when the robot is first started up and should be used for any initialization code.
   */
  public void robotInit() {

    SmartDashboard.putNumber("AutonMoveForwardTime", 6); // used in dynamic
    // movelinear//
    // TODO remove
    // eventually
    RobotMap.init();

    driveTrain = new DriveTrain();
    forklift = new Forklift();
    grabber = new Grabber();
    cam = new Camera();

    // After subsystems. If the OI creates Commands
    // (which it very likely will), subsystems are not guaranteed to be
    // constructed yet. Thus, their requires() statements may grab null
    // pointers. Bad news. Don't move it.

    oi = new OI();
    inputs = new Inputs();

    // TODO decrease wait time for auton
    autoChooser = new SendableChooser<>();
    autoChooser.addDefault("Do Nothing", new DoNothing());
    autoChooser.addObject("No Step - one tote", new OneToteNoStep());
    autoChooser.addObject("Step - one tote", new OneToteStep());
    autoChooser.addObject("No step - forward", new ForwardNoStep());
    autoChooser.addObject("Step - forward", new ForwardStep());
    autoChooser.addObject("Dynamic - one tote", new OneToteDynamic());
    autoChooser.addObject("Dynamic - forward", new ForwardDynamic());
    SmartDashboard.putData("PICK AN AUTONOMOUS", autoChooser);
  }

  /**
   * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
   */
  public void disabledInit() {
  }


  public void disabledPeriodic() {
    Scheduler.getInstance().run();

    showElevatorValues();

  }

  public void showElevatorValues() {

    SmartDashboard.putNumber("Elevator", RobotMap.elevatorTalon.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Raw pot", RobotMap.elevatorTalon.getSensorCollection().getAnalogInRaw());
    SmartDashboard
        .putBoolean("Forward limit is Closed?", RobotMap.elevatorTalon.getSensorCollection().isFwdLimitSwitchClosed());
    SmartDashboard
        .putBoolean("Reverse Limit is Closed?", RobotMap.elevatorTalon.getSensorCollection().isRevLimitSwitchClosed());
    SmartDashboard
        .putBoolean("Reverse Limit is Closed?", RobotMap.elevatorTalon.getSensorCollection().isRevLimitSwitchClosed());
    SmartDashboard.putNumber("Left Y", -Robot.oi.xbox.getLeftY());

    StickyFaults stickyFaults = new StickyFaults();
    RobotMap.elevatorTalon.getStickyFaults(stickyFaults);
    RobotMap.elevatorTalon.clearStickyFaults(0);

    SmartDashboard.putBoolean("Limit up", stickyFaults.ForwardSoftLimit);
    SmartDashboard.putBoolean("Limit down", stickyFaults.ReverseSoftLimit);

    SmartDashboard.putNumber("Soft limit up value", RobotMap.elevatorTalon.configGetParameter(
        ParamEnum.eForwardSoftLimitThreshold, 0, 0));
    SmartDashboard.putNumber("Soft limit down value", RobotMap.elevatorTalon.configGetParameter(
        ParamEnum.eReverseSoftLimitThreshold, 0, 0));

  }

  public void autonomousInit() {
    autonomousCommand = autoChooser.getSelected();
    autonomousCommand.start();

  }

  /**
   * This function is called periodically during autonomous
   */
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    showElevatorValues();
  }

  public void teleopInit() {

    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    Scheduler.getInstance().add(new UpDownTeleop());
    Scheduler.getInstance().add(new Grab());
    Scheduler.getInstance().add(new Drive());
    Scheduler.getInstance().add(new Camera());
  }

  /**
   * This function is called periodically during operator control
   */
  public void teleopPeriodic() {


    if ((Robot.oi.xbox.getButton(Gamepad.Button.B))) {
      Scheduler.getInstance().add(new Stop());
    }
    SmartDashboard.putData(Scheduler.getInstance());
    cam.updateSmartDashboard();
    Scheduler.getInstance().run();

    SmartDashboard.putNumber("Elevator", RobotMap.elevatorTalon.getSelectedSensorPosition(0));
    System.out.println("Raw pot: " + RobotMap.analog0.getValue());

    showElevatorValues();
  }
}
