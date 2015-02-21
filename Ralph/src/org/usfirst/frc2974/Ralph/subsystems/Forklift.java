package org.usfirst.frc2974.Ralph.subsystems;
import org.usfirst.frc2974.Ralph.RobotMap;

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
		setDefaultCommand(null);
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
	
	public final double LEVEL_MULTIPLIER = 12;
	//TODO determine by how much the level # (1, 2, 3) must be multiplied to get postiion to raise the arm
	//pos is in rotations: LEVEL_MULTIPLIERS = # rotations to raise the tote 1 level
	public final double HEIGHT_CONSTANT = -12.1635; // Calibrated against 
//	public final double HEIGHT_CONSTANT = 1;
	public final double MAX_POSITION = 55;//to be calibrated

	CANTalon elevatorTalon; 
	private boolean isInPositionMode = false;
	
	public Forklift()
	{
		
		elevatorTalon = RobotMap.elevatorTalon;

		elevatorTalon.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);//potentiometer gives feedback
		elevatorTalon.reverseSensor(true);
		
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
	
	public void setPowerMode()
	{
		elevatorTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);

		elevatorTalon.enableForwardSoftLimit(false);
		elevatorTalon.enableReverseSoftLimit(false);
		//will there be brake functionality?
		elevatorTalon.enableControl();
		isInPositionMode = false;
	}
	
	public void setPositionMode()
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
		
		elevatorTalon.set(elevatorTalon.getPosition());
		elevatorTalon.enableControl();
		
		isInPositionMode = true;
	}
	
	//returns if the forklift is at the bottom and has closed the limit switch
	public boolean isZero()
	{
//		return elevatorTalon.isFwdLimitSwitchClosed();
		return false;//TODO read hall effect sensor /properly/
	}
	
	public boolean isPositionMode()
	{
		return isInPositionMode;
	}

	public void resetPot()
	{
		zeroPosition = elevatorTalon.getPosition();
	}	
	
	// <\methods used in ForkliftZero>
	

	public void setElevatorPosition(double height)
	{
		double pos = HEIGHT_CONSTANT*height+zeroPosition;
		pos = Math.min(softLimitFwd, Math.max(softLimitRvs, pos));
		elevatorTalon.set(pos);
		
	}
	
	public boolean isAtPosition()
	{
		return Math.abs(elevatorTalon.getClosedLoopError()) < deadband;		
	}
	/**
	 * 
	 * @param rate value between -1 and 1 that represents the speed to which the elevator is set
	 * @param dTime value in seconds
	 */
	public void move(double rate, double dTime){
		if(!isInPositionMode){

			rate= Math.min(Math.max(rate,-1), 1);		
			elevatorTalon.set(-rate);
			lastSetPower = -rate;
			return;
		}
		double dheight = rate * speed * dTime;
		
		double height = currentTarget() + dheight;
//		height= Math.max(Math.min(height,zeroPosition), MAX_POSITION);
		setElevatorPosition(height);
	}
	
	double holdPower = .05;
	/**
	 * Tries to make this forklift stay put (not move up and down)
	 * @return
	 */
	public void hold(){
		if(!isInPositionMode){
			elevatorTalon.set(-holdPower);
			lastSetPower = -holdPower;
			return;
		}
//		elevatorTalon.set(elevatorTalon.getPosition());
	}
	
	public double currentError()
	{
		return elevatorTalon.getClosedLoopError()/HEIGHT_CONSTANT;
	}
	
	public double currentTarget()
	{
		return (elevatorTalon.getSetpoint()-zeroPosition)/HEIGHT_CONSTANT;
	}
	
	public double currentPosition()
	{
		return (elevatorTalon.getPosition()-zeroPosition)/HEIGHT_CONSTANT;
	}
	
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
