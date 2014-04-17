/**
   DrunkardTester     
       console-based test program for Drunkard class.
       Uses hard-coded data, prints expected and actual output.
       If any error is found, "failed" appears; otherwise all "passed" are displayed.

    author: Hefang Li
*/
public class DrunkardTester {
	
	/**
    	Test driver for Drunkard class.
    	@param args not used
    */
    public static void main(String[] args) {
		drunkardTester(5, 5);
		drunkardTester(10, 20);
		drunkardTester(15, 30);
    }
    	
    /**
		Test all Drunkard methods on given stepNum and stepSize.
		@param num number of steps in the random walk
		@param StepSize size of one step in the random walk
     */
    private static void drunkardTester(int stepNum, int stepSize) {
    	ImPoint startLoc = new ImPoint(0, 0);
		Drunkard tester = new Drunkard(startLoc, stepSize);	
	
		System.out.println("Drunkard starts at (" + startLoc.getX() +
				"," + startLoc.getY() + "); step size is " + stepSize);
		testgetCurrentLoc(tester, startLoc);
		testTakeStep(tester, stepNum, stepSize);

		System.out.println();
		System.out.println();
    }

    /**
		Test the constructor on the given start location.
		Test of getCurrentLoc method is also included.
		@param tester a Drunkard object created in drunkardTester method
		@param startLoc the start location given in drunkardTester method
     */
    private static void testgetCurrentLoc(Drunkard tester, ImPoint startLoc) {
    	System.out.println("get starting loc: (" + tester.getCurrentLoc().getX() + "," + 
    			tester.getCurrentLoc().getY() + ")");
	}

	/**
		Test takeStep method on given stepNum and stepSize by examining simulation step size and direction.
		Test of getCurrentLoc method is also included.
		@param tester a Drunkard object created in drunkardTester method
		@param num number of steps in the random walk
		@param StepSize size of one step in the random walk
     */
    private static void testTakeStep(Drunkard tester, int stepNum, int stepSize) {
		for (int i = 0; i < stepNum; i++) {
			ImPoint from = tester.getCurrentLoc();
			tester.takeStep();
			ImPoint to = tester.getCurrentLoc();
			System.out.print("took step to ("+ tester.getCurrentLoc().getX() + "," + 
					tester.getCurrentLoc().getY() + ") [Expected: (" + 
					tester.getCurrentLoc().translate(stepSize, 0).getX() + "," + 
					tester.getCurrentLoc().translate(stepSize, 0).getY() + ") or (" +
					tester.getCurrentLoc().translate(-stepSize, 0).getX() + "," + 
					tester.getCurrentLoc().translate(-stepSize, 0).getY() +") or (" + 
					tester.getCurrentLoc().translate(0, stepSize).getX() + "," + 
					tester.getCurrentLoc().translate(0, stepSize).getY() + ") or (" + 
					tester.getCurrentLoc().translate(0, -stepSize).getX() + "," + 
					tester.getCurrentLoc().translate(0, -stepSize).getY() + ")]");
			if (distance(from, to) != (double)stepSize) {
				System.out.print("  FAILED: not a valid step");
			} else if ((from.getX() - to.getX() != 0) && (from.getY() - to.getY() != 0)) {
				System.out.print("  FAILED: not a valid step");
			}
			System.out.println();
		}
    }
	
    /**
		@param two ImPoint variables at the ends of one step
		@return the distance between the given two ImPoint variables.
     */   
    public static double distance(ImPoint a, ImPoint b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		return Math.sqrt(dx * dx + dy * dy);
    }
}
