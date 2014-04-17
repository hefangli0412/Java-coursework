import java.util.ArrayList;

public class PolyTester {

	public static void main(String[] args) {
		testConstructor1();
		testConstructor2();
		testConstructor34();
		testEval();
		testAddIn();
		testAdd();
		testMult();
	}

	private static void testConstructor1() {
		System.out.println("testing constructor1...");
		Poly poly = new Poly();
		System.out.println(poly.toFormattedString());
		System.out.println("Expected: 0.0");
		System.out.println();
	}

	private static void testConstructor2() {
		System.out.println("testing constructor2...");	
		Poly poly1 = new Poly(new Term(3, 2));
		System.out.println(poly1.toFormattedString());
		System.out.println("Expected: 3.0x^2");
		
		Poly poly2 = new Poly(new Term(0, 5));
		System.out.println(poly2.toFormattedString());
		System.out.println("Expected: 0.0");
		System.out.println();
	}
	
	private static void testConstructor34() {
		System.out.println("testing constructor3...");
		
		ArrayList<Term> list1 = create1();		
		Poly poly1 = new Poly(list1);
		System.out.println("poly1: " + poly1.toFormattedString());
		System.out.println(
				"Expected: 2.0x^5 + x^4 + 6.0x^2 + -x + -3.0");
		Poly poly11 = new Poly(poly1);
		System.out.println("poly11: " + poly11.toFormattedString());
		System.out.println(
				"Expected: 2.0x^5 + x^4 + 6.0x^2 + -x + -3.0");
		
		ArrayList<Term> list2 = create2();		
		Poly poly2 = new Poly(list2);
		System.out.println(poly2.toFormattedString());
		System.out.println("Expected: 3.0x^3 + 2.0x + -7.0");
		
		ArrayList<Term> list3 = create3();		
		Poly poly3 = new Poly(list3);
		System.out.println(poly3.toFormattedString());
		System.out.println("Expected: 0.0");

		ArrayList<Term> list4 = create4();		
		Poly poly4 = new Poly(list4);
		System.out.println(poly4.toFormattedString());
		System.out.println("Expected: 34.0");
		
		ArrayList<Term> list5 = create5();		
		Poly poly5 = new Poly(list5);
		System.out.println(poly5.toFormattedString());
		System.out.println("Expected: 12.0x");
		
		ArrayList<Term> list6 = create6();		
		Poly poly6 = new Poly(list6);
		System.out.println(poly6.toFormattedString());
		System.out.println("Expected: 3.0x^3 + -7.0");

		ArrayList<Term> list7 = create7();		
		Poly poly7 = new Poly(list7);
		System.out.println(poly7.toFormattedString());
		System.out.println("Expected: 3.0x^3 + -7.0");
		
		ArrayList<Term> list8 = create8();		
		Poly poly8 = new Poly(list8);
		System.out.println(poly8.toFormattedString());
		System.out.println("Expected: 0.0");
		
		ArrayList<Term> list9 = create9();		
		Poly poly9 = new Poly(list9);
		System.out.println(poly9.toFormattedString());
		System.out.println("Expected: 2.0^x + 7.0");
		System.out.println();
	}
	
	private static void testMult() {
		System.out.println("testing multi...");		
		
		ArrayList<Term> list1 = createMultTermList1();		
		Poly poly1 = new Poly(list1);	
		ArrayList<Term> list2 = createMultTermList2();		
		Poly poly2 = new Poly(list2);	
		Poly poly3 = poly1.mult(poly2);
		System.out.println(poly3.toFormattedString());
		System.out.println(
				"Expected: 3.0x^8 + -7.0x^6 + 7.0x^5 + -6.0x^4 + -6.0x^3 + 10.0x + 35.0");
		
		Poly poly4 = new Poly();
		Poly poly5 = poly1.mult(poly4);
		System.out.println(poly5.toFormattedString());
		System.out.println("Expected: 0.0");
	}

	private static void testAdd() {
		System.out.println("testing add...");		
		
		ArrayList<Term> list1 = create1();		
		Poly poly1 = new Poly(list1);		
		ArrayList<Term> list2 = create2();		
		Poly poly2 = new Poly(list2);		
		Poly poly3 = poly1.add(poly2);
		System.out.println("poly1: " + poly1.toFormattedString());
		System.out.println("poly2: " + poly2.toFormattedString());
		System.out.println("poly3: " + poly3.toFormattedString());
		System.out.println("Expected: 2.0x^5 + x^4 + 3.0x^3 + 6.0x^2 + x + -10.0");

		poly3.addIn(poly2);
		poly3.addIn(poly2);
		System.out.println("poly1: " + poly1.toFormattedString());
		System.out.println("poly3: " + poly3.toFormattedString());
		System.out.println("Expected: not identical (test independency)");
		
		ArrayList<Term> list4 = add4();
		Poly poly4 = new Poly(list4);
		ArrayList<Term> list5 = add5();
		Poly poly5 = new Poly(list5);
		Poly poly6 = poly4.add(poly5);
		System.out.println("poly4: " + poly4.toFormattedString());
		System.out.println("poly5: " + poly5.toFormattedString());
		System.out.println("poly6: " + poly6.toFormattedString());
		System.out.println("Expected: x^5 + 2.0x + 12.0");
		
		
		System.out.println();		
	}

