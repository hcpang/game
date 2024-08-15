package mancala.evaluation;

import mancala.MancalaGameState;

public abstract class AbstractMancalaEvaluation implements MancalaEvaluation {

	protected abstract int evaluateBoardNonTerminalState(MancalaGameState state);
	
	private int terminalScore(int score) {
		return score * 1000000;
	}
	
	@Override
	public int evaluate(MancalaGameState state) {
		if (state.isTerminalState()) {
			return terminalScore(state.getBottomScore() - state.getTopScore());
		} else {
			return evaluateBoardNonTerminalState(state);
		}
	}

}
