public class AssertTester {

	public static void main(String[] args) {
		System.out.println("testing assert...");	
		Term test1 = new Term(2, 3);
		System.out.println(test1.toString());
		System.out.println("Expected: Term[coeff=2.0,expon=3]");
		
		Term test2 = new Term(2, -3);
		System.out.println(test2.toString());
		System.out.println("Expected: FAILED");
	}

}
