
package org.usfirst.frc2974.Ralph;


import edu.wpi.first.wpilibj.Joystick;


public class Gamepad extends Joystick
{

	public Gamepad( int port )
	{

		super(port);
	}


	public double getLeftY( )
	{

		return getRawAxis(1);
	}


	public double getLeftX( )
	{

		return getRawAxis(0);
	}


	public double getRightX( )
	{

		return getRawAxis(4);
	}


	public double getRightY( )
	{

		return getRawAxis(5);
	}


	public double getLeftTrigger( )
	{

		return getRawAxis(2);
	}


	public double getRightTrigger( )
	{

		return getRawAxis(3);
	}


	public boolean getButton( int index )
	{

		return getRawButton(index);
	}


	public boolean getButton( Button b )
	{

		return b.getPressed(this);
	}
	
	public boolean getPOVButton( POV p )
	{

		return p.getPressed(this);
	}

	public enum POV
	{
		N( 0 ), S( 180 ), E( 90 ), W( 270 ), NE( 45 ), SE( 135 ), NW( 315 ), SW( 225 );

		private int	angle;


		private POV( int angle )
		{

			this.angle = angle;;
		}


		boolean getPressed( Gamepad g )
		{

			return g.getPOV() == angle;
		}
	}

	public enum Button
	{
		A( 0 ), B( 1 ), X( 2 ), Y( 3 ), L( 4 ), R( 5 ), BACK( 6 ), START( 7 );

		private int	index;


		private Button( int index )
		{

			this.index = index;
		}


		boolean getPressed( Gamepad g )
		{

			return g.getRawButton(index);
		}
	}

}
