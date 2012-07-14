package com.augmental.pov;

public abstract class Animator extends com.augmental.util.Observable<Animator.Observer>
{
	public enum Event { WILL_ANIMATE, DID_ANIMATE }
	
	public static interface Observer
	{
		public void willAnimate();
		public void didAnimate();
	}
	
	private Thread thread;
	
	protected float speed = 0.25f;
		
	private boolean animating = false;
	public void setAnimating(boolean animating)
	{
		if(this.animating!=animating)
		{
			thread = new Thread(animateTask);
			animateTask.setGo(true);
			thread.start();
		}
		else
		{
			animateTask.setGo(false);
		}
	}
	
	protected abstract void setupAnimationForTime(float t);
	
	protected abstract long getNextAnimateNanos();
	
	@Override
	protected void notifyObserver(String eventString, Observer observer, Object... parameter)
	{
		switch(Event.valueOf(eventString))
		{
			case WILL_ANIMATE:
				observer.willAnimate();
				break;
				
			case DID_ANIMATE:
				observer.didAnimate();
				break;
		}
	}
	 
	private AnimateTask animateTask = new AnimateTask();
	private class AnimateTask implements Runnable
	{
		float t = 0;
		boolean go = true;
		
		public void setGo(boolean go) { this.go = go; }
		
		Long lastTimeMs = null;
		
		public void run()
		{
			while(go)
			{
				if(t>1) t = 0;
				
				notifyObservers(Event.WILL_ANIMATE.toString());
				setupAnimationForTime(t);
				notifyObservers(Event.DID_ANIMATE.toString());
				
				//---
				long timeMs = System.currentTimeMillis();
				long elapsedMs = lastTimeMs==null ? 0 : (timeMs - lastTimeMs);
				lastTimeMs = timeMs;
				//---
				
				t += ((float)elapsedMs)/1000f;
				
				long nextNanos = getNextAnimateNanos();
				while(System.nanoTime()<nextNanos);
			}
		}
	};
}
