package frc.team2974.robot.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team2974.robot.autonomous.InitElevator;

/**
 * This autonomous command is used when we want our autonomous to do absolutely nothing
 */
public class DoNothing extends CommandGroup {

  public DoNothing() {
    addSequential(new InitElevator());
  }
}