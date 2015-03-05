package org.usfirst.frc2974.Ralph.subsystems;

//import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
//import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
//
///**
// *
// */
//public class Camera extends Subsystem {
//    
//    private NetworkTable networkTable;
//
//    public Camera()
//    {
//    	networkTable = NetworkTable.getTable("CAMERA");
//    	
//    }
//    public void initDefaultCommand() {
//        // Set the default command for a subsystem here.
//        //setDefaultCommand(new MySpecialCommand());
//    }
//    
//    public Double getBlobCount()
//    {
//    	try
//    	{
//    	return networkTable.getNumber("BLOB_COUNT",0);
//    	}
//    	catch(TableKeyNotDefinedException e)
//    	{
//    		e.printStackTrace(System.err);
//    	}
//    	return null;
//    }
// 
//
//
//	public Double getCOGX(int index)
//    {
//    	try
//    	{
//    		NumberArray array = new NumberArray();
//    		networkTable.retrieveValue("COG_X", array);
//    		return array.get(index);
//    	}
//    	catch(TableKeyNotDefinedException e)
//    	{
//    		e.printStackTrace(System.err);
//    	}
//    	return null;
//    }
//	
//	public Double getCOGY(int index) 
//    {
//    	try
//    	{
//    		NumberArray array = new NumberArray();
//    		networkTable.retrieveValue("COG_Y", array);
//    		return array.get(index);
//    	}
//    	catch(TableKeyNotDefinedException e)
//    	{
//    		e.printStackTrace(System.err);
//    	}
//    	return null;
//    }
//	
//	public Double getArea(int index) 
//    {
//    	try
//    	{
//    		NumberArray array = new NumberArray();
//    		networkTable.retrieveValue("AREA", array);
//    		return array.get(index);
//    	}
//    	catch(TableKeyNotDefinedException e)
//    	{
//    		e.printStackTrace(System.err);
//    	}
//    	return null;
//    }
//	
//	
//    
//}
//