	private static void testAddIn() {
		System.out.println("testing addIn...");		
		
		ArrayList<Term> list1 = create1();		
		Poly poly1 = new Poly(list1);		
		ArrayList<Term> list2 = create2();		
		Poly poly2 = new Poly(list2);
		poly1.addIn(poly2);
		System.out.println(poly1.toFormattedString());
		System.out.println("Expected: 2.0x^5 + x^4 + 3.0x^3 + 6.0x^2 + x + -10.0");

		ArrayList<Term> list3 = createAddZeroTermList1();		
		Poly poly3 = new Poly(list3);
		ArrayList<Term> list4 = createAddZeroTermList2();		
		Poly poly4 = new Poly(list4);
		poly3.addIn(poly4);
		System.out.println(poly3.toFormattedString());
		System.out.println("Expected: 2.0x^5");
		System.out.println();
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
		
		ArrayList<Term> list1 = create1();
		Poly poly4 = new Poly(list1);	
		System.out.println(poly4.eval(2));
		System.out.println("Expected: 99.0");	
		System.out.println();
	}

	private static ArrayList<Term> create1() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(3, 2));
		list.add(new Term(-3, 0));
		list.add(new Term(0, 100));
		list.add(new Term(1, 3));
		list.add(new Term(2, 5));
		list.add(new Term(3, 2));
		list.add(new Term(2, 4));
		list.add(new Term(-1, 3));
		list.add(new Term(-1, 4));
		list.add(new Term(-1, 1));
		
		return list;
	}
	
	private static ArrayList<Term> create2() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(3, 3));
		list.add(new Term(-7, 0));
		list.add(new Term(2, 1));
		
		return list;
	}

	private static ArrayList<Term> create3() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(0, 0));
		
		return list;
	}
	
	private static ArrayList<Term> create4() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(34, 0));
		
		return list;
	}
	
	private static ArrayList<Term> create5() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(12, 1));
		
		return list;
	}
	
	private static ArrayList<Term> create6() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(3, 3));
		list.add(new Term(-2, 1));
		list.add(new Term(-10, 0));
		list.add(new Term(2, 1));
		list.add(new Term(3, 0));
		
		return list;
	}
	
	private static ArrayList<Term> create7() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(3, 3));
		list.add(new Term(0, 2));
		list.add(new Term(0, 1));
		list.add(new Term(-7, 0));
		
		return list;
	}
	
	private static ArrayList<Term> create8() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(1, 2));
		list.add(new Term(-2, 1));
		list.add(new Term(-3, 2));
		list.add(new Term(0, 3));
		list.add(new Term(2, 2));
		list.add(new Term(2, 1));
		
		return list;
	}

	private static ArrayList<Term> create9() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(2, 1));
		list.add(new Term(3, 3));
		list.add(new Term(7, 0));
		list.add(new Term(2, 3));
		list.add(new Term(-5, 3));
		list.add(new Term(0, 10));
		
		return list;
	}

	private static ArrayList<Term> add4() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(3, 3));
		list.add(new Term(2, 1));
		list.add(new Term(7, 0));
		
		return list;
	}
	
	private static ArrayList<Term> add5() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(1, 5));
		list.add(new Term(-3, 3));
		list.add(new Term(5, 0));
		
		return list;
	}
	
	private static ArrayList<Term> createMultTermList1() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(3, 3));
		list.add(new Term(2, 1));
		list.add(new Term(7, 0));
		return list;
	}
	
	private static ArrayList<Term> createMultTermList2() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(1, 5));
		list.add(new Term(-3, 3));
		list.add(new Term(5, 0));		
		return list;
	}
	
	private static ArrayList<Term> createAddZeroTermList1() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(1, 5));
		list.add(new Term(-3, 3));
		list.add(new Term(5, 0));		
		return list;
	}
	
	private static ArrayList<Term> createAddZeroTermList2() {
		ArrayList<Term> list = new ArrayList<Term>();
		list.add(new Term(1, 5));
		list.add(new Term(3, 3));
		list.add(new Term(-5, 0));		
		return list;
	}
}
