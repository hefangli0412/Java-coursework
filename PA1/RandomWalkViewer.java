/**
 * RandomWalkViewer     class of main routine.  
 *
 *  author: Hefang Li
 *
 * This program simulates the random walk of a Drankard object 
 * and draw the walk course in GUI.
 */

import java.util.Scanner;
import javax.swing.JFrame;
/**
 * This program simulates the Drankard in a random walk
 * and draws the resulting course into a GUI.
 * @author hefangli
 *
 */
public class RandomWalkViewer {	
	/**
		Main routine for the entire project.
		Use "RandomWalkViewer" commandline to start.
		@param args not used
	*/
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int num = 0;
		final int WINDOW_SIZE = 400;

		/**
			Prompts for the number of steps.
			Only positive input is admitted; otherwise, re-input is required.
		 */		
		boolean validated = false;
		while (!validated) {
			System.out.print("Enter the number of steps: ");
			num = in.nextInt();
			
			if (num > 0) {
				validated = true;
			} else {
				System.out.println("ERROR: Number entered must be greater than 0.");
			}
		}
		in.close();

		/**
			Create and configure the JFrame and then contain the RandomWalkComponent into the window.			
		 */	
		JFrame frame = new JFrame();
		frame.setSize(WINDOW_SIZE, WINDOW_SIZE);
		frame.setTitle("RandomWalk_HefangLi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		RandomWalkComponent component = new RandomWalkComponent(num);
		frame.add(component);
		
		frame.setVisible(true);
	}
}
