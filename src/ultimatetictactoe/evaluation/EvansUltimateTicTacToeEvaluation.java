package ultimatetictactoe.evaluation;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class EvansUltimateTicTacToeEvaluation extends AbstractUltimateTicTacToeEvaluation {

	private int captureScore = 2;
	private int advantageScore = 1;
	
	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		// number of boards captured by o - number of boards captured by x
		
		int circleScore = 0;
		int crossScore = 0;
		
		for (int i = 0; i<=8; i++) {
			if (state.getBoardsCapturedByCircle().get(i)) {
				circleScore += captureScore;
			} else if (state.getBoardsCapturedByCross().get(i)) {
				crossScore += captureScore;
			} else {
				int circlePieces = state.getCirclePieces()[i].cardinality();
				int crossPieces = state.getCrossPieces()[i].cardinality();
				if (circlePieces > crossPieces) {
					circleScore+=advantageScore;
				} else if (crossPieces > circlePieces) {
					crossScore += advantageScore;
				}
			}
		}
		return circleScore - crossScore;

	}

}
