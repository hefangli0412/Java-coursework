import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This will contain most of the program logic and data structures, and will
 * probably only have a few methods. You will create it with some form of the
 * source text and possibly other arguments. Then it will have another method to
 * generate random text from that source.
 */

public class RandomTextGenerator{
	
	private Scanner fileScanner;	// source file
	private PrintWriter fileWriter;	// out file
	private HashMap<Prefix, ArrayList<String>> myHashMap;	// map to store
	private Random randomNumGenerator;	// generate random number
	
	private int prefixLength;	// k-order
	private Counters counters;	// line and text counters
	private boolean isDebugMode;
	
	private boolean isFirstWord;	// skip the first word space
	private boolean hasUniqueEnding;	// for special case
	private Prefix endingPrefix;	// for special case
	
	public RandomTextGenerator(Scanner fileScanner, PrintWriter fileWriter,
			int numOutFileWords, int prefixLength, boolean isDebugMode) {
		this.fileScanner = fileScanner;
		this.fileWriter = fileWriter;
		this.prefixLength = prefixLength;		
		this.isDebugMode = isDebugMode;
		counters = new Counters(numOutFileWords);	
		
		myHashMap = new HashMap<Prefix, ArrayList<String>>();
		randomNumGenerator = new Random();				
		isFirstWord = true;
		hasUniqueEnding = false;
	}

	/**
	 * PRE: prefixLength < number of words in sourceFile
	 */
	public void preprocessSourceFile() throws BadPrefixLengthException {			
		
		ArrayList<String> firstPrefixList = generateFirstPrefixList(fileScanner);
		Prefix key = new Prefix(firstPrefixList, fileWriter, isDebugMode);	// create the first key		
		
		while (fileScanner.hasNext()) {			
			String nextWord = fileScanner.next();	// scan in next word and use as new value
			
			if (myHashMap.get(key) == null) {	// if no corresponding key in the set, create an entry
				ArrayList<String> valueList = new ArrayList<String>();				
				myHashMap.put(key, valueList);	
			} 
			myHashMap.get(key).add(nextWord);	// update the corresponding entry, add new value
 
			key = key.generateNextPrefix(nextWord);	// update key for the next iteration				
		}	
	
		
		if(myHashMap.get(key) == null) {	// for special case
			hasUniqueEnding = true;
			endingPrefix = key;
//			System.out.print("DEBUG: hasUniqueEnding = true  endingPrefix: ");
//			endingPrefix.printPrefix();
//			System.out.println();
		}		
		
//		System.out.println("MapSize: " + myHashMap.size() + "  PrefixLength: " + prefixLength);		
//		for (Prefix key1 : myHashMap.keySet()) {
//			System.out.print("ENTRY: ");
//			key1.printPrefix();
//			System.out.println(myHashMap.get(key1));
//		}
	}

	public void generateOutFile() {
		List<Prefix> prefixes = new ArrayList<Prefix>(myHashMap.keySet());	// convert Set type to ArrayList
		Prefix curPrefix = pickRandomPrefix(prefixes);	// write out first prefix
		if (curPrefix.writePrefix(this) == false) {
			return;
		}
			
		boolean done = false;
		while (!done) {
			if ((hasUniqueEnding && curPrefix.equals(endingPrefix))) {	// for special case reselect and write out a random new prefix
				if (isDebugMode) {
					System.out.println("DEBUG: successors: <END OF FILE>");
				}

				curPrefix = pickRandomPrefix(prefixes);
				if (curPrefix.writePrefix(this) == false) {
					done = true;
				}
			}
			
			ArrayList<String> valueList = myHashMap.get(curPrefix);	// search HashMap for successive arraylist
			if (isDebugMode) {
				System.out.print("DEBUG: successors: ");			
				for (String elem : valueList) {
					System.out.print(elem + " ");
				}
				System.out.println();
			}
			String nextWord = valueList.get(randomNumGenerateWrapper(valueList.size()));	// pick a random word from the successive arraylist
			if (isDebugMode) {
				System.out.println("DEBUG: word generated: " + nextWord);
			}
			if (writeOneWord(nextWord) == false) {	// write out that word
				done = true;
			}
			curPrefix = curPrefix.generateNextPrefix(nextWord);	// update the current prefix for the next iteration
			if (isDebugMode) {
				System.out.print("DEBUG: prefix: ");
				curPrefix.printPrefix();
			}
		}	
	}

	/**
	 * Write curWord starting with a space. If the current line is to be more than
	 * NUM_LINE_CHARACTERS, start a new line. 
	 */
	private boolean writeOneWord(String curWord) {		
		if (counters.decrementTextCounter(1) == false) {	// stop writing
			return false;
		}
		
		if (counters.decrementLineCounter(curWord.length() + 1) == true) {	
			fileWriter.print(" " + curWord);
		} else {	// start a new line
			counters.resetLineCounter();
			counters.decrementLineCounter(curWord.length());
			fileWriter.print("\n" + curWord);
		}				
		return true;
	}

	/**
	 * choose a random prefix from prefixes, the converted arraylist from this.myHashMap.KeySet
	 * big-O: O(1)
	 */
	private Prefix pickRandomPrefix(List<Prefix> prefixes) {		
		Prefix randPrefix = prefixes.get(randomNumGenerateWrapper(prefixes.size()));	// random access
		return randPrefix;
	}
	
	/**
	 * generate a random number range from 0 to size - 1
	 * In debug mode, choose size - 1 as return value
	 */
	private int randomNumGenerateWrapper(int size) {
		int index;
		if (isDebugMode) {
			index = size - 1;
		} else {
			index = randomNumGenerator.nextInt(size);
		}
		return index;
	}

	/**
	 * scan in a list of value of the number of this.prefixLength
	 * PRE: prefixLength < number of words in sourceFile
	 */
	private ArrayList<String> generateFirstPrefixList(Scanner fileScanner) throws BadPrefixLengthException{
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < prefixLength; i++) {
			if (fileScanner.hasNext()) {
				result.add(fileScanner.next());
			} else {
				 throw new BadPrefixLengthException(
				 "ERROR: prefixLength >= number of words in sourceFile");
			}
		}
		return result;
	}

	public Counters getCounters() {
		return counters;
	}

	public boolean getIsFirstWord() {
		return isFirstWord;
	}

	public void setIsFirstWord(boolean isFirstWord) {
		this.isFirstWord = isFirstWord;
	}
	
}
