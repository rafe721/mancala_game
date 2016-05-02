package kalah;

import java.util.Scanner;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import edu.se701.assign2.mancala.MancalaBoard;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		
		MancalaBoard aMancalaBoard = new MancalaBoard("Payal", "Rahul");
		String currentMove;
		Scanner scanner = new Scanner(System.in);
		boolean isSuccessfull = false;
		int i = 0;
		
		// Replace what's below with your implementation
		
		while (true) {
			try {
				this.printBoardState(io, aMancalaBoard);
				
				if (aMancalaBoard.isGameOver()) {
					io.println("Game over");
					this.printBoardState(io, aMancalaBoard);
					io.println("\tplayer 1:" + aMancalaBoard.getScore("Payal"));
					io.println("\tplayer 2:" + aMancalaBoard.getScore("Rahul"));
					io.println(aMancalaBoard.getMessage()); // print who won.
					break;
				}
				
				if (aMancalaBoard.isPlayer1Turn()) {
					// currentMove = io.readFromKeyboard("Player 1's turn - Specify house number or 'q' to quit: ");
					currentMove = Integer.toString(io.readInteger("Player P1's turn - Specify house number or 'q' to quit: ", 1, 6, 0, "q"));
					// this.printBoardState(io, aMancalaBoard);
				} else {
					// currentMove = io.readFromKeyboard("Player 2's turn - Specify house number or 'q' to quit: ");
					currentMove = Integer.toString(io.readInteger("Player P2's turn - Specify house number or 'q' to quit: ", 1, 6, 0, "q"));
				}
				// if input is 'q', quit
				if ("q".equalsIgnoreCase(currentMove) || "0".equalsIgnoreCase(currentMove)) {
					io.println("Game over");
					this.printBoardState(io, aMancalaBoard);
					break;
				}
				
				try {
					isSuccessfull = aMancalaBoard.makeMove(Integer.parseInt(currentMove));
					if (!isSuccessfull) {
						io.println(aMancalaBoard.getMessage());
						continue;
					}
				} catch (NumberFormatException nfe) {
					io.println("%%Message: '"+ "' must be between Invalid input. Input must range from 1 to 6. ");
					continue;
				} catch (Exception e) {
					io.println("Invalid input. Input must range from 1 to 6. ");
					continue;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printBoardState(IO io, MancalaBoard aMancalaBoard) {
		
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		io.print("| P2 |");
		for (int i = 5; i >= 0; --i) {
			io.print(aMancalaBoard.Player2Pits[i].getPitDetails());
			io.print("|");
		}
		io.println(aMancalaBoard.Player1Mancala.getPitDetails() + "|");
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		io.print("|" + aMancalaBoard.Player2Mancala.getPitDetails() + "|");
		for (int i = 0; i <= 5; ++i) {
			io.print(aMancalaBoard.Player1Pits[i].getPitDetails());
			io.print("|");
		}
		io.println(" P1 |");
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		
	}
}
