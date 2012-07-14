package com.augmental.pov;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.augmental.io.port.Pin;

public abstract class LED<P extends Pin<PD>, PD, OD>
extends World2DObject
{
	protected abstract OD getValueForSample(Color sample);
		
	public void setValueOfPinFromCanvas(Canvas canvas)
	{
		Point2D point = this.getAbsolutePosition();
		Color sample = canvas.getSample(point);
		setValueOfPinFromSample(sample);
	}
	
	public void setValueOfPinFromSample(Color sample)
	{
		OD value = getValueForSample(sample);
		setOutput(value);
	}
	
	public abstract void setOutput(OD values);
}
