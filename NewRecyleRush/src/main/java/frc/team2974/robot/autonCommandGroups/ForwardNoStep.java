package frc.team2974.robot.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team2974.robot.autonomous.InitElevator;
import frc.team2974.robot.autonomous.MoveLinear;

/**
 *
 */
public class ForwardNoStep extends CommandGroup {

  public ForwardNoStep() {
    addSequential(new InitElevator());
    addSequential(new MoveLinear(1.6, 1));

  }
}
