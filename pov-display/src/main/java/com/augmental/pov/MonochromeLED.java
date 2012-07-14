package com.augmental.pov;

import java.awt.Color;

import com.augmental.awt.Colors;
import com.augmental.io.port.Pin;

public abstract class MonochromeLED<P extends Pin<PD>, PD, OD> extends LED<P, PD, OD>
{
	public static class Digital extends MonochromeLED<Pin.Digital, Boolean, Boolean>
	{
		public int threshold = 128;
		
		public Digital(Pin.Digital pin) { this(pin, null); }
		public Digital(Pin.Digital pin, Color color) { super(pin, color); }

		@Override
		protected Boolean getValueForSample(Color sample)
		{
			Color colorResponse = (color==null) ? sample : Colors.multiply(sample, color);
			return Colors.Luminance(colorResponse) > threshold;
		}

		@Override
		public void setOutput(Boolean values) { getPin().setValue(values); }
	}
	
	public static class PWM extends MonochromeLED<Pin.PWM, Integer, Integer>
	{
		public PWM(Pin.PWM pin) { this(pin, null); }
		public PWM(Pin.PWM pin, Color color) { super(pin, color); }

		@Override
		protected Integer getValueForSample(Color sample)
		{
			Color colorResponse = (color==null) ? sample : Colors.multiply(sample, color);
			return Colors.Luminance(colorResponse);
		}

		@Override
		public void setOutput(Integer values) { getPin().setValue(values); }
	}
	
	protected final Color color; 
	
	private final P pin;
	public P getPin() { return pin; }

	public MonochromeLED(P pin) { this(pin, null); }
	
	public MonochromeLED(P pin, Color color)
	{
		this.pin = pin;
		this.color = color;
	}
}
