package com.augmental.pov;

import java.awt.Point;
import java.awt.geom.Point2D.Float;

public class Pixel implements IRelativePosition
{
	private IRelativePosition parent;
	
	private Point position;
	
	public Pixel()
	{
		
	}

	public IRelativePosition getParent() { return parent; }

	public Float getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
