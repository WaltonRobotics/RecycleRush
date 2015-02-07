package org.usfirst.frc2974.Ralph.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 *
 */
public class Camera extends Subsystem {
    
    private NetworkTable networkTable;

    public Camera()
    {
    	networkTable = NetworkTable.getTable("CAMERA");
    	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getBlobCount()
    {
    	return networkTable.getNumber("BLOB_COUNT",0);
    }
 

	public double getCOGX(int index)
    {
    	try
    	{
    		NumberArray array = new NumberArray();
    		networkTable.retrieveValue("COG_X", array);
    		return array.get(index);
    	}
    	catch(TableKeyNotDefinedException tknde)
    	{
    		tknde.printStackTrace(System.err);
    	}
    }
    
}

