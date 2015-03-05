package org.usfirst.frc2974.Ralph.subsystems;
import java.util.Iterator;

import org.usfirst.frc2974.Ralph.RobotMap;
import org.usfirst.frc2974.Ralph.commands.UpDownTeleop;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
//Forklift subsystem
//Robot will have:
//Potentiometer to see lift's position on robot
//switch to see bottom
//2 CAN Talons - 1 for elevator, 1 for claw
//-possibly- a brake on elevator(PID controlled), need to turn on/off;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**DESIGNERS' NOTE: DO NOT USE A VALUE OF 1 FOR A PARAMETER FOR ANYTHING UNLESS YOU 
*WANT THE ROBOT TO LAUNCH ITS PARTS AT HIGH SPEEDS AT SOMETHING
*.....seriously
*/

public final class Forklift extends Subsystem
{
	private static int timesIncremented=0;
	
	public void initDefaultCommand(){
		
	}

	public final double NEUTRAL_POS = 6;
	//after elevator is zeroed, raises to this for safe driving

	private double zeroPosition = 0;
	private double speed = 0;
	private double lastSetPower=0;
	
	//TODO get input from limit switch
	private double rampRate;
	private int izone;
	private double p = 10;
	private double i = 0;
	private double d = 0;
	private double f = 0;
	private double deadband = 0.25;
	public final int profile1 = 1;
	private int softLimitFwd;
	private int softLimitRvs;
	
	public final double LEVEL_DEADBAND = 1;
	public final double LEVEL_MULTIPLIER = 12;
	//TODO determine by how much the level # (1, 2, 3) must be multiplied to get postiion to raise the arm
	//pos is in rotations: LEVEL_MULTIPLIERS = # rotations to raise the tote 1 level
	public final double HEIGHT_CONSTANT = -12.1635; // Calibrated against 
//	public final double HEIGHT_CONSTANT = 1;
	public final double MAX_POSITION = 55;//to be calibrated

	CANTalon elevatorTalon; 
	private boolean isInPositionMode = false;
	
	private final double[] LEVELS = {0, 12, 24, 36, 48, 60}; 
	private final double ACCEL_OVERSHOOT = .15;
	private final double BUFFER_LENGTH = 4;
	private final boolean POWER_RESPECTS_LIMITS = true;
	
	private Mode mode;
	
	public Forklift()
	{
		
		elevatorTalon = RobotMap.elevatorTalon;

		elevatorTalon.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);//potentiometer gives feedback
		elevatorTalon.reverseSensor(true);
		
		Preferences prefs = Preferences.getInstance();
		
		softLimitFwd = prefs.getInt("E_SoftLimitForward", -400);
		softLimitRvs = prefs.getInt("E_SoftLimitReverse", -800);
				
		elevatorTalon.setForwardSoftLimit(softLimitFwd);
		elevatorTalon.setReverseSoftLimit(softLimitRvs);
		elevatorTalon.enableForwardSoftLimit(true);
		elevatorTalon.enableReverseSoftLimit(true);
		
		setPowerMode();
	}
	//These are methods used in ForkliftZero command
