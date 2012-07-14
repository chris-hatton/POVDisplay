package com.augmental.pov;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.augmental.jbcm2835.Direction;
import org.augmental.jbcm2835.GPIOPin;
import org.augmental.jbcm2835.JBCM2835;
import org.augmental.jbcm2835.port.shift595.Shift595;

import com.augmental.awt.Colors;

/**
 * Persistence-of-Vision hardware display driver application
 */
public class POV 
{
	private static final GPIOPin DATA_PIN = GPIOPin.PIN11;
	private static final GPIOPin CLOCK_PIN = GPIOPin.PIN15;
	private static final GPIOPin LATCH_PIN = GPIOPin.PIN16;
	private static final GPIOPin CLEAR_PIN = GPIOPin.PIN18;
	
	private static final Options options = new Options();
	private static final CommandLineParser argsParser = new GnuParser();
	
	private static final Option uiOption, textOption, modeOption, bitsOption, dirOption, timeOption, fontOption;
	
	static
	{
    	uiOption	= new Option("g",	"gui",			false,	"Enables graphical user interface");
    	textOption	= new Option("m", 	"message",		true,	"The text to be displayed: [Hello, world!]");
    	modeOption	= new Option("om",	"output-mode",	true,	"The POV output mode: [p] - Parallel, s - Shifted");
    	bitsOption	= new Option("b",	"bits",			true,	"The number of bits per row: 8, 16, 24, 32");
    	dirOption	= new Option("d",	"direction",	true,	"Direction mode: [t] - Timed, [i] - Input");
    	timeOption	= new Option("t",	"timing",		true,	"Timing (ms): [500]");
    	fontOption	= new Option("f",	"font",			true,	"Font [FreeSerif]");
    	
    	options.addOption(uiOption);
    	options.addOption(textOption);
    	options.addOption(modeOption);
    	options.addOption(bitsOption);
    	options.addOption(dirOption);
    	options.addOption(timeOption);
    	options.addOption(fontOption);
	}
	
	public static final Shift595 shift595 = new Shift595(DATA_PIN, CLOCK_PIN, LATCH_PIN, CLEAR_PIN, 24);
	
	private static String defaultFontName = "SansSerif";
	
	private static Dimension getStringSize(String string, Font font)
	{
		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
		FontMetrics fm = bi.getGraphics().getFontMetrics(font);
		int width = fm.stringWidth(string);
		int height = fm.getHeight();
		return new Dimension(width, height);
	}
	
	private static Font font = new Font(defaultFontName, Font.BOLD, 11);
		
	private enum Mode { MESSAGE, IMAGE, COLOR_TEST }  
	
