/**
 * 
 */

package org.usfirst.frc2974.Ralph;


import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author Piyush Kancharlawar
 */
public class Camera
{

	NetworkTable	networkTable;
	
	/**
	 * 
	 */
	public Camera( )
	{

		networkTable = NetworkTable.getTable("roborealm");
		
		SmartDashboard.putBoolean("is aligned", false);
		
		SmartDashboard.putNumber("green threshold", 0);
		SmartDashboard.putNumber("blue threshold", 0);
		SmartDashboard.putNumber("red threshold", 0);
	}


	public void updateSmartDashboard( )
	{
		double r, g, b;
		
		r = networkTable.getNumber("SUM_R", 0);
		g = networkTable.getNumber("SUM_G", 0);
		b = networkTable.getNumber("SUM_B", 0);
		
		System.out.println(r + ", "+ g + ", " + b);
		
		double redThreshold = SmartDashboard.getNumber("red threshold");
		double greenThreshold = SmartDashboard.getNumber("green threshold");
		double blueThreshold = SmartDashboard.getNumber("blue threshold");

		if ( b >= blueThreshold && g <= greenThreshold && r <= redThreshold )
			SmartDashboard.putBoolean("is aligned", true);
		else
			SmartDashboard.putBoolean("is aligned", false);
	}
}
