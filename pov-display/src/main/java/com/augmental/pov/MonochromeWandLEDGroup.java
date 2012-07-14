package com.augmental.pov;

import com.augmental.io.port.Shift595Pin;

public class MonochromeWandLEDGroup extends LEDGroup
{	
	public MonochromeWandLEDGroup()
	{
		for(int i=0;i<8;i++)
		{
			addChild((MonochromeLED.Digital)(new MonochromeLED.Digital(new Shift595Pin(i)).setTranslation(0, i)));
		}
	}
}
