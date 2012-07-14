package com.augmental.pov;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Canvas
{
	private BufferedImage	buffer;
	private Dimension		size;
	
	public Canvas(BufferedImage image)
	{
		size = new Dimension(image.getWidth(), image.getHeight());
		buffer = image;
	}
	
	public Canvas(int width, int height)
	{
		size	= new Dimension(width, height);
		buffer	= new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	}
	
	public Dimension getSize() { return size; }
	
	public Color getSample(Point2D point)
	{
		double x = point.getX(), y = point.getY();
		
		x = x < 0 ? 0 : x >= size.getWidth()  ? size.getWidth() -1 : x;
		y = y < 0 ? 0 : y >= size.getHeight() ? size.getHeight()-1 : y;
		
		int value = buffer.getRGB((int)x, (int)y);
		return new Color(value);
	}
	
	public int getWidth()  { return (int)size.getWidth();  }
	public int getHeight() { return (int)size.getHeight(); }

	public BufferedImage getBuffer() { return buffer; }
}
