package ultimatetictactoe.evaluation;

import common.Evaluation;
import ultimatetictactoe.UltimateTicTacToeGameState;

public abstract class AbstractUltimateTicTacToeEvaluation implements Evaluation<UltimateTicTacToeGameState> {

	protected abstract int evaluateNonTerminalState(UltimateTicTacToeGameState state);

	private final int MAX_SCORE = 999999999;
	
	@Override
	public int evaluate(UltimateTicTacToeGameState state) {
		if (state.hasCircleWon()) {
			return MAX_SCORE;
		} else if (state.hasCrossWon()) {
			return -MAX_SCORE;
		} else if (state.getMoves().isEmpty()){ 
			return 0;
		} else {
			return evaluateNonTerminalState(state);
		}
	}

}
