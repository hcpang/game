package ultimatetictactoe.evaluation;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class EvansUltimateTicTacToeEvaluation extends AbstractUltimateTicTacToeEvaluation {

	private int captureScore = 2;
	private int advantageScore = 1;
	
	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		
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
		
		for (int i = 0; i<=8; i++) {
			if (!state.getBoardsCapturedByCircle().get(i) && 
					!state.getBoardsCapturedByCross().get(i)) {
				int circlePieces = BitSetUtils.getPotentialCaptureMoves(state.getCirclePieces()[i], state.getCrossPieces()[i]).cardinality();
				int crossPieces = BitSetUtils.getPotentialCaptureMoves(state.getCrossPieces()[i], state.getCirclePieces()[i]).cardinality();
				if (circlePieces > crossPieces) {
					circleScore+=1;
				} else if (crossPieces > circlePieces) {
					crossScore += 1;
				}
			}
		}
		return circleScore - crossScore;

	}

}
