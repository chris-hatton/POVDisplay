package com.augmental.pov;

import java.awt.geom.Point2D;

public interface IRelativePosition
{
	public IRelativePosition getParent();
	public Point2D.Float getPosition();
}
