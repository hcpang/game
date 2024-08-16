package mancala.evaluation;

import mancala.MancalaGameState;
import mancala.MancalaMove;

public class DaddysEvaluation extends EvansEvaluation {
	
	

	public DaddysEvaluation() {
		super(0);
	}


	
	@Override
	protected int evaluateBoardNonTerminalState(MancalaGameState state) {
		short[] board = state.getBoard();
		
		int numBottomMarbles = calculateNumBottomMarbles(board);
		int numTopMarbles = calculateNumTopMarbles(board);
		int numMarblesDiff = numBottomMarbles - numTopMarbles;
		
		int totalMarblesLeft = numBottomMarbles + numTopMarbles;
		
		int weight = totalMarblesLeft < 30 ? 1 : 0;
		
		return (board[7] - board[0]) + weight * numMarblesDiff;
	}


}
