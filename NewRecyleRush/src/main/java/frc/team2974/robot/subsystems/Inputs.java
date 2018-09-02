package frc.team2974.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.RobotMap;
import frc.team2974.robot.commands.ShowInputs;

/**
 * Shows Inputs
 */
public class Inputs extends Subsystem {


  private final DigitalInput digital0 = RobotMap.digital0;
  private final DigitalInput digital1 = RobotMap.digital1;
  private final DigitalInput digital2 = RobotMap.digital2;
  private final DigitalInput digital3 = RobotMap.digital3;
  private final DigitalInput digital4 = RobotMap.digital4;
  private final DigitalInput digital5 = RobotMap.digital5;
  private final DigitalInput digital6 = RobotMap.digital6;
  private final DigitalInput digital7 = RobotMap.digital7;
  private final DigitalInput digital8 = RobotMap.digital8;
  private final DigitalInput digital9 = RobotMap.digital9;


  private final AnalogInput analog0 = RobotMap.analog0;
  private final AnalogInput analog1 = RobotMap.analog1;
  private final AnalogInput analog2 = RobotMap.analog2;
  private final AnalogInput analog3 = RobotMap.analog3;


  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new ShowInputs());
    // TODO Auto-generated method stub

  }

  public void updateSmartDashboard() {
    SmartDashboard.putData("Digital In 0", digital0);
    SmartDashboard.putData("Digital In 1", digital1);
    SmartDashboard.putData("Digital In 2", digital2);
    SmartDashboard.putData("Digital In 3", digital3);
    SmartDashboard.putData("Digital In 4", digital4);
    SmartDashboard.putData("Digital In 5", digital5);
    SmartDashboard.putData("Digital In 6", digital6);
    SmartDashboard.putData("Digital In 7", digital7);
    SmartDashboard.putData("Digital In 8", digital8);
    SmartDashboard.putData("Digital In 9", digital9);

    SmartDashboard.putData("Analog In 0", analog0);
    SmartDashboard.putData("Analog In 1", analog1);
    SmartDashboard.putData("Analog In 2", analog2);
    SmartDashboard.putData("Analog In 3", analog3);

  }


}

