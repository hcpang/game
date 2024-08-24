package ultimatetictactoe.evaluation;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class SimpleUltimateTicTacToeEvaluation extends AbstractUltimateTicTacToeEvaluation {

	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		// number of boards captured by o - number of boards captured by x
		return state.getBoardsCapturedByCircle().cardinality() - 
				state.getBoardsCapturedByCross().cardinality();

	}

}
