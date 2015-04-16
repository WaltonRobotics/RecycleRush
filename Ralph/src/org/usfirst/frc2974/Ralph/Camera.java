/**
 * 
 */

package org.usfirst.frc2974.Ralph;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Piyush Kancharlawar
 */
public class Camera extends Command{

	NetworkTable networkTable;
	Relay light = RobotMap.alignmentIndicatorLightSpike;

	/**
	 * 
	 */
	public Camera() {

		networkTable = NetworkTable.getTable("roborealm");

		SmartDashboard.putBoolean("is aligned", false);

		SmartDashboard.putNumber("green threshold", 0);
		SmartDashboard.putNumber("blue threshold", 0);
		SmartDashboard.putNumber("red threshold", 0);
	}
	
	protected void initialize() {
	}
	protected void execute() {
		if(Robot.oi.xbox.getButton(Gamepad.Button.A)) {
			light.set(Value.kOn);
		}else {
			light.set(Value.kOff);
		}
		System.out.println("LIGHT IS SET TO "+light.get().name());
	}
	protected void end() {
		light.free();
	}
	protected void interrupted() {
//		end();
	}
	protected boolean isFinished() {
		return false;
	}

	public void updateSmartDashboard() {
		double r, g, b;

		r = networkTable.getNumber("SUM_R", 0);
		g = networkTable.getNumber("SUM_G", 0);
		b = networkTable.getNumber("SUM_B", 0);

//		System.out.println(r + ", " + g + ", " + b);

		double redThreshold = SmartDashboard.getNumber("red threshold");
		double greenThreshold = SmartDashboard.getNumber("green threshold");
		double blueThreshold = SmartDashboard.getNumber("blue threshold");

		if (b >= blueThreshold && g <= greenThreshold && r <= redThreshold)
			SmartDashboard.putBoolean("is aligned", true);
		else
			SmartDashboard.putBoolean("is aligned", false);
	}
}
