package edu.se701.assign2.mancala;

/*  Class Node  */
public class Pit
{
    /* Owner of the pit */
    protected String owner;
    /* Pit number */
    protected int pitNumber;
	/* Stones in the pit */
    protected int stones;
    /* The next pit */
    protected Pit nextPit;
 
    /*  Constructor  */
    public Pit(String owner, int pitNumber)
    {
        this(owner, pitNumber, 0, null);
    }    
    /*  Constructor  */
    public Pit(String owner, int pitNumber, int d,Pit n)
    {
        this.stones = d;
        this.nextPit = n;
        this.owner = owner;
        this.pitNumber = pitNumber;
    }
    /*  Function to set link to next Node  */
    public void setNextPit(Pit n)
    {
        nextPit = n;
    }    
    /*  Function to set data to current Node  */
    public void setStones(int d)
    {
        stones = d;
    }    
    /*  Function to get link to next node  */
    public Pit getNextPit()
    {
        return nextPit;
    }    
    /*  Function to get data from current Node  */
    public int getStones()
    {
        return stones;
    }
    /* function to set the owner of this pit */
    public void setOwner(String owner){
    	this.owner = owner;
    }
    /* function to get the owner of this pit */
    public String getOwner(){
    	return this.owner;
    }
    public void setPitNumber(int pitNumber) {
    	this.pitNumber = pitNumber;
    }
    public int getPitNumber() {
    	return this.pitNumber;
    }
	@Override
	public String toString() {
		return "Pit [owner=" + owner + ", pitNumber=" + pitNumber + ", stones=" + stones + ", nextPit=[" + nextPit.pitNumber + " of " + nextPit.owner +  "]]";
	}
	
	public String getPitDetails() {
		if (this.stones < 10) {
			return " " + this.pitNumber + "[ " + this.stones + "] "; // note the trailing space in the end.
		}
		return " " + this.pitNumber + "[" + this.stones + "] "; // note the trailing space in the end.
	}
    
    
}
