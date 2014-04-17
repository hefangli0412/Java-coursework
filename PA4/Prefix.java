import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This corresponds to the idea of a prefix in the problem description. That is,
 * a sequence of words that you are using as a basis to choose the next word to
 * generate. Usually this sequence consists of the prefixLength previous words
 * you have just generated (except for when you need to get a new initial prefix). 
 * When deciding on the Prefix methods, you should think in terms of what are the
 * things we have to do with a prefix in this program. (Hint: you can look for
 * verbs that are associated with the word prefix in the problem description.)
 * Note that this is a immutable class.
 */
public class Prefix {
	
	private ArrayList<String> prefixList;	// the expression of prefix
	private PrintWriter fileWriter;	// out file
	private boolean isDebugMode;
		
	@SuppressWarnings("unchecked")
	public Prefix(ArrayList<String> prefixList, PrintWriter fileWriter,
			boolean isDebugMode) {
		this.prefixList = (ArrayList<String>) prefixList.clone();
		this.fileWriter = fileWriter;
		this.isDebugMode = isDebugMode;
	}
	
	/**
	 * generate a new prefix on the basis of the previous prefix and the chosen 
	 * next word by shifting one word.
	 * the caller is unchanged.
	 */
	public Prefix generateNextPrefix(String nextWord) {
		ArrayList<String> nextPrefixList = new ArrayList<String>();
		ListIterator<String> iter = prefixList.listIterator();
		iter.next();	// skip first element
		while (iter.hasNext()) {
			nextPrefixList.add(iter.next());
		}
		nextPrefixList.add(nextWord);	// add nextWord to the end
		return new Prefix(nextPrefixList, fileWriter, isDebugMode);
	}	
	
	/**
	 * Write out prefix starting with a space. If the current line is to be more than
	 * NUM_LINE_CHARACTERS, start a new line.
	 */
	public boolean writePrefix(RandomTextGenerator rtGenerator) {		
		for (String elem : prefixList) {
			if (rtGenerator.getCounters().decrementTextCounter(1) == false) {
				return false;	// stop writing
			}
 			if (rtGenerator.getCounters().decrementLineCounter(elem.length() + 1) == true) {
 				if (rtGenerator.getIsFirstWord() == true) {
 					fileWriter.print(elem); 					
 					rtGenerator.setIsFirstWord(false);
 				} else {
 					fileWriter.print(" " + elem);					
 				}
 			} else {	// start a new line
 				rtGenerator.getCounters().resetLineCounter();
 				rtGenerator.getCounters().decrementLineCounter(elem.length());
 				fileWriter.print("\n" + elem);
 			}
		}
		
		if (isDebugMode) {
			System.out.print("DEBUG: choose a new initial prefix: ");
			printPrefix();
			System.out.print("DEBUG: prefix: ");
			printPrefix();
		}
		
		return true;
	}	

	public void printPrefix() {
		for (String elem : prefixList) {
			System.out.print(elem + " ");			
		}
		System.out.println();
	}
	
	/**
	 * Whenever a.equals(b), then a.hashCode() must be same as b.hashCode(). 
	 * If you override one, then you should override the other.
	 * Use the same set of fields that you use to compute equals() to compute hashCode().
	 */	
	@Override
    public int hashCode() {
        int h = 0;        
        for (String elem : prefixList) {
        	h = ConstantValue.HASH_MULTIPLIER * h + elem.hashCode();
        }        
        return h;
    }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Prefix other = (Prefix) obj;

		return this.prefixList.equals(other.prefixList);
	}
	
}
