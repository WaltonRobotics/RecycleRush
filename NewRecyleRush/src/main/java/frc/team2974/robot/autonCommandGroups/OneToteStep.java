package frc.team2974.robot.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team2974.robot.autonomous.GrabAndHold;
import frc.team2974.robot.autonomous.InitElevator;
import frc.team2974.robot.autonomous.MoveElevator;
import frc.team2974.robot.autonomous.MoveLinear;
import frc.team2974.robot.autonomous.Release;
import frc.team2974.robot.autonomous.Turn;
import frc.team2974.robot.autonomous.Wait;

public class OneToteStep extends CommandGroup {

  public OneToteStep() {
    addSequential(new InitElevator());
    addParallel(new GrabAndHold());
    addSequential(new Wait(2));
    addParallel(new MoveElevator(20));
    addSequential(new Wait(2));
    addSequential(new MoveLinear(6, .4));
    addSequential(new Turn(.75, -1));
    addSequential(new MoveElevator(6));
    addParallel(new MoveElevator(1));
    addSequential(new Release());
  }
}
