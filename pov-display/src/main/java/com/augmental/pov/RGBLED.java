package com.augmental.pov;

import java.awt.Color;

import com.augmental.io.port.Pin;

public abstract class RGBLED<P extends Pin<PD>, PD, OD>
extends LED<P, PD, OD>
{
	protected final int threshold = 128;
	
	protected final Pin<PD>
		redPin,
		greenPin,
		bluePin;
	
	public RGBLED(Pin<PD> redPin, Pin<PD> greenPin, Pin<PD> bluePin)
	{
		this.redPin		= redPin;
		this.greenPin	= greenPin;
		this.bluePin	= bluePin;
	}
	
	public static class Digital extends RGBLED<Pin.Digital, Boolean, Boolean[]>
	{
		public Digital(Pin<Boolean> redPin, Pin<Boolean> greenPin, Pin<Boolean> bluePin)
		{
			super(redPin, greenPin, bluePin);
		}

		@Override
		protected Boolean[] getValueForSample(Color sample)
		{
			return new Boolean[]
				{
					sample.getRed()   > threshold,
					sample.getGreen() > threshold,
					sample.getBlue()  > threshold
				};
		}

		@Override
		public void setOutput(Boolean[] values)
		{
			redPin	.setValue(values[0]);
			greenPin.setValue(values[1]);
			bluePin	.setValue(values[2]);
		}
	}
	
	public static class PWM extends RGBLED<Pin.PWM, Integer, Integer[]>
	{
		public PWM(Pin<Integer> redPin, Pin<Integer> greenPin, Pin<Integer> bluePin)
		{
			super(redPin, greenPin, bluePin);
		}

		@Override
		protected Integer[] getValueForSample(Color sample)
		{
			return new Integer[]
					{
						sample.getRed(),
						sample.getGreen(),
						sample.getBlue()
					};
		}

		@Override
		public void setOutput(Integer[] values)
		{
			redPin	.setValue(values[0]);
			greenPin.setValue(values[1]);
			bluePin	.setValue(values[2]);
		}
	}
}
