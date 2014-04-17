import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
   A polynomial. Polynomials can be copied, added together, multiplied, 
   evaluated, and converted to a string form for printing.

   Version for PA3
*/
public class Poly {
	
    // **************************************************************
    //  CONSTRUCTOR(S)
	
    /**
       Creates the 0 polynomial
    */
    public Poly() {
    	m_list = new LinkedList<Term>();
    	assert isValidPoly();      
    }

    /**
       Creates polynomial with single term given
     */
    public Poly(Term term) {
    	m_list = new LinkedList<Term>();
	   	
		if (term.getCoeff() != 0) {
			m_list.add(term);
		}    	
		assert isValidPoly();      
    }

    /**
       Creates poly that is a copy of original
       (Copy constructor)
    */
    @SuppressWarnings("unchecked")
	public Poly(Poly original) {
    	m_list = (LinkedList<Term>) original.m_list.clone(); // Terms are immutable
    	assert isValidPoly();
    }

    /**
       Creates polynomial from a (possibly-empty) ArrayList of terms.
       The terms in the ArrayList have no restrictions: can be in any
       order, have 0 terms, and/or duplicate exponents.
       The given ArrayList is not modified by the constructor.
    */
    public Poly(ArrayList<Term> termList) {
	
		Term[] tempArr = termList.toArray(new Term[termList.size()]); // makes a copy into an array
	
		Arrays.sort(tempArr, new TermComparator()); // sorts in decreasing order by exponent
	
		// System.out.println("DEBUG: sorted array: " + Arrays.toString(tempArr));
	                                    	// you can remove this line later
	
		// you complete the implementation of this method . . .
		m_list = new LinkedList<Term>();
	
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
							m_list.add(tempArr[i - 1]);
						} else {
							m_list.add(new Term(coeff, expon));
						}
					}
					coeff = tempArr[i].getCoeff();
					expon = tempArr[i].getExpon();
				}
			}
	
			// stores the last term
			if (coeff != 0) {
				m_list.add(new Term(coeff, expon));
			}
			
			assert isValidPoly();
		}
    }

    // **************************************************************
    //  PUBLIC METHOD(S)
    
    /**
       Returns the Poly that is the sum of this polynomial and b
       (neither poly is modified)
       big-O:  O(m + n)
     */
    public Poly add(Poly b) {
    	Poly sum = new Poly(this);  	
    	sum.addIn(b);
    	
    	assert isValidPoly();
    	assert b.isValidPoly();
    	assert sum.isValidPoly();  
    	return sum;
    }

    /**
       Returns the value of the poly at a given value of x.
     */
    public double eval(double x) {
    	double value = 0;
    	if (!m_list.isEmpty()) {
    		ListIterator<Term> iter = m_list.listIterator();
	    	while (iter.hasNext()) {
	    		Term aTerm = iter.next();
	    		int expon = aTerm.getExpon();
	    		double coeff = aTerm.getCoeff();    		
	    		value += coeff * Math.pow(x, expon);
	    	}
    	}
    	
    	assert isValidPoly();      
    	return value;
    }

    /**
       Return a String version of the polynomial with the 
       following format, shown by exmaple:
       zero poly:   "0.0"
       1-term poly: "3.0x^2"
       4-term poly: "3.0x^5 + x^2 + 2.0x + 7.0"

       Poly is in a simplified form (only one term for any exponent),
       with no zero-coefficient terms, and terms are shown in
       decreasing order by exponent.
    */
    public String toFormattedString() {
    	if (m_list.isEmpty()) {
    		assert isValidPoly();        
    		return "0.0";
    	} else{
    		String formattedString = "";    		
    		
    		ListIterator<Term> iter = m_list.listIterator();
	    	while (iter.hasNext()) {
	    		Term aTerm = iter.next();
	    		int expon = aTerm.getExpon();
	    		double coeff = aTerm.getCoeff();
	    		
	    		if ((coeff != 1 && coeff != -1) || (coeff == 1 && expon == 0)) {
	    			formattedString += coeff;
	    		} else if (coeff == -1) {
	    			formattedString += "-";
	    		}
	    		if (expon == 0) {
	    			formattedString += "";
	    		} else if (expon == 1) {
	    			formattedString += "x";
	    		} else {
	    			formattedString += "x^" + expon;
	    		}
	    		
	    		if (iter.nextIndex() != m_list.size()) {
	    			formattedString += " + ";
	    		}
	    	}
	    	
	    	assert isValidPoly();     
	    	return formattedString;
    	}
    }

    /**
       Returns the Poly that is the product of this polynomial and b
       (neither poly is modified)
       big-O: at least O(m * n)
     */
    public Poly mult(Poly b) {
    	Poly[] subPolies = new Poly[b.m_list.size()]; // intermediate results that will be added together
    		
		ListIterator<Term> bIter = b.m_list.listIterator();
    	while (bIter.hasNext()) {
    		Term bTerm = bIter.next();
    		ArrayList<Term> subList = new ArrayList<Term>();
    										// corresponding to certain subPoly
    		ListIterator<Term> thisIter = m_list.listIterator();
    		while (thisIter.hasNext()) {
    			Term thisTerm = thisIter.next();
    			Term nTerm = new Term(bTerm.getCoeff() * thisTerm.getCoeff(), 
    					bTerm.getExpon() + thisTerm.getExpon());
    			subList.add(nTerm);
    		}    		
    		subPolies[bIter.previousIndex()] = new Poly(subList);
    	}
    	
    	// add up subPolies[] and result is subPolies[0]
    	for (int k = 1; k < subPolies.length; k++) {
    		subPolies[0].addIn(subPolies[k]);
    	}
    	
    	assert isValidPoly();
    	assert b.isValidPoly();    	
    	if (b.m_list.isEmpty()) {
    		return new Poly();
    	} else {
    		assert subPolies[0].isValidPoly();
    		return subPolies[0];
    	}
    }

    /**
       Adds b to this poly.  (mutator)
       (b is unchanged)
       big-O:  O(m + n)
    */
    public void addIn(Poly b) {    			
    	ListIterator<Term> thisIter = m_list.listIterator();		
		ListIterator<Term> bIter = b.m_list.listIterator();
		
		// As long as neither b nor this(a) past the end,
		// move the larger element into this(a)
		while (thisIter.hasNext() && bIter.hasNext()) {
			Term aTerm = thisIter.next();
			Term bTerm = bIter.next();		
			int aExpon = aTerm.getExpon();
			int bExpon = bTerm.getExpon();
			
			if (aExpon == bExpon) {
				double coeff = aTerm.getCoeff() + bTerm.getCoeff();
				if (coeff != 0) {
					Term nTerm = new Term(coeff, aExpon); 
					thisIter.set(nTerm); // compute and set
				} else {
					thisIter.remove(); // compute and remove zero-coeff term
				}
			} else if (aExpon < bExpon) { // insert b
				thisIter.previous();
				thisIter.add(bTerm);
			} else { // leave a
				bIter.previous();
			}
		}
		
		// Copy any remaining entries of b into this(a)
		while (bIter.hasNext()) {
			m_list.add(bIter.next());
		}
		
		assert isValidPoly();    
    	assert b.isValidPoly();
    }

    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
       Returns true iff the poly data is in a valid state.
       Checking point: For a non-zero poly,
       -- all the terms must be in decreasing order by exponent
       -- all expons must be nonnegative integers
       -- all coeffs must be nonzero
    */
    private boolean isValidPoly() {
    	if (m_list.isEmpty()) {
    		return true;
    	}
    	
    	ListIterator<Term> iter = m_list.listIterator();
    	Term firstTerm = iter.next();
    	if (firstTerm.getExpon() < 0 || firstTerm.getCoeff() == 0) {
			return false;
		}
    	
    	while (iter.hasNext()) {
    		Term prevTerm = iter.previous();
    		iter.next();    		    	
    		Term curTerm = iter.next();;
    		if (curTerm.getExpon() >= prevTerm.getExpon() ||
    				curTerm.getExpon() < 0 || curTerm.getCoeff() == 0) {
    			return false;
    		}
    	}
    	return true;     
    }

    // **************************************************************
    //  PRIVATE INSTANCE VARIABLE(S)
    
    /* Representation invariants: For a non-zero poly,
     * -- all the terms must be in decreasing order by exponent
     * -- all expons must be nonnegative integers
     * -- all coeffs must be nonzero
     */    
    private LinkedList<Term> m_list;
    
}

// *****************************************************************
// Helper class needed for call to Arrays.sort above  -- DO NOT CHANGE

// comparator to be used by sort in ArrayList to Poly constructor, above
class TermComparator implements Comparator<Term> {

    // returns value < 0 if t1's exponent is > t2's exponent (i.e. t1 should come before t2),
    // value > 0 if t1's exponent is < t2's exponent (i.e., t1 should come after t2),
    // and 0 if their exponents are the same
    public int compare(Term t1, Term t2) {
    	return t2.getExpon() - t1.getExpon();
    }
}
