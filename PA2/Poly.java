import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;


/**
   A polynomial. Polynomials evaluated and converted to a string form
   for printing.
*/
public class Poly {

    /**
       Creates the 0 polynomial
    */
    public Poly() {
    	termsArrayList = new ArrayList<Term>();
    	assert isValidPoly();      // calls isValidPoly on implicit parameter
    }

    /**
       Creates polynomial with single term given
     */
    public Poly(Term term) {
    	termsArrayList = new ArrayList<Term>();
    	   	
		if (term.getCoeff() != 0) {
			termsArrayList.add(term);
		}    	
		assert isValidPoly();      // calls isValidPoly on implicit parameter
    }


    /**
       Creates polynomial from a (possibly-empty) ArrayList of terms.
       The terms in the ArrayList have no restrictions: can be in any
       order, have 0 terms, and/or duplicate exponents.
       The given ArrayList is not modified by the constructor.
    */
	public Poly(ArrayList<Term> termList) {

		Term[] tempArr = termList.toArray(new Term[termList.size()]);
		// makes a copy into an array

		Arrays.sort(tempArr, new TermComparator());
		// sorts in decreasing order by exponent

		// System.out.println("DEBUG: sorted array: " + Arrays.toString(tempArr));
		// you can remove this line later

		// you complete the implementation of this method . . .
		termsArrayList = new ArrayList<Term>();

		if (tempArr.length != 0) {
			int expon = tempArr[0].getExpon();
			double coeff = tempArr[0].getCoeff();
			boolean reusable = true;

			for (int i = 1; i < tempArr.length; i++) {
				if (tempArr[i].getExpon() == tempArr[i - 1].getExpon()) {
					// equal to: compute coeff
					coeff = coeff + tempArr[i].getCoeff();
					reusable = false;
				} else { // smaller than: store the previous pair and update
					if (coeff != 0) {
						if (reusable) {
							termsArrayList.add(tempArr[i - 1]);
						} else {
							termsArrayList.add(new Term(coeff, expon));
						}
					}
					coeff = tempArr[i].getCoeff();
					expon = tempArr[i].getExpon();
				}
			}

			// store the last term if suitable
			if (coeff != 0) {
				termsArrayList.add(new Term(coeff, expon));
			}
			
			assert isValidPoly();      // calls isValidPoly on implicit parameter
		}

	}


    /**
       Returns the value of the poly at a given value of x.
     */
    public double eval(double x) {
    	double value = 0;
    	if (!termsArrayList.isEmpty()) {
	    	for (int i = 0; i < termsArrayList.size(); i++) {
	    		int expon = termsArrayList.get(i).getExpon();
	    		double coeff = termsArrayList.get(i).getCoeff();
	    		
	    		value += coeff * Math.pow(x, expon);
	    	}
    	}
    	
    	assert isValidPoly();      // calls isValidPoly on implicit parameter
    	return value;        
    }


    /**
       Return a String version of the polynomial with the 
       following format, shown by exmaple:
       zero poly:   "0.0"
       1-term poly: "3.2x^2"
       4-term poly: "3.0x^5 + -x^2 + x + -7.9"

       Poly is in a simplified form (only one term for any exponent),
       with no zero-coefficient terms, and terms are shown in
       decreasing order by exponent.
    */
    public String toFormattedString() {
    	if (termsArrayList.isEmpty()) {
    		assert isValidPoly();      // calls isValidPoly on implicit parameter
    		// return "zero poly:  \"0.0\"";      
    		return "0.0";
    	} else{
    		String formattedString = "";    		
    		// formattedString = termsArrayList.size() + "-term poly: \"";
    		
	    	for (int i = 0; i < termsArrayList.size(); i++) {
	    		int expon = termsArrayList.get(i).getExpon();
	    		double coeff = termsArrayList.get(i).getCoeff();
	    		
	    		if (coeff != 1 || (coeff == 1 && expon == 0)) {
	    			formattedString += coeff;
	    		}
	    		if (expon == 0) {
	    			formattedString += "";
	    		} else if (expon == 1) {
	    			formattedString += "x";
	    		} else {
	    			formattedString += "x^" + expon;
	    		}
	    		
	    		if (i != termsArrayList.size() - 1) {
	    			formattedString += " + ";
	    		}
	    	}
	    	
	    	// formattedString += "\"";
	    	assert isValidPoly();      // calls isValidPoly on implicit parameter
	    	return formattedString;
    	}
    	
    }

    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
       Returns true iff the poly data is in a valid state.
       Checking point: For a non-zero poly all the terms must be in decreasing order by exponent.
       Checking point: For a non-zero poly all expons must be nonnegative integers.
       Checking point: For a non-zero poly all coeffs must be nonzero.
    */
    private boolean isValidPoly() {
    	if (termsArrayList.isEmpty()) {
    		return true;
    	}
    	if (termsArrayList.get(0).getExpon() < 0 ||
				termsArrayList.get(0).getCoeff() == 0) {
			return false;
		}
    	for (int i = 1; i < termsArrayList.size(); i++) {
    		if (termsArrayList.get(i).getExpon() >= 
    				termsArrayList.get(i - 1).getExpon() ||
    				termsArrayList.get(i).getExpon() < 0 ||
    				termsArrayList.get(i).getCoeff() == 0) {
    			return false;
    		}
    	}
    	return true;     
    }



    // **************************************************************
    //  PRIVATE INSTANCE VARIABLE(S)
    /* Representation invariants: For a non-zero poly 
     *  all the terms must be in decreasing order by exponent.'
     *  all expons must be nonnegative integers.
     *  all coeffs must be nonzero.
     */
    private ArrayList<Term> termsArrayList;
    

}

// *****************************************************************
// Helper class needed for call to Arrays.sort above  -- DO NOT CHANGE

// comparator to be used by sort in ArrayList to Poly constructor, above
class TermComparator implements Comparator<Term> {

    // returns value < 0 if t1's exponent is > t2's exponent 
    //                                   (i.e. t1 should come before t2),
    // value > 0 if t1's exponent is < t2's exponent 
    //                                   (i.e., t1 should come after t2),
    // and 0 if their exponents are the same
    public int compare(Term t1, Term t2) {
	return t2.getExpon() - t1.getExpon();
    }
}