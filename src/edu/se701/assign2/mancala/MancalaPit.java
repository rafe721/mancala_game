package edu.se701.assign2.mancala;

/* For representation purposes only */
public class MancalaPit extends Pit {

	/* Constructor */
	/** Always has pitNumber: 7*/
	public MancalaPit(String owner) {
		super(owner, 7);
	}
	
	/* Constructor */
	/** Always has pitNumber: 7*/
	public MancalaPit(String owner, int d,Pit n) {
		super(owner, 7, d, n);
	}
	
	@Override
	public String getPitDetails() {
		if (this.stones < 10) {
			return "  " + this.stones + " "; // note the trailing space in the end.
		}
		return " " + this.stones + " "; // note the trailing space in the end.
	}
	
}
