package org.usfirst.frc2974.Ralph.subsystems;
//Forklift subsystem
//Robot will have:
//Potentiometer/encoder to see lift's position on robot
//switch to see bottom
//2 CAN Talons - 1 for elevator, 1 for claw
//-possibly- a brake on elevator(PID controlled), need to turn on/off;

public class Forklift 
{
	private final double oneToteLevel = 0;//placeholder value
	private boolean isLowered;
	private boolean isHolding;
	
	public Forklift()
	{
		isLowered = false;
		isHolding = false;
	}
	
	//raises/lowers by set number of levels(oneToteLevel* preset number of levels)
	public void raiseLow()
	{
		isLowered = false;
	}
	
	//raises/lowers by input number of levels(oneToteLevel*level)
	public void raiseLow(double level)
	{
		isLowered = false;
	}
	
	//raises/lowers to a variable height(not dependent on levels)
	public void raiseLowVar(double var)
	{
		
	}
	
	
	//raises to max height(set # of levels?)
	public void raiseToTop()
	{
		isLowered = false;
	}
	//same logic as raiseToTop
	public void lowerToBottom()
	{
		isLowered = true;
	}
	//open/close a variable amount
	public void open(int x)
	{
		
	}
	
	public void close(int x)
	{
		
	}
	//open/close all the way
	public void open()
	{
		
	}
	
	public void close()
	{
		
	}
	
	//Maybe these should be commands instead of methods??? Probably, since they basically work solely by calling methods
	public void grab()
	{
		close();
		isHolding = true;
	}
	
	public void drop()
	{
		lowerToBottom();
		open();
		isHolding = false;
	}
	
	public void resetClaw()
	{
		open();
		lowerToBottom();		
	}
	
	public void stack()
	{
		grab();
		raiseLow();//lifts			
		drop();
	}
}
