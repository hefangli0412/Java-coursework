import java.util.ArrayList;
import java.util.Scanner;

/**
 * A command-based program that lets you manipulate polynomials. 
 * The program is bullet-proof and will handle any invalid input given by the user.
 * 
 * Version for PA3
 */
public class PolyProg {

	private final static int NUM_POLIES = 10;
	private static final int COMMAND_LENGTH_CREATE = 2;
	private static final int COMMAND_LENGTH_COPY = 3;
	private static final int COMMAND_LENGTH_PRINT = 2;
	private static final int COMMAND_LENGTH_EVAL = 2;
	private static final int COMMAND_LENGTH_ADDIN = 3;
	private static final int COMMAND_LENGTH_ADD = 4;
	private static final int COMMAND_LENGTH_MULT = 4;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);		
		Poly[] polies = new Poly[NUM_POLIES];
		
		// initialize the polies array
		for (int i = 0; i < NUM_POLIES; i++) {
			polies[i] = new Poly();
		}

		while (true) {
			try {
				String commandString = in.nextLine();
				String[] commandArray = commandString.split("[ 	]+");
	
				if (commandArray.length != 0) {
					String firstArgument = commandArray[0];
					switchCommand(firstArgument, commandArray, polies, in);	
				}
			} catch (BadCommandException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Parses the command array created in main method and
	 * passes control to relevant methods.
	 * @param firstArgument the first argument of the command array
	 * @param commandArray the command array to parse
	 * @param polies the Poly array to manipulate
	 * @param in standard input stream
	 * @throws BadCommandException
	 */
	private static void switchCommand(String firstArgument,
			String[] commandArray, Poly[] polies, Scanner in)
			throws BadCommandException {
		if (firstArgument.equalsIgnoreCase("create")) {
			doCreate(commandArray, in, polies);
		} else if (firstArgument.equalsIgnoreCase("copy")) {
			doCopy(commandArray, polies);
		} else if (firstArgument.equalsIgnoreCase("print")) {
			doPrint(commandArray, polies);
		} else if (firstArgument.equalsIgnoreCase("eval")) {
			doEval(commandArray, in, polies);
		} else if (firstArgument.equalsIgnoreCase("addin")) {
			doAddIn(commandArray, polies);
		} else if (firstArgument.equalsIgnoreCase("add")) {
			doAdd(commandArray, polies);
		} else if (firstArgument.equalsIgnoreCase("mult")) {
			doMulti(commandArray, polies);
		} else if (firstArgument.equalsIgnoreCase("help")) {
			doHelp();
		} else if (firstArgument.equalsIgnoreCase("quit")) {
			System.exit(0);
		} else {
			throw new BadCommandException(
					"ERROR: invalid command.  Type 'help' for command options.");
		}	
	}

	// **************************************************************
    //  DO METHOD(S)
	
	/**
	 * Prints help message.
	 */
	private static void doHelp() {
		System.out.println("*note that INDEX 0 ~ " + (NUM_POLIES - 1));
		System.out.println("create INDEX");
		System.out.println("copy DEST SOURCE");
		System.out.println("print INDEX");
		System.out.println("eval INDEX");
		System.out.println("addin SUM/ADD1 ADD2");
		System.out.println("add SUM ADD1 ADD2");
		System.out.println("mult PRODUCT MULT1 MULT2");
		System.out.println("quit");
	}

	/**
	 * Creates a polynomial according to standard input stream.
	 * @param commandArray the command array to parse
	 * @param in standard input stream
	 * @param polies the Poly array to manipulate
	 * @throws BadCommandException
	 */
	private static void doCreate(String[] commandArray, Scanner in,
			Poly[] polies) throws BadCommandException {
		checkCommandArrayLength(commandArray, COMMAND_LENGTH_CREATE);
		
		try {
			int index = Integer.parseInt(commandArray[1]);
			if (!isValidated(index)) {
				throw new BadCommandException(
						"ERROR: illegal polynomial index.  Type 'help' for command options.");
			}
			createOnePoly(polies, index, in); // implement create
		} catch (NumberFormatException e) {
			throw new BadCommandException(
					"ERROR: wrong input type.  Type 'help' for command options.");
		}
	}

	/**
	 * Called by doCreate. Creates a polynomial according to standard input stream.
	 * @param polies the Poly array to manipulate
	 * @param index the index of the Poly array
	 * @param in standard input stream
	 */
	private static void createOnePoly(Poly[] polies, int index, Scanner in) {
		boolean done = false;
		while (!done) {
			System.out.println(
					"Enter a space-separated sequence of coeff-power pairs terminated by <nl>");
			String inputData = in.nextLine();
			String[] inputDataArray = inputData.split("[ 	]+");
			try {
				readData(polies, index, inputDataArray);
				done = true;
			} catch (BadDataException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Called by createOnePoly. Creates a polynomial according to input array list.
	 * @param polies the Poly array to manipulate
	 * @param index the index of the Poly array
	 * @param inputDataArray the parameter array to create a polynomial
	 * @throws BadDataException
	 */
	private static void readData(Poly[] polies, int index,
			String[] inputDataArray) throws BadDataException {
		ArrayList<Term> inputArrayList = new ArrayList<Term>();
	
		try {
			int i = 0;
			while (i < inputDataArray.length - 1) {
				double coeff = Double.parseDouble(inputDataArray[i++]);
				int expon = Integer.parseInt(inputDataArray[i++]);
				if (expon < 0) {
					throw new BadDataException(
							"ERROR: negative exponent. Exponent is nonnegative");
				}
				Term t = new Term(coeff, expon);
				inputArrayList.add(t);
			}
	
			if (i == inputDataArray.length - 1) { // odd number of values
				Double.parseDouble(inputDataArray[i]);
				System.out.println(
						"WARNING: missing the last exponent. Ignore the last value entered");
			}
			polies[index] = new Poly(inputArrayList); // create Poly object
		} catch (NumberFormatException e) {
			throw new BadDataException(
					"ERROR: wrong input type.  A term is a coefficient (double)\n"
							+ "       followed by an exponent (int).");
		}
	}

	/**
	 * Evaluates a polynomial according to standard input value.
	 * @param commandArray the command array to parse
	 * @param in standard input stream
	 * @param polies the Poly array to manipulate
	 * @throws BadCommandException
	 */
	private static void doEval(String[] commandArray, Scanner in, Poly[] polies)
			throws BadCommandException {
		checkCommandArrayLength(commandArray, COMMAND_LENGTH_EVAL);
		
		try {
			int index = Integer.parseInt(commandArray[1]);
			if (!isValidated(index)) {
				throw new BadCommandException(
						"ERROR: illegal polynomial index.  Type 'help' for command options.");
			}
			evalOnePoly(polies, index, in); // implement eval
		} catch (NumberFormatException e) {
			throw new BadCommandException(
					"ERROR: wrong input type.  Type 'help' for command options.");
		}  
	}

	/**
	 * Called by doEval. Evaluates a polynomial according to standard input value.
	 * @param polies the Poly array to manipulate
	 * @param index the index of the Poly array
	 * @param in standard input stream
	 */
	private static void evalOnePoly(Poly[] polies, int index, Scanner in) {
		boolean done = false;
		while (!done) {
			System.out.println("Enter a floating point value for x: ");
			String inputData = in.nextLine();
			try {
				float xValue = Float.parseFloat(inputData);
				System.out.println(polies[index].eval(xValue));
				done = true;
			} catch (NumberFormatException e) {
				System.out.println("ERROR: illegal x value. x is float");
			}		
		}
	}

	/**
	 * Makes a copy of a polynomial.
	 * @param commandArray the command array to parse
	 * @param polies the Poly array to manipulate
	 * @throws BadCommandException
	 */
	private static void doCopy(String[] commandArray, Poly[] polies)
			throws BadCommandException {
		checkCommandArrayLength(commandArray, COMMAND_LENGTH_COPY);

		try {
			int dest = Integer.parseInt(commandArray[1]);
			int src = Integer.parseInt(commandArray[2]);
			if (!isValidated(dest) || !isValidated(src)) {
				throw new BadCommandException(
						"ERROR: illegal polynomial index.  Type 'help' for command options.");
			}
			polies[dest] = new Poly(polies[src]); // implement copy
		} catch (NumberFormatException e) {
			throw new BadCommandException(
					"ERROR: wrong input type.  Type 'help' for command options.");
		}
	}

	/**
	 * Prints information of a polynomial.
	 * @param commandArray the command array to parse
	 * @param polies the Poly array to manipulate
	 * @throws BadCommandException
	 */
	private static void doPrint(String[] commandArray, Poly[] polies)
			throws BadCommandException {
		checkCommandArrayLength(commandArray, COMMAND_LENGTH_PRINT);
		
		try {
			int index = Integer.parseInt(commandArray[1]);
			if (!isValidated(index)) {
				throw new BadCommandException(
						"ERROR: illegal polynomial index.  Type 'help' for command options.");
			}
			System.out.println(polies[index].toFormattedString()); // implement print
		} catch (NumberFormatException e) {
			throw new BadCommandException(
					"ERROR: wrong input type.  Type 'help' for command options.");
		}
	}

	/**
	 * Add a polynomial to an existing polynomial.
	 * @param commandArray the command array to parse
	 * @param polies the Poly array to manipulate
	 * @throws BadCommandException
	 */
	private static void doAddIn(String[] commandArray, Poly[] polies)
			throws BadCommandException {
		checkCommandArrayLength(commandArray, COMMAND_LENGTH_ADDIN);

		try {
			int add1 = Integer.parseInt(commandArray[1]);
			int add2 = Integer.parseInt(commandArray[2]);
			if (!isValidated(add1) || !isValidated(add2)) {
				throw new BadCommandException(
						"ERROR: illegal polynomial index.  Type 'help' for command options.");
			}
			polies[add1].addIn(polies[add2]); // implement addIn
		} catch (NumberFormatException e) {
			throw new BadCommandException(
					"ERROR: wrong input type.  Type 'help' for command options.");
		}			
	}

	/**
	 * Add two polynomials, resulting in a new one that is the sum of the first two.
	 * @param commandArray the command array to parse
	 * @param polies the Poly array to manipulate
	 * @throws BadCommandException
	 */
	private static void doAdd(String[] commandArray, Poly[] polies)
			throws BadCommandException {
		checkCommandArrayLength(commandArray, COMMAND_LENGTH_ADD);
		
		try {
			int sum = Integer.parseInt(commandArray[1]);
			int add1 = Integer.parseInt(commandArray[2]);
			int add2 = Integer.parseInt(commandArray[3]);
			if (!isValidated(sum) || !isValidated(add1) || !isValidated(add2)) {
				throw new BadCommandException(
						"ERROR: illegal polynomial index.  Type 'help' for command options.");
			}
			polies[sum] = polies[add1].add(polies[add2]); // implement add
		} catch (NumberFormatException e) {
			throw new BadCommandException(
					"ERROR: wrong input type.  Type 'help' for command options.");
		}					
	}

	/**
	 * Multiply two polynomials, resulting in a new one that is the product of the first two.
	 * @param commandArray the command array to parse
	 * @param polies the Poly array to manipulate
	 * @throws BadCommandException
	 */
	private static void doMulti(String[] commandArray, Poly[] polies)
			throws BadCommandException {
		checkCommandArrayLength(commandArray, COMMAND_LENGTH_MULT);

		try {
			int product = Integer.parseInt(commandArray[1]);
			int mult1 = Integer.parseInt(commandArray[2]);
			int mult2 = Integer.parseInt(commandArray[3]);
			if (!isValidated(product) || !isValidated(mult1) || !isValidated(mult2)) {
				throw new BadCommandException(
						"ERROR: illegal polynomial index.  Type 'help' for command options.");
			}
			polies[product] = polies[mult1].mult(polies[mult2]); // implement mult
		} catch (NumberFormatException e) {
			throw new BadCommandException(
					"ERROR: wrong input type.  Type 'help' for command options.");
		}					
	}
	
	// **************************************************************
    //  helper METHOD(S)

	/**
	 * Checks the number of command arguments.
	 * @param commandArray the command array to parse
	 * @param commandLength the expected command length
	 * @throws BadCommandException
	 */
	private static void checkCommandArrayLength(String[] commandArray,
			int commandLength) throws BadCommandException {
		if (commandArray.length > commandLength) {
			throw new BadCommandException(
					"ERROR: too many arguments.  Expecting " + (commandLength - 1) + 
					". \nFor more information type 'help'");
		} else if (commandArray.length < commandLength) {
			throw new BadCommandException(
					"ERROR: too few arguments.  Expecting " + (commandLength - 1) + 
					". \nFor more information type 'help'");
		}
		
	}

	/**
	 * Checks the validity of the index.
	 */
	private static boolean isValidated(int index) {
		return (index >= 0 && index < NUM_POLIES);
	}
}
