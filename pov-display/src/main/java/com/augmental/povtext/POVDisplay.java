package com.augmental.povtext;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Persistence-of-Vision hardware display driver application
 */
public class POVDisplay 
{
	// Startup
	
	private static final Options options = new Options();
	private static final CommandLineParser argsParser = new GnuParser();
	
	private static final Option uiOption, textOption, modeOption, bitsOption, dirOption, timeOption;
	
	static
	{
    	uiOption	= new Option("g",	"gui",			false,	"Enables graphical user interface");
    	textOption	= new Option("m", 	"message",		true,	"The text to be displayed: [Hello, world!]");
    	modeOption	= new Option("om",	"output-mode",	true,	"The POV output mode: [p] - Parallel, s - Shifted");
    	bitsOption	= new Option("b",	"bits",			true,	"The number of bits per row: 8, 16, 24, 32");
    	dirOption	= new Option("d",	"direction",	true,	"Direction mode: [t] - Timed, [i] - Input");
    	timeOption	= new Option("t",	"timing",		true,	"Timing (ms): [500]");
    	
    	options.addOption(uiOption);
	}
	
    public static final void main ( String[] args )
    {
        final POVDisplay povText = new POVDisplay();
        
        applyArgs(povText, args);
        
        
    }
    
    public static void applyArgs(POVDisplay povText, String[] args)
    {
    	CommandLine commandLine;
        try { commandLine = argsParser.parse(options, args); }
        catch (ParseException e) { throw new RuntimeException(e); }
    	
    	if(commandLine.hasOption("ui"))
    	{
    		povText.getFrame().setVisible(true);
    	}
    }
    
    // Application
    
    private static int
    	DEFAULT_PREVIEW_CANVAS_SCALE	= 8,
    	DEFAULT_CANVAS_ASPECT_RATIO		= 8;
    
    private int bits = 8;
    
    private BufferedImage previewCanvas, outputCanvas;
    
	private JFrame frame = null;
	public  JFrame getFrame()
	{
		if(frame==null) // UI initialisation
		{
			Container contentPane = frame.getContentPane();
			contentPane.setLayout(new GridLayout(3,1));
			
			textPanel = new TextPanel();
			
			contentPane.add(textPanel);
		}
		
		return frame;
	}
	
	private TextPanel	textPanel;
	private Font		font;
	
	public POVDisplay()
	{
		frame = new JFrame("POVText");
		
		outputCanvas = new BufferedImage(DEFAULT_CANVAS_ASPECT_RATIO * bits, bits, BufferedImage.TYPE_BYTE_GRAY);
		
		
	}

	public static class TextPanel extends JComponent
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			
			
		}
	}
}