/*
 * RandomWalkComponent     class extends JComponent.  
 *
 *  author: Hefang Li
 *
 * Constructor uses hard-coded data. 
 * Override paintComponent to draw the random walk.
 * Use a Drunkard object to keep track of current state of drunkard.
 *
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class RandomWalkComponent extends JComponent{	
	private int num;
	private Drunkard drunkman;
	private final int WIDTH = 200;
	private final int HEIGHT = 200;
	private final int STEP_SIZE = 5;
	
    /**
    	Creates the component with stepNum num. Other data is hard-coded.
		@param num number of steps in the random walk
     */
	public RandomWalkComponent(int num) {
		this.num = num;
		this.drunkman = new Drunkard(new ImPoint(WIDTH, HEIGHT), STEP_SIZE);
	}
	/**
		Overrides paintComponent method to draw the random walk.
		Modify private data of the Drunkard object to keep track of current location of drunkard.
		@param g the object graph paintComponent method works on
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		for (int i = 0; i < num; i++) {
			ImPoint from = drunkman.getCurrentLoc();	
			drunkman.takeStep();
			ImPoint to = drunkman.getCurrentLoc();
			Line2D.Double segment = new Line2D.Double(from.getPoint2D(), to.getPoint2D());
			g2.draw(segment);
		}	
	}
}
