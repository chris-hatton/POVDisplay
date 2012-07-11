package com.augmental.pov;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class World2DObject
{
	private Set<World2DObject> children = new HashSet<>();

	public void addChild(World2DObject child)
	{
		if(child.getParent()==null)
		{
			children.add(child);
			child.setParent(this);
		}
		else throw new RuntimeException(child+" already belongs to "+child.getParent()+". Remove it first.");
	}
	
	public void removeChild(World2DObject child)
	{
		if(child.getParent()==this)
		{
			children.remove(child);
			child.setParent(null);
		}
		else throw new RuntimeException(child+" doesn't belong to this object anyway, why try to remove it?");
	}
	
	public boolean containsChild(World2DObject child)
	{
		return children.contains(child);
	}
	
	private AffineTransform transform;
	public final AffineTransform getTransform() { return transform; }
	public final void setTransform(AffineTransform transform) { this.transform = transform; }
	
	private World2DObject parent;
	public final World2DObject getParent() { return parent; }
	public final void setParent(World2DObject parent)
	{
		if(!parent.containsChild(this)) parent.addChild(this);
		this.parent = parent;
	}
	
	public final Point2D getAbsolutePosition()
	{
		AffineTransform currentTransform = new AffineTransform();
		World2DObject position = this;
		
		do { currentTransform.preConcatenate(position.getTransform()); }
		while((position = position.getParent()) != null);
		
		Point2D absolute = new Point2D.Double();
		Point2D zero = new Point2D.Double(0,0);
		return currentTransform.transform(zero,absolute);
	}

	public final void setTranslation(float x, float y )
	{
		transform = AffineTransform.getTranslateInstance((double)x, (double)y);
	}
	
	public final void setRotation(double d)
	{
		transform = AffineTransform.getRotateInstance(d);
	}
}
