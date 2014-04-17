import java.util.ArrayList;
import java.util.Scanner;

public class PolyProg {

	private final static int NUM_POLIES = 10;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);		
		Poly[] polies = new Poly[NUM_POLIES];
		
		// initialize the polies array
		for (int i = 0; i < NUM_POLIES; i++) {
			polies[i] = new Poly();
		}
		
		while (true) {
			String commandString = in.nextLine();
			Scanner commandScanner = new Scanner(commandString);

			if (commandScanner.hasNext()) {
				String firstArgument = commandScanner.next();
				if (firstArgument.equalsIgnoreCase("create")) {
					doCreate(commandScanner, in, polies);
				} else if (firstArgument.equalsIgnoreCase("print")) {
					doPrint(commandScanner, polies);
				} else if (firstArgument.equalsIgnoreCase("eval")) {
					doEval(commandScanner, in, polies);
				} else if (firstArgument.equalsIgnoreCase("help")) {
					doHelp();
				} else if (firstArgument.equalsIgnoreCase("quit")) {
					return;
				} else {
					printIllegalCommandMessage();
				}				
			}
		}

	}

	private static void doHelp() {
		System.out.println("create [index: 0 ~ " + (NUM_POLIES - 1) + "]");
		System.out.println("print [index: 0 ~ " + (NUM_POLIES - 1) + "]");
		System.out.println("eval [index: 0 ~ " + (NUM_POLIES - 1) + "]");
		System.out.println("quit");
	}

	private static void doEval(Scanner commandScanner, Scanner in, Poly[] polies) {
		if (commandScanner.hasNextInt()) {
			int index = commandScanner.nextInt();
			if (isValidated(index)) {
				evalOnePoly(polies, index, in);
			} else {
				printOutOfBoundMessage();
			}
		} else {
			printIllegalCommandMessage();
		}
	}

	private static void doPrint(Scanner commandScanner, Poly[] polies) {
		if (commandScanner.hasNextInt()) {
			int index = commandScanner.nextInt();
			if (isValidated(index)) {
				printOnePoly(polies, index);
			} else {
				printOutOfBoundMessage();
			}
		} else {
			printIllegalCommandMessage();
		}
		
	}

	private static void doCreate(Scanner commandScanner, Scanner in, Poly[] polies) {
		if (commandScanner.hasNextInt()) {
			int index = commandScanner.nextInt();
			if (isValidated(index)) {
				createOnePoly(polies, index, in);
			} else {
				printOutOfBoundMessage();
			}
		} else {
			printIllegalCommandMessage();
		}
	}

	private static void printOutOfBoundMessage() {
		System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
	}

	private static void printIllegalCommandMessage() {
		System.out.println("ERROR: Illegal command.  Type 'help' for command options.");
	}

	private static boolean isValidated(int index) {
		return (index >= 0 && index < NUM_POLIES);
	}

	private static void evalOnePoly(Poly[] polies, int index, Scanner in) {
		System.out.println("Enter a floating point value for x: ");
		if (in.hasNextFloat()) {
			float xValue = in.nextFloat();
			System.out.println(polies[index].eval(xValue));
		} else {
			in.nextLine();
			System.out.println("ERROR: Illegal x value.  Type 'help' for command options.");
		}
	}

	private static void printOnePoly(Poly[] polies, int index) {
		System.out.println(polies[index].toFormattedString());		
	}

	private static void createOnePoly(Poly[] polies, int index, Scanner in) {
		System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by <nl>");
		String input = in.nextLine();
		Scanner inputScanner = new Scanner(input);
		
		/*
		 * Store the first part of the input pair and see if there is a second part;
		 * if not, ignore the last value entered.
		 */
		ArrayList<Term> inputArrayList = new ArrayList<Term>();
		if (inputScanner.hasNextDouble()) {
			double coeff = storeCoeffCheckExpon(inputScanner);
			while (inputScanner.hasNextInt()) {
				int expon = inputScanner.nextInt();
				if (expon < 0) {
					System.out.println("WARNING: exponent must be nonnegative, change to absolute value");
				}
				Term t = new Term(coeff, Math.abs(expon)); // exponent has absolute value
				inputArrayList.add(t);
				if (inputScanner.hasNextDouble()) {
					coeff = storeCoeffCheckExpon(inputScanner);
				}
			}
		}
		
		polies[index] = new Poly(inputArrayList);
	}

	private static double storeCoeffCheckExpon(Scanner inputScanner) {
		double coeff = inputScanner.nextDouble();
		if (!inputScanner.hasNextInt()) {
			System.out.println("WARNING: odd number of values, ignore the last value entered");
		}
		return coeff;
	}

}
