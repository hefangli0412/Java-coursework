import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * This class will have a main that's responsible for processing the
 * command-line arguments, opening and closing the files, and handling any
 * errors related to the above tasks. All the other functionality will be
 * delegated to other object(s) created in main and their methods.
 */

public class GenText {

	private static String sourceFile;
	private static String outFile;
	private static String err;
	
	private static int prefixLength;	// For order-k text, call "k" the prefixLength
	private static int numOutFileWords;	// the number of words to be generated
	private static boolean isDebugMode = false;	

	public static void main(String[] args) {
		if (processOptions(args) == false) {
			System.out.println(err);
			System.out.println("how to run: java GenText [-d] prefixlength numWords sourceFile outFile");
			return;
		}
		
		try {		
			PrintWriter writer = new PrintWriter(outFile, "UTF-8");	
			RandomTextGenerator rtGenerator = new RandomTextGenerator(
					new Scanner(new File(sourceFile)), writer, numOutFileWords,
					prefixLength, isDebugMode);
	
			rtGenerator.preprocessSourceFile();		
			rtGenerator.generateOutFile();			
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(
					"ERROR: input file does not exist or cannot write to output file");
		} catch (BadPrefixLengthException e) {
			System.out.println(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static boolean processOptions(String[] args) {		
		if (args[0].equals("-d")) {	// check to see if it is in debug mode
			isDebugMode = true;
			
			String[] newArgs = new String[args.length - 1];
			for (int i = 0; i < newArgs.length; i ++) {
				newArgs[i] = args[i+1];
			}
			args = newArgs;	// remove "-d" from command array args
		}			
		return checkOptions(args);
	}

	private static boolean checkOptions(String[] args) {
		// check the number of arguments
		if (args.length != ConstantValue.NUM_NORMAL_MODE_ARGS) {
			err = "ERROR: wrong number of arguments, expecting "
					+ ConstantValue.NUM_NORMAL_MODE_ARGS;
			return false;
		}
		// check argument format
		try {
			prefixLength = Integer.parseInt(args[0]);
			if (prefixLength < 1) {
				err = "ERROR: prefixLength no less than 1";
				return false;
			}
			numOutFileWords = Integer.parseInt(args[1]);
			if (numOutFileWords < 0) {
				err = "ERROR: numWords no less than 0";
				return false;
			}
			sourceFile = args[2];			
			outFile = args[3];
			return true;
		} catch (NumberFormatException e1) {
			err = "ERROR: prefixLength or numWords arguments are not integers";
			return false;
		}
	}

}
