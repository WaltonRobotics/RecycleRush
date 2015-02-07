package org.usfirst.frc2974.Ralph.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
//Forklift subsystem
//Robot will have:
//Potentiometer to see lift's position on robot
//switch to see bottom
//2 CAN Talons - 1 for elevator, 1 for claw
//-possibly- a brake on elevator(PID controlled), need to turn on/off;

public class Forklift extends Subsystem
{
	public void initDefaultCommand(){
		setDefaultCommand(null);
	}
	private boolean isHolding;
	private boolean clawIsFullyOpen;
	public final int profile1 = 1;
	public final double NEUTRAL_POS = 6;
	//after elevator is zeroed, raises to this for safe driving
	public final double TOP_POS = 12;
	//after elevator is zeroed, raises to top to clear arms of the stack
	public final double BOTTOM_POS = 0;
	//after elevator is zeroed, lowers to bottom. can be called from a command
	// using raiseToPosition(BOTTOM_POS)
	
	//TODO get input from limit switch
	private double rampRate = 0;
	private int izone = 0;
	private double p = 0;
	private double i = 0;
	private double d = 0;
	private double f = 0;
	//TODO set values for p, i, d 
	public final double LEVEL_MULTIPLIER = 20;
	//TODO determine by how much the level # (1, 2, 3) must be multiplied to get postiion to raise the arm
	//pos is in rotations: LEVEL_MULTIPLIERS = # rotations to raise the tote 1 level
	
	CANTalon elevatorTalon = new CANTalon(0); 
	CANTalon clawTalon = new CANTalon(0);
	// TODO get motor reference 
	//Encoder liftEncoder = new Encoder(0, 0);
	//DigitalInput limitSwitch = new DigitalInput(0);
	
	public Forklift()
	{
		isHolding = false;
		clawIsFullyOpen = false;
		elevatorTalon.ConfigRevLimitSwitchNormallyOpen(true);//assuming up = fwd, rev = down
		elevatorTalon.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);//potentiometer gives feedback
		
		/*elevatorTalon.setProfile(profile1);
		elevatorTalon.setCloseLoopRampRate(rampRate);
		elevatorTalon.setIZone(izone);
		elevatorTalon.setP(p);
		elevatorTalon.setI(i);
		elevatorTalon.setD(d);
		elevatorTalon.setF(f);*/
		elevatorTalon.setPID(p, i, d, f, izone, rampRate, profile1);
		
	}
	//These are methods used in ForkliftZero command
	public void setPowerMode()
	{
		elevatorTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//will there be brake functionality?
		elevatorTalon.enableControl();
	}
	
	public void setPositionMode()
	{
		elevatorTalon.changeControlMode(CANTalon.ControlMode.Position);	
		elevatorTalon.enableControl();
	}
	
	//returns if the forklift is at the bottom and has closed the limit switch
	public boolean isZero()
	{
		return elevatorTalon.isRevLimitSwitchClosed();
	}
	
	public void resetPot()
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
	//do we really need this one?-Armaan
	// we referene it in other methods-- keep for now
	
	//raises/lowers by input number of levels(oneToteLevel*level)
	public void raiseLowLevel(double level)
	{
		double pos = level * LEVEL_MULTIPLIER;
		setPositionMode();				
		elevatorTalon.set(pos);
	}
	
	//raises/lowers to a variable height(not dependent on levels)
	public void raiseLowVar(double power)
	{
		setPowerMode();
		if(power <= 1 && power >= -1){
		elevatorTalon.set(power);
		}
	}
	
	/*//raises to max height(set # of levels?)
	public void raiseToTop()
	{
		//elevatorTalon.raiseToPosition(TOP_POS);
		
	}
	
	//same logic as raiseToTop
	public void lowerToBottom()
	{
		isLowered = true;
	}
	*/
	//we don't really need these methods because we can reference the constant positions from the commands (TOP_POS, BOTTOM_POS)
	
	//open/close a variable amount
	public void openCloseVar(double power)
	{
		setPowerMode();
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
			
			clawTalon.set(-.1);
			checkFullyOpen();
			if(!clawIsFullyOpen)
				break;
		}
		clawIsFullyOpen = false;
	}
	
	public void grab()
	{
		isHolding = true;
		//TODO implement the potentiometer/encoder for the claw motor
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