	public static final void main ( String[] args )
    {
		if (!JBCM2835.init()) throw new RuntimeException("Cannot init BCM2835!");
		
		DATA_PIN.setDirection(Direction.OUTPUT);
	    CLOCK_PIN.setDirection(Direction.OUTPUT);
	    LATCH_PIN.setDirection(Direction.OUTPUT);
	    CLEAR_PIN.setDirection(Direction.OUTPUT);
		
        final POV pov = new POV();
        
        applyArgs(pov, args);
        
        Canvas canvas;
        
        String fileName = "test.png";
        
        boolean animatingMode;
        
        switch(Mode.MESSAGE)
        {
        	case MESSAGE:
        		int pinCount = 8;
        		String message = "Persistence of Vision";
                Dimension size = getStringSize(message, font);
                canvas = new Canvas((int)size.getWidth()+2,pinCount);
                Graphics graphics = canvas.getBuffer().getGraphics();
                graphics.setFont(font.deriveFont(pinCount));
                
                // Fill background
                graphics.setColor(Color.BLUE);
                graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                
                // Draw text shadow
                graphics.setColor(Color.BLACK);
                graphics.drawString(message, 0, canvas.getHeight()+1);
                
                // Draw text
                graphics.setColor(Color.WHITE);
                graphics.drawString(message, 0, canvas.getHeight());
                
                animatingMode = true;
                break;
        		
        	case IMAGE:
        		BufferedImage image;
				try
				{
					image = ImageIO.read(new File(fileName));
					System.out.println("Loaded image '"+fileName+"': "+image.getWidth()+"x"+image.getHeight());
				}
				catch (IOException e)
				{
					throw new RuntimeException(e);
				}
        		canvas = new Canvas(image);
        		animatingMode = true;
        		break;
        		
        	case COLOR_TEST:
        		
        		final LEDGroup colorWand = new ColorWandLEDGroup();
        		final Color[] palette = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};
        		
        		Runnable colorTest = new Runnable()
        		{
        			int testCount = 10;
        			int pauseMs;
        			
        			public void run()
        			{
        				while(testCount-->0)
        				{
        					pauseMs = 2000;
        					
        					System.out.println("Test cycles remaining: "+testCount);
        					
        					for(Color color : palette)
        					{
        						System.out.println("Showing color: "+color);
        						
        						shift595.beginAtomic();
        						
        						LED<?,?,?> led;
		        				Iterator<LED<?,?,?>> ledIterator = colorWand.getLEDIterator();
		        				
		        				while(ledIterator.hasNext())
		        				{
		        					led = ledIterator.next();
	        						led.setValueOfPinFromSample(color);
		        				}
		        				
		        				shift595.completeAtomic();
		        				
		        				synchronized(this)
		        				{
			        				try { wait(pauseMs); }
			        				catch (InterruptedException e) { throw new RuntimeException(e); }
		        				}
        					}
        					
        					pauseMs = 500;
        					
        					LED<?,?,?> led;
        					Iterator<LED<?,?,?>> ledIterator = colorWand.getLEDIterator();
        					while(ledIterator.hasNext())
        					{
        						led = ledIterator.next();
        						
        						for(Color color : palette)
            					{
        							shift595.beginAtomic();
        							
        							System.out.println("Setting ");
        							
        							led.setValueOfPinFromSample(color);
        							
        							shift595.completeAtomic();
        							
        							synchronized(this)
    		        				{
    			        				try { wait(pauseMs); }
    			        				catch (InterruptedException e) { throw new RuntimeException(e); }
    		        				}
            					}
        						
        					}
        				}
        			}
        		};
        		
        		Thread testThread = new Thread(colorTest);
        		testThread.start();
        		
				try { testThread.join(); }
				catch (InterruptedException e) { throw new RuntimeException(e); }
        		
        		animatingMode = false;
        		canvas = null;
        		break;
        	
        	default: throw new RuntimeException("Invalid mode");
        }
                        
        System.out.println(defaultFontName+" -> "+font);
       
        if(animatingMode)
        {
	        Color sample;
	        for(int y=-1;y<=canvas.getHeight();y++)
	        {
	        	System.out.print('|');
	        	for(int x=0;x<canvas.getWidth();x++)
	        	{
	        		char c;
	        		
	        		if(y==-1||y==canvas.getHeight()) c = '-';
	        		else
	        		{
		        		sample = canvas.getSample(new Point2D.Double(x,y));
		        		int luminance = Colors.Luminance(sample);
		        		if(luminance<51) c = ' ';
		        		else if (luminance<102) c = '░';
		        		else if (luminance<153) c = '▒';
		        		else if (luminance<204) c = '▓';
		        		else c = '█';
		        		
		        		//c = (Colors.Luminance(sample)>128 ? '*':' ');
	        		}
	        		
	        		System.out.print(c);
	        	}
	        	System.out.println('|');
	        }
	        
	        Animator animator = new DefaultAnimator(16,canvas);
	        
	        animator.addObserver(new Animator.Observer()
	        {
				public void willAnimate() { shift595.beginAtomic();	   }
				public void didAnimate()  { shift595.completeAtomic(); }
			});
	        
	        animator.setAnimating(true);
        }
    }

    public static void applyArgs(POV pov, String[] args)
    {
    	CommandLine commandLine;
        try { commandLine = argsParser.parse(options, args); }
        catch (ParseException e) { throw new RuntimeException(e); }
    	
    	if(commandLine.hasOption("g"))
    	{
    		//pov.getFrame().setVisible(true);
    	}
    	
    	if(commandLine.hasOption("f"))
    	{
            font = Font.getFont(args[0]);
            if(font==null)
            {
            	System.out.println("You DICK, I don't have "+args[0]);
            	System.exit(1);
            }
    	}
    }
}
