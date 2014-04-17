public class Counters {
	
	private int lineCounter;	// count down
	private int textCounter;	// count down
	
	public Counters(int numOutFileWords) {
		lineCounter = ConstantValue.NUM_LINE_CHARACTERS;
		textCounter = numOutFileWords;
	}
	
	/**
	 * @param num the number to be decremented from lineCounter
	 * @return num <= lineCounter; unchanged if false 
	 */
	public boolean decrementLineCounter(int num) {
		if (num <= lineCounter) {
			lineCounter -= num;
			return true;
		} else {
			return false;
		}
	}
	
	public void resetLineCounter() {
		lineCounter = ConstantValue.NUM_LINE_CHARACTERS;
	}
	
	/**
	 * @param num the number to be decremented from textCounter
	 * @return num <= textCounter; unchanged if false 
	 */
	public boolean decrementTextCounter(int num) {
		if (num <= textCounter) {
			textCounter -= num;
			return true;
		} else {
			return false;
		}
	}	

}
