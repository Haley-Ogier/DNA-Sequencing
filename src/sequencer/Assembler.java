/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assembler {
	private List<Fragment> fragments;

	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not be
	 * modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	public Assembler(List<Fragment> fragments) {
		this.fragments = new ArrayList(fragments);
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		return this.fragments;
	}

	/**
	 * Attempts to perform a single assembly, returning true iff an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the two
	 * fragments with the largest overlap, breaking ties between merged fragments by
	 * choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is inserted
	 * into the list of fragments in this assembler, and the two original fragments
	 * are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */
	public boolean assembleOnce() {
		int maxoverlap = 0;
		int index1 = 0;
		int index2 = 0;
		int x = 0;
		int i = 0;
		for (x = 0; x < this.fragments.size(); x++) {
			for (i = 0; i < this.fragments.size(); i++) {
				if (this.fragments.get(x) != this.fragments.get(i)) {
					if (this.fragments.get(x).calculateOverlap(this.fragments.get(i)) > maxoverlap) {
						maxoverlap = this.fragments.get(x).calculateOverlap(this.fragments.get(i));
						index1 = x;
						index2 = i;
					}
				}
			}
		}
		if (index1 > 0 || index2 > 0) {
			Fragment toAdd = this.fragments.get(index1).mergedWith(this.fragments.get(index2));
			this.fragments.add(toAdd);
			Fragment elementToRemove1 = this.fragments.get(index1);
			Fragment elementToRemove2 = this.fragments.get(index2);
			this.fragments.remove(elementToRemove1);
			this.fragments.remove(elementToRemove2);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
		while (this.fragments.size() > 1) {
			this.assembleOnce();
		}
	}

	public static void main(String[] args) {
		Assembler a = new Assembler(
				Arrays.asList(new Fragment("GCATAG"), new Fragment("GGCCAT"), new Fragment("CATAGG")));
		a.assembleOnce();
		System.out.print(a.getFragments() + "\n");

		a.assembleOnce();
		System.out.print(a.getFragments());

	}

}
