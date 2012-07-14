package com.augmental.pov;

public class DefaultAnimator extends Animator
{
	private ColorWandLEDGroup wand;
	private Canvas canvas;
	
	protected float speed;
	
	private long unitNanos;
	
	public DefaultAnimator(float speed, Canvas canvas)
	{
		this.speed = speed;
		this.canvas = canvas;
		wand = new ColorWandLEDGroup();
		
		unitNanos = (long)((500000000l / ((long)canvas.getWidth() * speed) ));
	}

	@Override
	protected void setupAnimationForTime(float t)
	{
		float x = ( t<0.5 ? (t * 2) : (2 - (t * 2) ) ) * canvas.getWidth();
		//System.out.println("t:"+t+", x:"+x);
		wand.setTranslation(x, 0);
		//System.out.println("Wand position: "+wand.getAbsolutePosition());		
		wand.setValuesFromCanvas(canvas);
	}

	Long lastAnimateNanos = null;
	
	@Override
	protected long getNextAnimateNanos()
	{
		long nextAnimateNanos = lastAnimateNanos==null ? System.nanoTime() : lastAnimateNanos + unitNanos ;
		
		lastAnimateNanos = System.nanoTime();
		
		return nextAnimateNanos;
	}
}
