package org.usfirst.frc2974.Ralph.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
//Forklift subsystem
//Robot will have:
//Potentiometer to see lift's position on robot
//switch to see bottom
//2 CAN Talons - 1 for elevator, 1 for claw
//-possibly- a brake on elevator(PID controlled), need to turn on/off;

/**DESIGNERS' NOTE: DO NOT USE A VALUE OF 1 FOR A PARAMETER FOR ANYTHING UNLESS YOU 
*WANT THE ROBOT TO LAUNCH ITS PARTS AT HIGH SPEEDS AT SOMETHING
*.....seriously
*/

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
	private double zeroPosition = 0;
	
	//TODO get input from limit switch
	private double rampRate = 10;
	private int izone = 0;
	private double p = 0.1;
	private double i = 0;
	private double d = 0;
	private double f = 0;
	//TODO set values for p, i, d 
	public final double LEVEL_MULTIPLIER = 20;
	//TODO determine by how much the level # (1, 2, 3) must be multiplied to get postiion to raise the arm
	//pos is in rotations: LEVEL_MULTIPLIERS = # rotations to raise the tote 1 level
	public final double HEIGHT_CONSTANT = 1;//needs calibrating[see board picture on wiki]
	
	public final double MAX_POSITION_ERROR = 2;//placeholder value
	
	public final double MAX_POSITION = 500;//to be calibrated
	
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
		clawTalon.ConfigRevLimitSwitchNormallyOpen(true);
		clawTalon.ConfigFwdLimitSwitchNormallyOpen(true);
		clawTalon.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		
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
		elevatorTalon.set(elevatorTalon.getPosition());
		elevatorTalon.enableControl();
	}
	
	//returns if the forklift is at the bottom and has closed the limit switch
	public boolean isZero()
	{
		return elevatorTalon.isRevLimitSwitchClosed();
	}
	
	public boolean clawIsOpen()
	{
		return clawTalon.isFwdLimitSwitchClosed();
	}
	
	public boolean clawIsClosed()
	{
		return clawTalon.isRevLimitSwitchClosed();
	}
	
	public void resetPot()
	{
		zeroPosition = elevatorTalon.getPosition();		
	}	
	
	// <\methods used in ForkliftZero>
	

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
	public void setElevatorPosition(double height)
	{		
		double pos = HEIGHT_CONSTANT*height+zeroPosition;
		elevatorTalon.set(pos);
	}
	
	//raises/lowers to a variable height(not dependent on levels)
	public void setElevatorPower(double power)
	{
		setPowerMode();
		power= Math.min(Math.max(power,-1), 1);		
		elevatorTalon.set(power);		
	}
	
	public boolean isAtPosition()
	{
		return Math.abs(elevatorTalon.getClosedLoopError()) < MAX_POSITION_ERROR;		
	}
	
	public void incrementElevatorPos(double dheight)
	{
		double dpos = HEIGHT_CONSTANT*dheight;
		double pos = elevatorTalon.getSetpoint()+dpos;
		pos= Math.min(Math.max(pos,zeroPosition), MAX_POSITION);	
		elevatorTalon.set(pos);
	}
	
	public double currentError()
	{
		return elevatorTalon.getClosedLoopError()/HEIGHT_CONSTANT;
	}
	
	public double currentTarget()
	{
		return (elevatorTalon.getSetpoint()-zeroPosition)/HEIGHT_CONSTANT;
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
	//positive variable = close
	//negative = open
	//power- value between -1 and 1(DO NOT USE 1!!!!! PLEASE!!!!)
	public void setClawMotor(double power)
	{
		power=Math.max(Math.min(power, 1), -1);			
		clawTalon.set(power);	
	}
	
	//open all the way
//	public void fullyOpen()
//	{
//		while(!clawIsFullyOpen){
//			
//			clawTalon.set(.1);
//			checkFullyOpen();
//			if(clawIsFullyOpen)
//				break;
//		}
//		clawIsFullyOpen = true;
//	}
	//no loops in subsystems-need to make this a command
	//close all the way
//	public void fullyClose()
//	{
//		while(clawIsFullyOpen){
//			
//			clawTalon.set(-.1);
//			checkFullyOpen();
//			if(!clawIsFullyOpen)
//				break;
//		}
//		clawIsFullyOpen = false;
//	}
	//no practical implementation
	
//	public void grab()
//	{
//		isHolding = true;
//		//TODO implement the potentiometer/encoder for the claw motor
//	}
	//make command
	
//	public void drop()
//	{
//		setElevatorPower(-.1);
//		clawTalon.set(-.1);
//		isHolding = false;
//	} will be a command or group command
	
//	public void resetClaw()
//	{
//		fullyOpen();
//		raiseLowVar(-.1);		
//	} no practical implementation
	
	/* Make stack a command
	 * public void stack(double level)
	{
		grab();
		raiseLowLevel(level);//lifts			
		drop();
	}
	*/
}
