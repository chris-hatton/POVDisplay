package com.augmental.pov;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World2DObject
{
	private List<World2DObject> children = new ArrayList<>();

	public void addChild(World2DObject child)
	{
		if(child.getParent()!=this)
		{
			children.add(child);
			child.setParent(this);
		}
	}
	
	public void removeChild(World2DObject child)
	{
		if(child.getParent()==this)
		{
			children.remove(child);
			child.setParent(null);
		}
	}
	
	public boolean containsChild(World2DObject child)
	{
		return children.contains(child);
	}
	
	public Iterator<World2DObject> getChildIterator() { return children.iterator(); }
	
	private AffineTransform transform = new AffineTransform();
	public final AffineTransform getTransform() { return transform; }
	public final World2DObject setTransform(AffineTransform transform) { this.transform = transform; return this; }
	
	private World2DObject parent;
	public final World2DObject getParent() { return parent; }
	public final World2DObject setParent(World2DObject parent)
	{
		if(this.parent!=parent)
		{
			if(this.parent!=null )this.parent.removeChild(this);
			this.parent = parent;
			parent.addChild(this);	
		}
		return this;
	}
	
	public final Point2D getAbsolutePosition()
	{
		AffineTransform currentTransform = new AffineTransform();
		World2DObject currentObject = this;
		
		do { currentTransform.preConcatenate(currentObject.getTransform()); }
		while((currentObject = currentObject.getParent()) != null);
		
		Point2D absolute = new Point2D.Double();
		Point2D zero = new Point2D.Double(0,0);
		return currentTransform.transform(zero,absolute);
	}

	public final World2DObject setTranslation(float x, float y )
	{
		transform = AffineTransform.getTranslateInstance((double)x, (double)y);
		return this;
	}
	
	public final World2DObject setRotation(double d)
	{
		transform = AffineTransform.getRotateInstance(d);
		return this;
	}
}
