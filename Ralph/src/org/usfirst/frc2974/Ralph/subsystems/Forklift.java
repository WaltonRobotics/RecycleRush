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
	private boolean clawIsFullyOpen;
	private boolean limitSwitchOn;
	//TODO get input from limit switch
	
	CANTalon elevatorTalon = new CANTalon(0);
	CANTalon clawTalon = new CANTalon(0);
	// TODO getMotorReference 
	Encoder liftEncoder = new Encoder(0, 0);
	
	public Forklift()
	{
		isLowered = false;
		isHolding = false;
		clawIsFullyOpen = false;
		limitSwitchOn = false;
	}
	
	public void checkFullyOpen()
	{
		if(limitSwitchOn){
			clawIsFullyOpen = true;
		}
		else {
			clawIsFullyOpen = false;
		}
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
	public void raiseLowVar(double power)
	{
		if(power <= 1 && power >= -1){
		elevatorTalon.set(power);
		}
	}
	
	//raises to max height(set # of levels?)
	public void raiseToTop()
	{

	}
	
	//same logic as raiseToTop
	public void lowerToBottom()
	{
		isLowered = true;
	}
	
	//open/close a variable amount
	public void openCloseVar(double power)
	{
		if(power <= 1 && power >= -1){
		clawTalon.set(power);
		}
	}
	
	//open all the way
	public void fullyOpen()
	{
		while(!clawIsFullyOpen){
			
			clawTalon.set(1);
			checkFullyOpen();
			if(clawIsFullyOpen)
				break;
		}
		clawIsFullyOpen = false;
	}
	//close all the way
	public void fullyClose()
	{
		
	}
	
	public void grab()
	{
		isHolding = true;
	}
	
	public void drop()
	{
		lowerToBottom();
		isHolding = false;
	}
	
	public void resetClaw()
	{
		fullyOpen();
		lowerToBottom();		
	}
	
	/* Make stack a command
	 * public void stack(double level)
	{
		grab();
		raiseLowLevel(level);//lifts			
		drop();
	}
	*/
}
