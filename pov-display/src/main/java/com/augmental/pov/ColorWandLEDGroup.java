package com.augmental.pov;

import com.augmental.io.port.Pin;
import com.augmental.io.port.Shift595Pin;

public class ColorWandLEDGroup extends LEDGroup
{	
	public ColorWandLEDGroup()
	{
		/*
		Pin.Digital rPin, gPin, bPin;
		
		int pin = 0;
		for(int i=0;i<8;i++)
		{
			rPin = new Shift595Pin(pin++);
			gPin = new Shift595Pin(pin++);
			bPin = new Shift595Pin(pin++);
			
			addChild(((RGBLED.Digital)(new RGBLED.Digital(rPin,gPin,bPin).setTranslation(0, i))));
		}
		*/
		
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin(  7 ),new Shift595Pin(  6 ),new Shift595Pin(  5 )).setTranslation(0, 0))));
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin(  4 ),new Shift595Pin(  3 ),new Shift595Pin(  2 )).setTranslation(0, 1))));
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin(  1 ),new Shift595Pin(  0 ),new Shift595Pin( 15 )).setTranslation(0, 2))));
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin( 14 ),new Shift595Pin( 13 ),new Shift595Pin( 12 )).setTranslation(0, 3))));
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin( 11 ),new Shift595Pin( 10 ),new Shift595Pin(  9 )).setTranslation(0, 4))));
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin(  8 ),new Shift595Pin( 23 ),new Shift595Pin( 22 )).setTranslation(0, 5))));
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin( 21 ),new Shift595Pin( 20 ),new Shift595Pin( 19 )).setTranslation(0, 6))));
		addChild(((RGBLED.Digital)(new RGBLED.Digital(new Shift595Pin( 18 ),new Shift595Pin( 17 ),new Shift595Pin( 16 )).setTranslation(0, 7))));
	}
}
