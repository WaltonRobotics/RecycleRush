package org.usfirst.frc2974.Ralph.autonCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2974.Ralph.autonomous.GrabAndHold;
import org.usfirst.frc2974.Ralph.autonomous.InitElevator;
import org.usfirst.frc2974.Ralph.autonomous.MoveElevator;
import org.usfirst.frc2974.Ralph.autonomous.Release;
import org.usfirst.frc2974.Ralph.autonomousDynamic.MoveLinear_Dynamic;
import org.usfirst.frc2974.Ralph.autonomousDynamic.Turn_Dynamic;
import org.usfirst.frc2974.Ralph.autonomousDynamic.Wait_Dynamic;

/**
 *
 */
public class OneTote_Dynamic extends CommandGroup {

	public OneTote_Dynamic() {
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
		addSequential(new Wait_Dynamic(autonFirstWait, "AutonFirstWait"));
		addParallel(new MoveElevator(20));
		addSequential(new Wait_Dynamic(autonSecondWait, "AutonSecondWait"));
		addSequential(new MoveLinear_Dynamic(autonMoveForwardTime, autonMoveForwardSpeed));
		addSequential(new Turn_Dynamic(autonTurnTime, Math.signum(autonTurnTime)));
		addSequential(new MoveElevator(6));
		addParallel(new MoveElevator(1));
		addSequential(new Release());
	}
}
