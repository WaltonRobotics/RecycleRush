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
	public final double NEUTRAL_POS = 6;//after elevator is zeroed, raises to this for safe driving
	//TODO get input from limit switch
	
	CANTalon elevatorTalon = new CANTalon(0);
	CANTalon clawTalon = new CANTalon(0);
	// TODO getMotorReference 
	//Encoder liftEncoder = new Encoder(0, 0);
	//DigitalInput limitSwitch = new DigitalInput(0);
	
	public Forklift()
	{
		//We need to remove/replace these placeholders 
		isLowered = false;
		isHolding = false;
		clawIsFullyOpen = false;
		limitSwitchOn = false;
		elevatorTalon.ConfigRevLimitSwitchNormallyOpen(true);//assuming up=fwd, rev=down
		elevatorTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);//assuming quad encoder,#s get bigger as we go up
	}
	//These are methods used in ForkliftZero command
	public void setPowerMode()
	{
		elevatorTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//will there be brake functionality???
	}
	
	public void setPositionMode()
	{
		elevatorTalon.changeControlMode(CANTalon.ControlMode.Position);		
	}
	
	public boolean isZero()
	{
		return elevatorTalon.isRevLimitSwitchClosed();
	}
	
	public void resetEncoder()
	{
		elevatorTalon.setPosition(0);
	}	
	
	// <\methods used in ForkliftZero>
	public void raiseToPosition(double pos)
	{
		elevatorTalon.set(pos);
	}
	
	public void checkFullyOpen()
	{
//		if(limitSwitchOn){
//			clawIsFullyOpen = true;
//		}
//		else {
//			clawIsFullyOpen = false;
//		}
	}
	//raises/lowers by input number of levels(oneToteLevel*level)
	//do we really need this one?-Armaan
	public void raiseLowLevel(double level)
	{
		//setPositionMode();				
		elevatorTalon.set(level);		
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
		//elevatorTalon.changeControlMode(CANTalon.ControlMode.Position);
		
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
			
			clawTalon.set(.1);
			checkFullyOpen();
			if(clawIsFullyOpen)
				break;
		}
		clawIsFullyOpen = true;
	}
	//close all the way
	public void fullyClose()
	{
		while(clawIsFullyOpen){
			
			clawTalon.set(-1);
			checkFullyOpen();
			if(!clawIsFullyOpen)
				break;
		}
		clawIsFullyOpen = false;
	}
	
	public void grab()
	{
		isHolding = true;
	}
	
	public void drop()
	{
		raiseLowVar(-.1);
		clawTalon.set(-.1);
		isHolding = false;
	}
	
	public void resetClaw()
	{
		fullyOpen();
		raiseLowVar(-.1);		
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
