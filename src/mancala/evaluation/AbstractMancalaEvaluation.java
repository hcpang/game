package mancala.evaluation;

import mancala.MancalaGameState;

public abstract class AbstractMancalaEvaluation implements MancalaEvaluation {

	protected abstract int evaluateBoardNonTerminalState(MancalaGameState state);
	
	@Override
	public int evaluate(MancalaGameState state) {
		short[] board = state.getBoard();
		
		if (board[8] == 0 && 
				board[9] == 0 && board[10] == 0 &&
				board[11] == 0 && board[12] == 0 && board[13] == 0) {
			return board[0] - (board[1] + board[2] + board[3] + board[4] + board[5] + board[6] + board[7]);
		} else if (board[1] == 0 && 
				board[2] == 0 && board[3] == 0 &&
				board[4] == 0 && board[5] == 0 && board[6] == 0) {
			return (board[8] + board[9] + board[10] + board[11] + board[12] + board[13] + board[0]) - board[7];
		} else {
			return evaluateBoardNonTerminalState(state);
		}
	}

}
