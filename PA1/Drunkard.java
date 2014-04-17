import java.util.Random;

/**
   A class to simulate a drunkard in a random walk.
*/
public class Drunkard {
	private ImPoint curLoc;
	private int theStepSize;
	// generator has to be here to produce true random numbers
	private Random generator;
	private final int NUM_DIRECTIONS = 4;
	private final int EAST = 0;
	private final int NORTH = 1;
	private final int WEST = 2;
	private final int SOUTH = 3;
	
    /**
       Constructs a Drunkard object from the given starting location and the distance
       moved in a single step.
       @param startLoc the starting location
       @param theStepSize the step size in one random walk
    */
    public Drunkard(ImPoint startLoc, int theStepSize) {
    	this.curLoc = startLoc;
    	this.theStepSize = theStepSize;
    	this.generator = new Random();
    }

    /**
       Takes one random step in one of the four compass directions 
       and changes the current location of the Drunkard object.
    */
    public void takeStep() {
		int randNum = generator.nextInt(NUM_DIRECTIONS);
		switch (randNum) {
			case EAST: this.curLoc = this.curLoc.translate(this.theStepSize, 0); break;
			case NORTH: this.curLoc = this.curLoc.translate(0, this.theStepSize); break;
			case WEST: this.curLoc = this.curLoc.translate(-this.theStepSize, 0); break;
			case SOUTH: this.curLoc = this.curLoc.translate(0, -this.theStepSize); break;
		}  	
    }

    /**
       Gets the current location of the Drunkard object.
       @return an ImPoint object representing drunkard's current location
    */
    public ImPoint getCurrentLoc() {
    	return new ImPoint(this.curLoc.getX(), this.curLoc.getY());
    }
}