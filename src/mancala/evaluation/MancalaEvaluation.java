package mancala.evaluation;

import mancala.MancalaGameState;

public interface MancalaEvaluation {
	
	public int evaluate(MancalaGameState state);
}
