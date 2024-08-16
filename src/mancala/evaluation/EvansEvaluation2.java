package mancala.evaluation;

import mancala.MancalaGameState;
import mancala.MancalaMove;

public class EvansEvaluation2 extends EvansEvaluation {

	public EvansEvaluation2(int weight) {
		super(weight);
	}

	public int CalculateTotal (short[] board) {
		int total = 0;
		for (int i = 1; i <= 13 && i != 7; i++) {
			total += board[i];
		}
		return total; 
	}

	@Override
	protected int evaluateBoardNonTerminalState(MancalaGameState state) {
		short[] board = state.getBoard();

		int sum = calculateNumBottomMarbles(board);
		int numofTakableMarbles = calculateNumTakeableMarbles(board);
		int numofVulnurableMarbles = calculateNumVulnurableMarbles(board);
		int numofEmptyPockets = calculateEmptyPockets(board);
		int totalMarblesNotInMancala = CalculateTotal(board);

		//		return weight * sum + board[7]  - 2 * numofEmptyPockets;
		if (totalMarblesNotInMancala >= 10) {
			return (board[7] - board[0]) - numofEmptyPockets; 
		} else {
			return board[7] - board[0];
		}
	}


}
