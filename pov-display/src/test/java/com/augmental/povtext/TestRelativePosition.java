package com.augmental.povtext;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Test;

import com.augmental.pov.World2DObject;

public class TestRelativePosition
{
	@Test
	public void testSinglePositionTranslation()
	{
		World2DObject a = new World2DObject();
		a.setTranslation(10, 6);
		
		Point2D absPosition = a.getAbsolutePosition();
		
		assertTrue(testPointsMatch(absPosition,new Point2D.Double(10,6)));
	}
	
	@Test
	public void testRelativePositionTranslation()
	{
		World2DObject a = new World2DObject();
		a.setTranslation(10, 6);
		
		World2DObject b = new World2DObject();
		b.setParent(a);
		b.setTranslation(2,3);
		
		Point2D absPosition = b.getAbsolutePosition();
		
		assertTrue(testPointsMatch(absPosition,new Point2D.Double(12,9)));
	}
	
	@Test
	public void testRelativePositionTranslation2()
	{
		World2DObject a = new World2DObject();
		a.setTranslation(5, 0);
		
		World2DObject b = new World2DObject();
		b.setParent(a);
		b.setTranslation(0,3);
		
		Point2D absPosition = b.getAbsolutePosition();
		
		assertTrue(testPointsMatch(absPosition,new Point2D.Double(5,3)));
	}
	
	@Test
	public void testRelativePositionRotation()
	{
		World2DObject b = new World2DObject();
		b.setRotation(Math.PI/2);
		
		World2DObject a = new World2DObject();
		a.setTranslation(1,0);
		a.setParent(b);
		
		Point2D absPosition = a.getAbsolutePosition();
		
		assertTrue(testPointsMatch(absPosition,new Point2D.Double(0,1)));
	}
	
	private boolean testPointsMatch(Point2D actual, Point2D expected)
	{
		boolean match = actual.equals(expected);
		System.out.println(String.format("Expected: (%.2f,  %.2f), Actual: (%.2f,  %.2f), %s",expected.getX(),expected.getY(),actual.getX(),actual.getY(),match?"PASSED":"FALED"));
		return match;
	}
}
