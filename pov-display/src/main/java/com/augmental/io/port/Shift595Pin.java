package com.augmental.io.port;

import com.augmental.pov.POV;

public class Shift595Pin extends Pin.Digital
{
	private final int pin;
	
	public Shift595Pin(int pin) { this.pin = pin; }
	
	public void setValue(Boolean value)
	{
		//System.out.println(pin+"="+value);
		POV.shift595.setValue(pin, value);
	}
}
