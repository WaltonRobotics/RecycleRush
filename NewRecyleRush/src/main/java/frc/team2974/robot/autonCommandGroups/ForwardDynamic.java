package frc.team2974.robot.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.autonomous.InitElevator;
import frc.team2974.robot.autonomous.MoveElevator;
import frc.team2974.robot.autonomousDynamic.MoveLinearDynamic;
import frc.team2974.robot.autonomousDynamic.TurnDynamic;

/**
 *
 */
public class ForwardDynamic extends CommandGroup {

  public ForwardDynamic() {
    double autonMoveForwardTime = 6;
    double autonMoveForwardSpeed = .4;
    double autonTurnTime = .75;

    SmartDashboard.putNumber("AutonMoveForwardTime", autonMoveForwardTime);
    SmartDashboard.putNumber("AutonMoveForwardSpeed", autonMoveForwardSpeed);
    SmartDashboard.putNumber("AutonTurnTime", autonTurnTime);
    addSequential(new InitElevator());
    addSequential(new MoveLinearDynamic(autonMoveForwardTime, autonMoveForwardSpeed));
    addSequential(new TurnDynamic(autonTurnTime, Math.signum(autonTurnTime)));
    addSequential(new MoveElevator(6));
    addParallel(new MoveElevator(1));
  }
}
