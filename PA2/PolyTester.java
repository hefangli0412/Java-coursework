import java.util.ArrayList;


public class PolyTester {

	public static void main(String[] args) {
		testConstructor1();
		testConstructor2();
		testConstructor3();
		testEval();
	}

	private static void testEval() {
		System.out.println("testing eval...");		
		Poly poly1 = new Poly();
		System.out.println(poly1.eval(2));
		System.out.println("Expected: 0.0");
	
		Poly poly2 = new Poly(new Term(3, 2));
		System.out.println(poly2.eval(2));
		System.out.println("Expected: 12.0");		
		
		Poly poly3 = new Poly(new Term(0, 5));
		System.out.println(poly3.eval(2));
		System.out.println("Expected: 0.0");	
		
		ArrayList<Term> testList4 = createFirstArrayList();
		Poly poly4 = new Poly(testList4);	
		System.out.println(poly4.eval(2));
		System.out.println("Expected: 99.0");			
	}

	private static void testConstructor1() {
		System.out.println("testing constructor1...");
		Poly poly = new Poly();
		System.out.println(poly.toFormattedString());
		System.out.println("Expected: zero poly:  \"0.0\"");
		System.out.println();
	}

	private static void testConstructor2() {
		System.out.println("testing constructor2...");	
		Poly poly1 = new Poly(new Term(3, 2));
		System.out.println(poly1.toFormattedString());
		System.out.println("Expected: 1-term poly: \"3.0x^2\"");
		System.out.println();
		
		Poly poly2 = new Poly(new Term(0, 0));
		System.out.println(poly2.toFormattedString());
		System.out.println("Expected: zero poly:  \"0.0\"");
		System.out.println();
		
		Poly poly3 = new Poly(new Term(34, 0));
		System.out.println(poly3.toFormattedString());
		System.out.println("Expected: 1-term poly: \"34.0\"");
		System.out.println();
	}

	private static void testConstructor3() {
		System.out.println("testing constructor3...");
		
		ArrayList<Term> testList1 = createFirstArrayList();		
		Poly poly1 = new Poly(testList1);
		System.out.println(poly1.toFormattedString());
		System.out.println("Expected: 5-term poly: \"2.0x^5 + 1.0x^4 + 6.0x^2 + -1.0x + -3.0\"");
		System.out.println();	
		
		ArrayList<Term> testList2 = createFirstTermList();		
		Poly poly2 = new Poly(testList2);
		System.out.println(poly2.toFormattedString());
		System.out.println("Expected: 3-term poly: \"3.0x^3 + 2.0x + -7.0\"");
		System.out.println();
		
		ArrayList<Term> testList3 = createSecondTermList();		
		Poly poly3 = new Poly(testList3);
		System.out.println(poly3.toFormattedString());
		System.out.println("Expected: zero poly:  \"0.0\"");
		System.out.println();
		
		ArrayList<Term> testList4 = createThirdTermList();		
		Poly poly4 = new Poly(testList4);
		System.out.println(poly4.toFormattedString());
		System.out.println("Expected: 1-term poly: \"34.0\"");
		System.out.println();
		
		ArrayList<Term> testList5 = createForthTermList();		
		Poly poly5 = new Poly(testList5);
		System.out.println(poly5.toFormattedString());
		System.out.println("Expected: 1-term poly: \"12.0x\"");
		System.out.println();
		
		ArrayList<Term> testList6 = createFifthTermList();		
		Poly poly6 = new Poly(testList6);
		System.out.println(poly6.toFormattedString());
		System.out.println("Expected: 2-term poly: \"3.0x^3 + -7.0\"");
		System.out.println();
		
		ArrayList<Term> testList7 = createSixthTermList();		
		Poly poly7 = new Poly(testList7);
		System.out.println(poly7.toFormattedString());
		System.out.println("Expected: 2-term poly: \"3.0x^3 + -7.0\"");
		System.out.println();
		
		ArrayList<Term> testList8 = createSeventhTermList();		
		Poly poly8 = new Poly(testList8);
		System.out.println(poly8.toFormattedString());
		System.out.println("Expected: zero poly:  \"0.0\"");
		System.out.println();
	}

	private static ArrayList<Term> createFirstArrayList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(3, 2));
		testList.add(new Term(-3, 0));
		testList.add(new Term(0, 100));
		testList.add(new Term(1, 3));
		testList.add(new Term(2, 5));
		testList.add(new Term(3, 2));
		testList.add(new Term(2, 4));
		testList.add(new Term(-1, 3));
		testList.add(new Term(-1, 4));
		testList.add(new Term(-1, 1));
		// System.out.println("Creates the first test arrayList : " + testList.toString());
		
		return testList;
	}
	
	private static ArrayList<Term> createFirstTermList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(3, 3));
		testList.add(new Term(-7, 0));
		testList.add(new Term(2, 1));
		// System.out.println("Creates the first test termList : " + testList.toString());
		
		return testList;
	}

	private static ArrayList<Term> createSecondTermList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(0, 0));
		// System.out.println("Creates the second test termList : " + testList.toString());
		
		return testList;
	}
	
	private static ArrayList<Term> createThirdTermList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(34, 0));
		// System.out.println("Creates the third test termList : " + testList.toString());
		
		return testList;
	}
	
	private static ArrayList<Term> createForthTermList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(12, 1));
		// System.out.println("Creates the forth test termList : " + testList.toString());
		
		return testList;
	}
	
	private static ArrayList<Term> createFifthTermList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(3, 3));
		testList.add(new Term(-2, 1));
		testList.add(new Term(-10, 0));
		testList.add(new Term(2, 1));
		testList.add(new Term(3, 0));
		// System.out.println("Creates the fifth test termList : " + testList.toString());
		
		return testList;
	}
	
	private static ArrayList<Term> createSixthTermList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(3, 3));
		testList.add(new Term(0, 2));
		testList.add(new Term(0, 1));
		testList.add(new Term(-7, 0));
		// System.out.println("Creates the sixth test termList : " + testList.toString());
		
		return testList;
	}
	
	private static ArrayList<Term> createSeventhTermList() {
		ArrayList<Term> testList = new ArrayList<Term>();
		testList.add(new Term(1, 2));
		testList.add(new Term(-2, 1));
		testList.add(new Term(-3, 2));
		testList.add(new Term(0, 3));
		testList.add(new Term(2, 2));
		testList.add(new Term(2, 1));
		// System.out.println("Creates the seventh test termList : " + testList.toString());
		
		return testList;
	}
}
