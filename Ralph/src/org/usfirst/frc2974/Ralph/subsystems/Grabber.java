package org.usfirst.frc2974.Ralph.subsystems;

import org.usfirst.frc2974.Ralph.RobotMap;
import org.usfirst.frc2974.Ralph.commands.Grab;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Grabber extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	CANTalon grabberTalon;
	private double maxSteadyCurrentClose = 3;
	private double steadyPower = 0.2;
	private double maxSteadyCurrentOpen = 3;
	
	
public Grabber()
{
	grabberTalon = RobotMap.grabberTalon;

	grabberTalon.ConfigRevLimitSwitchNormallyOpen(true);
	grabberTalon.ConfigFwdLimitSwitchNormallyOpen(true);
	grabberTalon.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
	grabberTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);
	grabberTalon.enableControl();
}
    public void initDefaultCommand() {
    	
    }
    
    public void setPowerMode()
	{
    	Preferences prefs = Preferences.getInstance();
    	maxSteadyCurrentClose = prefs.getDouble("G_MaxSteadyCurrentClose", maxSteadyCurrentClose);
    	maxSteadyCurrentOpen = prefs.getDouble("G_MaxSteadyCurrentOpen", maxSteadyCurrentOpen);
    	steadyPower = prefs.getDouble("G_SteadyPower", steadyPower);
		grabberTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//will there be brake functionality?
		grabberTalon.enableControl();
	}
	
	public boolean isOpen()
	{
		return grabberTalon.isFwdLimitSwitchClosed();
	}
	
	public boolean isClosed()
	{
		return grabberTalon.isRevLimitSwitchClosed();
	}
	
	//open/close a variable amount
	//positive variable = close
	//negative = open
	//power- value between -1 and 1(DO NOT USE 1!!!!! PLEASE!!!!)
	public void setGrabberPower(double power, boolean overrideLimit)
	{
		double current = Math.abs(readCurrent());
		if((power>0 && current < maxSteadyCurrentOpen) 
				|| (power<0 && current <maxSteadyCurrentClose) 
				|| overrideLimit)
		{			
			power=Math.max(Math.min(power, 1), -1);
		}
		else
		{
			power = Math.max(Math.min(power, steadyPower), -steadyPower);
		}
		grabberTalon.set(power);
			
	}
	
	public double readCurrent()
	{
		return grabberTalon.getOutputCurrent();	
	}
	
	/**
	 * updates smartdashboard
	 */
	public void updateSmartDashboard(){
		SmartDashboard.putString("GrabberMode", "PowerMode");
		SmartDashboard.putNumber("GrabberCurrent", readCurrent());
	}
	
}

