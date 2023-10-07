/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.ArrayList;
import java.util.List;

public class Fragment {

	private int size;
	private String nucleotides;

	/**
	 * Creates a new Fragment based upon a String representing a sequence of
	 * nucleotides, containing only the uppercase characters G, C, A and T.
	 * 
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of
	 *                                  nucleotides
	 */
	public Fragment(String nucleotides) throws IllegalArgumentException {
		for (int x = 0; x < nucleotides.length(); x++) {
			if ((nucleotides.charAt(x) != 'G') && (nucleotides.charAt(x) != 'C')
					&& (nucleotides.charAt(x) != 'T') && (nucleotides.charAt(x) != 'A')) {
				throw new IllegalArgumentException();
			}
		}
		this.size = nucleotides.length();
		this.nucleotides = nucleotides;
	}

	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
		return this.size;

	}

	/**
	 * Returns a String representation of this fragment, exactly as was passed to
	 * the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		return this.nucleotides;
	}

	/**
	 * Return true if and only if this fragment contains the same sequence of
	 * nucleotides as another sequence.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Fragment)) {
			return false;
		}

		Fragment f = (Fragment) o;

		if (this.nucleotides.equals(f.toString())) {
			return true;
		}

		// Don't unconditionally return false; check that
		// the relevant instances variables in this and f
		// are semantically equal
		return false;
	}

	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment
	 * and the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2,
	 * not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	public int calculateOverlap(Fragment f) {
		int overlap = 0;
		int indexOfF = 0;
		int x;
		for (x = 0; x < this.nucleotides.length(); x++) {
			indexOfF = 0;
			if (f.toString().charAt(indexOfF) == this.nucleotides.charAt(x)) {
				for (int n = x; n < this.nucleotides.length(); n++) {
					if (this.nucleotides.charAt(n) == f.toString().charAt(indexOfF)) {
						overlap = overlap + 1;
						indexOfF++;
					} else {
						overlap = 0;
						break;
					}
				}
				if (overlap > 0) {
					return overlap;
				}
			}
		}
		return overlap;
	}

	/**
	 * Returns a new fragment based upon merging this fragment with another fragment
	 * f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right;
	 * the fragments will be overlapped as much as possible during the merge.
	 * 
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment
	 */
	public Fragment mergedWith(Fragment f) {
		int overlapped = calculateOverlap(f);
		String newNucleotides = "";
		String stringToMerge = f.toString().substring(overlapped, f.length());
		newNucleotides = newNucleotides + this.nucleotides + stringToMerge;
		Fragment fragToBeReturned = new Fragment(newNucleotides);
		return fragToBeReturned;
	}

	public static void main(String[] args) {
		Fragment f = new Fragment("GGCCAT");
		Fragment g = new Fragment("GCATAGG");
		System.out.print(f.calculateOverlap(g) + "\n");
		System.out.print(g.calculateOverlap(f) + "\n");

	}
}
