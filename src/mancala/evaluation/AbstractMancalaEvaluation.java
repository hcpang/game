package mancala.evaluation;

import common.Evaluation;
import mancala.MancalaGameState;

public abstract class AbstractMancalaEvaluation implements Evaluation<MancalaGameState> {

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
