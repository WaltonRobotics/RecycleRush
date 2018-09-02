package frc.team2974.robot.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team2974.robot.autonomous.InitElevator;
import frc.team2974.robot.autonomous.MoveLinear;
import frc.team2974.robot.autonomous.Turn;

/**
 *
 */
public class ForwardStep extends CommandGroup {

  public ForwardStep() {
    addSequential(new InitElevator());
    addSequential(new MoveLinear(6, .4));
    addSequential(new Turn(.75, -1));

  }
}