//	public void increment(final double amount){
//		elevatorTalon.set(elevatorTalon.getSetpoint()+amount);
//		if(amount<0){
//			timesIncremented--;
//			return;
//		}
//		timesIncremented++;
//	}
//	
//	public int timesIncremented(){
//		return timesIncremented;
//	}
	
	/**
	 * sets elevator to powermode
	 */
	private void setPowerMode()
	{
		elevatorTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);
		if(!POWER_RESPECTS_LIMITS)
		{
			elevatorTalon.enableForwardSoftLimit(false);
			elevatorTalon.enableReverseSoftLimit(false);
		}
		//will there be brake functionality?
		elevatorTalon.enableControl();
		isInPositionMode = false;
	}
	
	/**
	 * sets elevtor to positionmode
	 */
	private void setPositionMode()
	{
		Preferences prefs = Preferences.getInstance();
		p = prefs.getDouble("E_P", 3);
		i = prefs.getDouble("E_I", 0.02);
		d = prefs.getDouble("E_D", 0.0);
		izone = prefs.getInt("E_Izone", 5);
		deadband = Math.abs(prefs.getDouble("E_Deadband", 0.25) * HEIGHT_CONSTANT);
		rampRate = Math.abs(prefs.getDouble("E_RampRate", 10));
		elevatorTalon.setPID(p, i, d, f, izone, rampRate, profile1);
		
		softLimitFwd = prefs.getInt("E_SoftLimitForward", -400);
		softLimitRvs = prefs.getInt("E_SoftLimitReverse", -800);
		
		speed = prefs.getDouble("E_Speed", 1);
		
		elevatorTalon.setForwardSoftLimit(softLimitFwd);
		elevatorTalon.setReverseSoftLimit(softLimitRvs);
		elevatorTalon.enableForwardSoftLimit(true);
		elevatorTalon.enableReverseSoftLimit(true);
		
		elevatorTalon.changeControlMode(CANTalon.ControlMode.Position);
		
		double speed = currentSpeed();
		double overshoot = (speed*speed)*Math.signum(speed)*ACCEL_OVERSHOOT/2;
		SmartDashboard.putNumber("Debug_Overshoot", overshoot);
		SmartDashboard.putNumber("Debug_Position", currentPosition());
		
		elevatorTalon.set(elevatorTalon.getPosition() + overshoot*HEIGHT_CONSTANT);
		elevatorTalon.enableControl();
		
		isInPositionMode = true;
	}
	
	public enum Mode{
		power, position, switcheroo;
		}
	
	/**
	 * sets mode for elevator
	 * @param mode the mode to be in power or position
	 */
	public void setMode(Mode mode)
	{
		if(mode == Mode.power && !(this.mode == Mode.power))
		{
			setPowerMode();
			this.mode = mode;
		}
		else if(mode == Mode.position && !(this.mode == Mode.position))
		{
			setPositionMode();
			this.mode = mode;
		}
		else if(mode == Mode.switcheroo && !(this.mode == Mode.switcheroo))
		{
			setPositionMode();
			this.mode= mode;
		}
	}
	
	
	
	/**
	 * returns if the forklift is at the bottom and has closed the limit switch
	 * @return true if the elevator has triggered proximity switch
	 */
	public boolean isZero()
	{
		return RobotMap.digital0.get();
	}
	
	

	/**
	 * resets potentiometer to such that position zero is current position
	 */
	public void resetPot()
	{
		zeroPosition = elevatorTalon.getPosition();
	}	
	
	/**
	 * moves elevator in position mode
	 * @param height - target height in inches relative to last zero position
	 */
	public void setElevatorPosition(double height)
	{
		double pos = HEIGHT_CONSTANT*height+zeroPosition;
		pos = Math.min(softLimitFwd, Math.max(softLimitRvs, pos));
		elevatorTalon.set(pos);
		
	}
	
	/**
	 * moves elevator in power mode
	 * 
	 * power is cutoff outside of softlimits as defined by preferences
	 * power is reduced as limits are approached
	 * 
	 * @param power - power of motor
	 */
	public void setElevatorPower(double power)
	{
		double position = elevatorTalon.getPosition();
		double maxPower;
		if(power<0)
		{
			if(position<softLimitRvs)
			{
				SmartDashboard.putString("Debug Buffer", "hitUpperLimit");
				elevatorTalon.set(0);
				lastSetPower = 0;
				return;
			}
			maxPower = Math.min(Math.abs(power), 
					(position - softLimitRvs)/Math.abs(BUFFER_LENGTH*HEIGHT_CONSTANT));
			SmartDashboard.putString("Debug Buffer", "hitNearUpperLimit" + (position - softLimitRvs));
			elevatorTalon.set(Math.signum(power)*maxPower);
			lastSetPower = Math.signum(power)*maxPower;
			return;
		}		
		if(position>softLimitFwd)
		{
			SmartDashboard.putString("Debug Buffer", "hitLowerLimit");
			elevatorTalon.set(0);
			lastSetPower = 0;
			return;
		}
		maxPower = Math.min(Math.abs(power), 
				(softLimitFwd - position)/Math.abs(BUFFER_LENGTH*HEIGHT_CONSTANT));
		SmartDashboard.putString("Debug Buffer", "hitNearLowerLimit" + (position - softLimitFwd));
		elevatorTalon.set(Math.signum(power)*maxPower);
		lastSetPower = Math.signum(power)*maxPower;
	}
	
	/**
	 * checks if elevator is close to current target
	 * @return true if currentposition is within deadband
	 */
	//TODO check if still implemented
	public boolean isAtPosition()
	{
		return Math.abs(elevatorTalon.getClosedLoopError()) < deadband;		
	}
	/**
	 * 
	 * @param rate value between -1 and 1 that represents the speed to which the elevator is set
	 * @param dTime value in seconds
	 */
	public void move(double rate, double dTime)
	{		
		switch(mode)
		{
		case power:			
		case switcheroo:
			if(isInPositionMode)
			{
				setPowerMode();
			}
			rate= Math.min(Math.max(rate,-1), 1);		
			setElevatorPower(-rate);
			break;
		case position:
			if(!isInPositionMode)
			{
				setPositionMode();
			}
			double dHeight = rate * speed * dTime;
			
			double height = currentTarget() + dHeight;
			setElevatorPosition(height);
			break;
		}
		
	}
	
	double holdPower = .05;
	/**
	 * Tries to make this forklift stay put (not move up and down)
	 * @return
	 */
	public void hold(boolean driverPosMode){
		switch(mode)
		{
		case power:
			if(isInPositionMode)
			{
				setPowerMode();
			}
			setElevatorPower(-holdPower);	
			break;
		case switcheroo:
		case position:
			if(!isInPositionMode)
			{
				setPositionMode();
			}
		}		
	}
	
	/**
	 * moves elevator up or down by an amount of levels
	 * @param isUp - true=up, false=down
	 */
	public void changeLevel(boolean isUp)
	{
		double currentPos = currentPosition();
		double target = currentPos;
		
		if(isUp)
		{			
			for(double iLvl: LEVELS)
			{
				if(currentPos < iLvl-LEVEL_DEADBAND/2)
				{
					target = iLvl;	
					break;
				}
			}
		}
		
		else
		{
			for(int i=LEVELS.length-1; i>=0; i--)
			{
				if(currentPos > LEVELS[i] + LEVEL_DEADBAND/2)
				{
					target = LEVELS[i];
					break;
				}
			}
		}
		setElevatorPosition(target);
	}
	
	/**
	 * finds current error as reported by talon controls
	 * @return current error in inches
	 */
	public double currentError()
	{
		return elevatorTalon.getClosedLoopError()/HEIGHT_CONSTANT;
	}
	
	/**
	 * finds current target position of elevator
	 * @return current target in inches
	 */
	public double currentTarget()
	{
		return (elevatorTalon.getSetpoint()-zeroPosition)/HEIGHT_CONSTANT;
	}
	
	/**
	 * finds current position
	 * @return current position in inches
	 */
	public double currentPosition()
	{
		return (elevatorTalon.getPosition()-zeroPosition)/HEIGHT_CONSTANT;
	}
	
	/**
	 * finds current speed
	 * @return speed in in/sec
	 */
	public double currentSpeed()
	{
		return elevatorTalon.getSpeed()/HEIGHT_CONSTANT;
	}
	
	public Mode currentMode()
	{
		return mode;
	}
	/**
	 * updates smartdashboard
	 */
	public void updateSmartDashboard(){
		SmartDashboard.putString("MODE", isInPositionMode?"PositionMode":"PowerMode");
		SmartDashboard.putNumber("RawPotValue", elevatorTalon.getPosition());
		SmartDashboard.putNumber("Last setPower()",lastSetPower);
		SmartDashboard.putNumber("CurrentPosition", currentPosition());
		SmartDashboard.putNumber("CurrentTarget", currentTarget());
		SmartDashboard.putNumber("CurrentError", currentError());
		SmartDashboard.putNumber("ZeroPosition", zeroPosition);
	}
}
