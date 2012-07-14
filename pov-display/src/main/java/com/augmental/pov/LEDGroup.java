package com.augmental.pov;

import java.util.Iterator;

public class LEDGroup extends World2DObject
{	
	public final void setValuesFromCanvas(Canvas canvas)
	{
		Iterator<World2DObject> childIterator = getChildIterator();
		
		while(childIterator.hasNext())
		{
			World2DObject child = childIterator.next();
			if(child instanceof LED<?,?,?>)
			{
				((LED<?,?,?>)child).setValueOfPinFromCanvas(canvas);
			}
		}
	}
	
	public Iterator<LED<?,?,?>> getLEDIterator()
	{
		return new Iterator<LED<?,?,?>>()
		{
			Iterator<World2DObject> childIterator = getChildIterator();
			LED<?,?,?> nextLED = getNextLED();
			
			private LED<?,?,?> getNextLED()
			{
				World2DObject child;
				while(childIterator.hasNext())
				{
					child = childIterator.next();
					if(child instanceof LED<?,?,?>) return (LED<?,?,?>)child;
				}
				return null;
			}
			
			@Override
			public boolean hasNext() { return nextLED != null; }

			@Override
			public LED<?, ?, ?> next()
			{
				LED<?,?,?> currentLED = nextLED;
				nextLED = getNextLED();
				return currentLED;
			}

			@Override
			public void remove() { throw new UnsupportedOperationException("Operation unsupported"); }
		};
	}
}
