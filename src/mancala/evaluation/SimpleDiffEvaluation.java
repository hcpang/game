package mancala.evaluation;

import mancala.MancalaGameState;

public class SimpleDiffEvaluation extends AbstractMancalaEvaluation {

	@Override
	protected int evaluateBoardNonTerminalState(MancalaGameState state) {
		short[] board = state.getBoard();
		
		return board[7] - board[0];
	}


}
