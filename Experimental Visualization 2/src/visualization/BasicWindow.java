package visualization;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * A basic window for graphics
 *
 * @author Justin
 */
public class BasicWindow extends Canvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JFrame frame;
    private final BufferStrategy bufferStrategy;
    private Graphics2D graphics;

    /**
     * Initializes a window
     * Creates a JFrame to contain the class in (an extension of canvas)
     */
    public BasicWindow(String window_name, Dimension dimensions, int buffer_strategies) {
        // Size of the window
        setSize(dimensions);
        // MUST be called or else java awt or something calls repaint, bad things happen
        setIgnoreRepaint(true);
        setBackground(Color.black);
        frame = new JFrame();
        // frame position on screen
        frame.setLocation(200, 150);
        // Again..
        frame.setIgnoreRepaint(true);
        // Size again.. I needed to add this because of a small gap between the window border and canvas border
        frame.setPreferredSize(dimensions);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(window_name);
        // Allows usage of alt+tab, etc
        frame.setFocusTraversalKeysEnabled(false);
        // Adds the canvas to the frame
        frame.add(this);
        // Makes the frame conform to the objects contained within it, namely the canvas
        frame.pack();
        // "Prevents resizing, as this can cause some problems which I havn't bothered to look in to" ***edited
        frame.setResizable(true);
        // Makes the frame visable
        frame.setVisible(true);
        requestFocus();
        // Creates a offscreen buffer to be drawn on then rendered to the screen to prevent flickering
        createBufferStrategy(buffer_strategies);
        bufferStrategy = getBufferStrategy();
        graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
    }
    
    public JFrame getFrame() {
    	return this.frame;
    }

    /**
     * Renders the offscreen buffer
     * Call after all drawing has been done for the current frame
     */
    public void renderBuffer() {
        // Destroys the graphics object used for drawing
        graphics.dispose();
        // Shows the frame
        bufferStrategy.show();
        // Creates a new graphics object
        graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    @Override
    /**
     * Call this method to get the graphics object for drawing
     *
     * @return
     */
    public Graphics2D getGraphics() {
        return graphics;
	}

}
