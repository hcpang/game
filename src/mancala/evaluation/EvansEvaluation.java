package mancala.evaluation;

import mancala.MancalaGameState;
import mancala.MancalaMove;

public class EvansEvaluation extends AbstractMancalaEvaluation {
	
	private int weight;
	
	

	public EvansEvaluation(int weight) {
		super();
		this.weight = weight;
	}


	public int calculateNumTakeableMarbles(short[] board) {
		int numofTakableMarbles = 0;
		for (int i = 12; i >= 8; i--) {
			if (i + board[i]<=13 && board[i]>0) {
				if (board[i + board[i]] == 0) {
					numofTakableMarbles += board[MancalaMove.OPPOSITE_LOOKUP[i + board[i]]] + 1;
				}
			}
		}
		return numofTakableMarbles;
	}
	

	public int calculateNumBottomMarbles(short[] board) {
		int sum = 0;
		for (int i = 1; i <= 6; i++) {
			sum += board[i];
		}
		return sum;
	}
	
	public int calculateNumTopMarbles(short[] board) {
		int sum = 0;
		for (int i = 8; i <= 13; i++) {
			sum += board[i];
		}
		return sum;
	}
	
	
	public int calculateNumVulnurableMarbles(short[] board) {
		int numofVulnurableMarbles = 0; 
		for (int i = 12; i >= 8; i--) {
			if (board[MancalaMove.OPPOSITE_LOOKUP[i]] == 0) {
				numofVulnurableMarbles += board[i];
			}
		}
		return numofVulnurableMarbles; 
	}
	
	public int calculateEmptyPockets(short[] board) {
		int numofEmptyPockets = 0;
		for (int i = 13; i>=8; i--) {
			if (board[i] == 0) {
				numofEmptyPockets +=1;
			}
		}
		return numofEmptyPockets;
	}
	
	
	@Override
	protected int evaluateBoardNonTerminalState(MancalaGameState state) {
		short[] board = state.getBoard();
		
		int sum = calculateNumBottomMarbles(board);
		int numofTakableMarbles = calculateNumTakeableMarbles(board);
		int numofVulnurableMarbles = calculateNumVulnurableMarbles(board);
		int numofEmptyPockets = calculateEmptyPockets(board);
		
//		return weight * sum + board[7]  - 2 * numofEmptyPockets;
		return (board[7] - board[0]) - numofEmptyPockets; 
	}


}
