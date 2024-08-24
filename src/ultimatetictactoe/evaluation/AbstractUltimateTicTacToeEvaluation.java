package ultimatetictactoe.evaluation;

import common.Evaluation;
import ultimatetictactoe.UltimateTicTacToeGameState;

public abstract class AbstractUltimateTicTacToeEvaluation implements Evaluation<UltimateTicTacToeGameState> {

	protected abstract int evaluateNonTerminalState(UltimateTicTacToeGameState state);

	@Override
	public int evaluate(UltimateTicTacToeGameState state) {
		if (state.hasCircleWon()) {
			return 999999;
		} else if (state.hasCrossWon()) {
			return -999999;
		} else if (state.getMoves().isEmpty()){ 
			return 0;
		} else {
			return evaluateNonTerminalState(state);
		}
	}

}
