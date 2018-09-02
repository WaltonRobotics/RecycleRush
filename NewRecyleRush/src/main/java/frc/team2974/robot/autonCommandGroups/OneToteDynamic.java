package frc.team2974.robot.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2974.robot.autonomous.GrabAndHold;
import frc.team2974.robot.autonomous.InitElevator;
import frc.team2974.robot.autonomous.MoveElevator;
import frc.team2974.robot.autonomous.Release;
import frc.team2974.robot.autonomousDynamic.MoveLinearDynamic;
import frc.team2974.robot.autonomousDynamic.TurnDynamic;
import frc.team2974.robot.autonomousDynamic.WaitDynamic;

/**
 *
 */
public class OneToteDynamic extends CommandGroup {

  public OneToteDynamic() {
    double autonMoveForwardTime = 6;
    double autonMoveForwardSpeed = .4;
    double autonFirstWait = 2;
    double autonSecondWait = 2;
    double autonTurnTime = .75;

    SmartDashboard.putNumber("AutonMoveForwardTime", autonMoveForwardTime);
    SmartDashboard.putNumber("AutonMoveForwardSpeed", autonMoveForwardSpeed);
    SmartDashboard.putNumber("AutonFirstWait", autonFirstWait);
    SmartDashboard.putNumber("AutonSecondWait", autonSecondWait);
    SmartDashboard.putNumber("AutonTurnTime", autonTurnTime);
    addSequential(new InitElevator());
    addParallel(new GrabAndHold());
    addSequential(new WaitDynamic(autonFirstWait, "AutonFirstWait"));
    addParallel(new MoveElevator(20));
    addSequential(new WaitDynamic(autonSecondWait, "AutonSecondWait"));
    addSequential(new MoveLinearDynamic(autonMoveForwardTime, autonMoveForwardSpeed));
    addSequential(new TurnDynamic(autonTurnTime, Math.signum(autonTurnTime)));
    addSequential(new MoveElevator(6));
    addParallel(new MoveElevator(1));
    addSequential(new Release());
  }
}
