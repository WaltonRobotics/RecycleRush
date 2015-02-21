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
	
	CANTalon clawTalon;
	private boolean isHolding;
	private boolean clawIsFullyOpen;
	
public Grabber()
{
	clawIsFullyOpen = false;
	isHolding = false;
	clawTalon = RobotMap.clawTalon;

	clawTalon.ConfigRevLimitSwitchNormallyOpen(true);
	clawTalon.ConfigFwdLimitSwitchNormallyOpen(true);
	clawTalon.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
	clawTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);
	clawTalon.enableControl();
}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Grab());
    }
    
    public void setPowerMode()
	{
		clawTalon.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//will there be brake functionality?
		clawTalon.enableControl();
	}
	
	public boolean isOpen()
	{
		return clawTalon.isFwdLimitSwitchClosed();
	}
	
	public boolean isClosed()
	{
		return clawTalon.isRevLimitSwitchClosed();
	}
	
	//open/close a variable amount
	//positive variable = close
	//negative = open
	//power- value between -1 and 1(DO NOT USE 1!!!!! PLEASE!!!!)
	public void setClawPower(double power)
	{
		power=Math.max(Math.min(power, 1), -1);			
		clawTalon.set(power);	
	}
	
	public double readCurrent()
	{
		return clawTalon.getOutputCurrent();	
	}
	
	
	
}

