package ultimatetictactoe.evaluation;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class DaddysUltimateTicTacToeEvaluation extends AbstractUltimateTicTacToeEvaluation {

	private int captureScore = 5;
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
				int circlePieces = BitSetUtils.getPotentialCaptureMoves(state.getCirclePieces()[i], state.getCrossPieces()[i]).cardinality();
				int crossPieces = BitSetUtils.getPotentialCaptureMoves(state.getCrossPieces()[i], state.getCirclePieces()[i]).cardinality();
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
