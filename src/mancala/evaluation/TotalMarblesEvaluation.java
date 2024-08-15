package mancala.evaluation;

import mancala.MancalaGameState;

public class TotalMarblesEvaluation extends AbstractMancalaEvaluation {

	@Override
	protected int evaluateBoardNonTerminalState(MancalaGameState state) {
		short[] board = state.getBoard();
		
		int sum = 0;
		for (int i = 1; i <= 6; i++) {
			sum += board[i];
		}
		
		return 2 * sum +  board[7];
	}


}
