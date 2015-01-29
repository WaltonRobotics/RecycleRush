package org.usfirst.frc2974.Ralph.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
//Forklift subsystem
//Robot will have:
//Potentiometer/encoder to see lift's position on robot
//switch to see bottom
//2 CAN Talons - 1 for elevator, 1 for claw
//-possibly- a brake on elevator(PID controlled), need to turn on/off;

public class Forklift extends Subsystem
{
	
	public void initDefaultCommand(){
		setDefaultCommand(null);
	}
	private final double oneToteLevel = 0;//placeholder value
	private boolean isLowered;
	private boolean isHolding;
	
	CANTalon elevator;
	CANTalon claw;
	Encoder encoder = new Encoder(0, 0);
	
	public Forklift()
	{
		isLowered = false;
		isHolding = false;
	}
	
	//raises/lowers by input number of levels(oneToteLevel*level)
	public void raiseLowLevel(double level)
	{
		if(isLowered)
			isLowered = false;
		else if(!isLowered)
			isLowered = true;
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
	public void openClose(double x)
	{
		
	}
	
	//open/close all the way
	public void openClose()
	{
		
	}	
	
	//Maybe these should be commands instead of methods??? Probably, since they basically work solely by calling methods
	public void grab()
	{
		openClose();
		isHolding = true;
	}
	
	public void drop()
	{
		lowerToBottom();
		openClose();
		isHolding = false;
	}
	
	public void resetClaw()
	{
		openClose();
		lowerToBottom();		
	}
	
	public void stack(double level)
	{
		grab();
		raiseLowLevel(level);//lifts			
		drop();
	}
}
