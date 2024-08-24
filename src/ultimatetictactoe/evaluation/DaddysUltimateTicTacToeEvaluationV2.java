package ultimatetictactoe.evaluation;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class DaddysUltimateTicTacToeEvaluationV2 extends AbstractUltimateTicTacToeEvaluation {


	
	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		// number of boards captured by o - number of boards captured by x
		
		int circleScore = 0;
		int crossScore = 0;
		
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
		int advantageScore = circleScore - crossScore;
		
		int circleBoardAdv = BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCircle(), state.getBoardsCapturedByCross()).cardinality();
		int crossBoardAdv = BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCross(), state.getBoardsCapturedByCircle()).cardinality();
		
		int boardAdvantageScore = circleBoardAdv - crossBoardAdv;
		
		int boardCenterScore = state.getBoardsCapturedByCircle().get(4) ? 1 : 
			(state.getBoardsCapturedByCross().get(4) ? -1 : 0);
		int boardCaptureDiffScore = state.getBoardsCapturedByCircle().cardinality() - 
				state.getBoardsCapturedByCross().cardinality();
		
		return boardAdvantageScore * 10 + boardCenterScore * 10
			//	+ boardCaptureDiffScore * 2
				+ advantageScore;

	}

}
