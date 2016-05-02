package edu.se701.assign2.mancala;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MancalaBoard {
	
	/* Name of Player1 */
	String player1_Name;
	/* Name of Player2 */
	String player2_Name;
	/* tracks player1 turn */
	private boolean isPlayer1Turn;
	/* tracks completion of Game. */
	boolean isGameOver;
	/* MancalaPit of player11 */
	public MancalaPit Player1Mancala;
	/* MancalaPit of Player2 */
	public MancalaPit Player2Mancala;
	/* board/game message */
	String message;
	
	public Pit[] Player1Pits;
	public Pit[] Player2Pits;
	
	public MancalaBoard(String player1_Name, String player2_Name) {
		// initialize the names
		this.player1_Name = player1_Name;
		this.player2_Name = player2_Name;
		/* Set turn as Player1 */
		this.setPlayer1Turn(true);
		/* sets game over as false. */
		this.isGameOver = false;
		
		// define the arrays
		Player1Pits = new Pit[6];
		Player2Pits = new Pit[6];
		this.initialiseBoard();
	}
	
	private void initialiseBoard(){
		/* Define Player1 Pits in receding order */
		Player1Mancala = new MancalaPit(this.player1_Name, 0, null);
		Pit player1_Pit6 = new Pit(this.player1_Name, 6, 4, Player1Mancala);
		Pit player1_Pit5 = new Pit(this.player1_Name, 5, 4, player1_Pit6);
		Pit player1_Pit4 = new Pit(this.player1_Name, 4, 4, player1_Pit5);
		Pit player1_Pit3 = new Pit(this.player1_Name, 3, 4, player1_Pit4);
		Pit player1_Pit2 = new Pit(this.player1_Name, 2, 4, player1_Pit3);
		Pit player1_Pit1 = new Pit(this.player1_Name, 1, 4, player1_Pit2);
		
		/* Define Player1 Pits in receding order */
		Player2Mancala = new MancalaPit(this.player2_Name, 0, null);
		Pit player2_Pit6 = new Pit(this.player2_Name, 6, 4, Player2Mancala);
		Pit player2_Pit5 = new Pit(this.player2_Name, 5, 4, player2_Pit6);
		Pit player2_Pit4 = new Pit(this.player2_Name, 4, 4, player2_Pit5);
		Pit player2_Pit3 = new Pit(this.player2_Name, 3, 4, player2_Pit4);
		Pit player2_Pit2 = new Pit(this.player2_Name, 2, 4, player2_Pit3);
		Pit player2_Pit1 = new Pit(this.player2_Name, 1, 4, player2_Pit2);
		
		/* Complete the circle */
		Player1Mancala.setNextPit(player2_Pit1);
		Player2Mancala.setNextPit(player1_Pit1);
		
		/* Initialize the Player1Pits array */
		this.Player1Pits[0] = player1_Pit1;
		this.Player1Pits[1] = player1_Pit2;
		this.Player1Pits[2] = player1_Pit3;
		this.Player1Pits[3] = player1_Pit4;
		this.Player1Pits[4] = player1_Pit5;
		this.Player1Pits[5] = player1_Pit6;
		
		/* Initialize the Player1Pits array */
		this.Player2Pits[0] = player2_Pit1;
		this.Player2Pits[1] = player2_Pit2;
		this.Player2Pits[2] = player2_Pit3;
		this.Player2Pits[3] = player2_Pit4;
		this.Player2Pits[4] = player2_Pit5;
		this.Player2Pits[5] = player2_Pit6;
	}
	
	private Pit getPlayer1Pit(int pitNumber) {
		return this.Player1Pits[pitNumber - 1];
	}
	// gets the pit of player2
	private Pit getPlayer2Pit(int pitNumber) {
		return this.Player2Pits[pitNumber - 1];
	}

	/** makes a move on the Mancala table. returns if the move was successful or not*/
	public boolean makeMove(int pitNumber) {
		Pit aPit = null;
		String currentPlayer;
		
		/* if game is over, return */
		if (this.isGameOver) {
			// set message
			this.message="Game Over";
			return false;
		}
		// return false if the incomming number is 0 or greater than 6.
		if (pitNumber == 0 || pitNumber > 6) {
			// set message
			this.message="Invalid House Number; House numbers range from 1 to 6.";
			return false;
		}
		
		// get the Pit
		if (this.isPlayer1Turn()) {
			aPit = this.getPlayer1Pit(pitNumber);
		} else {
			aPit = this.getPlayer2Pit(pitNumber);
		}
		currentPlayer = aPit.getOwner();
		// check for empty capacity
		if (aPit.getStones() == 0) {
			// set message
			this.message="House is empty. Move again.";
			return false;
		}
		int stonesOnHand = aPit.getStones();
		aPit.setStones(0);
		while (true) {
			// get the nextPit
			aPit = aPit.getNextPit();
			// if the next pit is a Mancala pit of the other user.
			if (isMancalaPit(aPit) && !currentPlayer.equalsIgnoreCase(aPit.getOwner())) {
				continue;
			}
			aPit.setStones(aPit.getStones() + 1); // increment number of Stones
													// of the

			// if the number of stones on hand is z then break
			if (--stonesOnHand == 0) {
				// if current pit is the player's mancala Pit, then the player gets another turn.
				
				// if the current pit is not a mancala pit, check it a steal is necessary...
				if (!isMancalaPit(aPit)) {
					if (aPit.getOwner().equals(currentPlayer) && aPit.getStones() == 1) {
						Pit oppositePit = getOppositePit(aPit.getOwner(), aPit.getPitNumber());
						if (oppositePit.getStones() != 0) {
							// move all the stones present in opposite pit to owner's mancalaPit.
							Pit mancalaPit = getMancala(currentPlayer);
							mancalaPit.setStones(oppositePit.getStones() + mancalaPit.getStones() + aPit.getStones());
							oppositePit.setStones(0); // reset opposite pit's stone count.
							aPit.setStones(0);
						}
					}
					// flip the turn of the game.
					this.setPlayer1Turn(!this.isPlayer1Turn());
				} 
				/*
				 * if the ending pit belongs to the same owner and it was empty
				 */
				/* if (aPit.getOwner().equals(currentPlayer) && aPit.getStones() == 1) {
					Pit oppositePit = getOppositePit(aPit.getOwner(), aPit.getPitNumber());
					if (oppositePit.getStones() != 0) {
						// move all the stones present in opposite pit to owner's mancalaPit.
						Pit mancalaPit = getMancala(currentPlayer);
						mancalaPit.setStones(oppositePit.getStones() + mancalaPit.getStones() + aPit.getStones());
						oppositePit.setStones(0); // reset opposite pit's stone count.
						aPit.setStones(0);
					}
				} */
				/* Determine if Game is over. */
				if (0 == getStonesInPits(this.getNextPlayer())) {
					if (this.player1_Name.equalsIgnoreCase(this.getWinner())) {
						this.message = "Player 1 wins!";
					} else if ("Tie".equalsIgnoreCase(this.getWinner())){
						this.message = "A tie!";
					} else {
						this.message = "Player 2 wins!";
					}
					this.isGameOver = true;
				}

				break;
			}
			
			
		}

		return true;
	}
	
	/**  to get the pit that is opposite to players pit. */
	private Pit getOppositePit(String Owner, int pitNumber) throws NullPointerException {
		try {
			// if the owner of pitNumber is Player 1
			if (this.player1_Name.equals(Owner)) {
				// return player 2's pit that is opposite to player 1's pitNumber
				return Player2Pits[6 - pitNumber]; 
			}
			// else,... return player 1's pit that is opposite to player 2's pitNumber
			return Player1Pits[6 - pitNumber];
		} catch (IndexOutOfBoundsException ie) {
			return null;
		}
	}
	/** to determine if the current pit is a mancalaPit */
	private boolean isMancalaPit(Pit aPit) {
		if (aPit.getPitNumber() == 7) {
			return true;
		}
		return false;
	}
	/** returns Mancala Pit of the Owner */
	public Pit getMancala(String owner) {
		// If the owner is 
		if (this.player1_Name.equalsIgnoreCase(owner)) {
			return this.Player1Mancala;
		}
		return this.Player2Mancala;
	}
	
	public String getBoardState() {
		StringBuilder boardState = new StringBuilder();
		boardState.append("+----+-------+-------+-------+-------+-------+-------+----+\n");
		boardState.append("| P2 |");
		for (int i = 5; i >= 0; --i) {
			boardState.append(this.Player2Pits[i].getPitDetails());
			boardState.append("|");
		}
		boardState.append(this.Player1Mancala.getPitDetails() + "|");
		boardState.append("\n");
		boardState.append("|    |-------+-------+-------+-------+-------+-------|    |\n");
		boardState.append("|" + this.Player2Mancala.getPitDetails() + "|");
		for (int i = 0; i <= 5; ++i) {
			boardState.append(this.Player1Pits[i].getPitDetails());
			boardState.append("|");
		}
		boardState.append(" P1 |");
		boardState.append("\n");
		boardState.append("+----+-------+-------+-------+-------+-------+-------+----+");
		return boardState.toString();
	}
	
	private int getStonesInPits(String playerName) {
		int pitStoneCount = 0;
		Pit[] playerpits;
		if (this.player1_Name.equalsIgnoreCase(playerName)) {
			playerpits = this.Player1Pits;
		} else {
			playerpits = this.Player2Pits;
		}
		
		for (Pit aPit : playerpits){
			pitStoneCount += aPit.getStones();
		}
		return pitStoneCount;
	}
	/** returns the stones collected by the Player */
	public int getScore(String playerName) {
		return this.getMancala(playerName).getStones() + this.getStonesInPits(playerName);
	}
	/** returns the name of the winner */
	public String getWinner() {
		if (this.getScore(this.player1_Name) > this.getScore(this.player2_Name)) {
			return this.player1_Name;
		} else if (this.getScore(this.player1_Name) == this.getScore(this.player2_Name)) {
			return "Tie";
		}
		return this.player2_Name;
	}
	/** returns  */
	public boolean isGameOver() {
		return isGameOver;
	}
	/** */
	public String getNextPlayer() {
		if (this.isPlayer1Turn()) {
			return this.player1_Name;
		}
		return this.player2_Name;
	}
	/** */
	public String getMessage() {
		return this.message;
	}

	public boolean isPlayer1Turn() {
		return isPlayer1Turn;
	}

	public void setPlayer1Turn(boolean isPlayer1Turn) {
		this.isPlayer1Turn = isPlayer1Turn;
	}
}
