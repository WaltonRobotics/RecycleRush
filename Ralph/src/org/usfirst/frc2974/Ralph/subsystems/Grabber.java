package org.usfirst.frc2974.Ralph.subsystems;

import org.usfirst.frc2974.Ralph.RobotMap;
import org.usfirst.frc2974.Ralph.commands.Grab;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	CANTalon grabberTalon;
	
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
	public void setGrabberPower(double power)
	{
		power=Math.max(Math.min(power, 1), -1);			
		grabberTalon.set(power);	
	}
	
	public double readCurrent()
	{
		return grabberTalon.getOutputCurrent();	
	}
	
	
	
}

