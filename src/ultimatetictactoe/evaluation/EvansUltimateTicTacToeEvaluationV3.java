package ultimatetictactoe.evaluation;

import java.util.BitSet;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class EvansUltimateTicTacToeEvaluationV3 extends AbstractUltimateTicTacToeEvaluation {


	
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
		
		BitSet circleAdvantageBoard = BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCircle(), state.getBoardsCapturedByCross());
		BitSet crossAdvantageBoard = BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCross(), state.getBoardsCapturedByCircle());
		
		int circleBoardAdv = circleAdvantageBoard.cardinality();
		int crossBoardAdv = crossAdvantageBoard.cardinality();
		
		int boardAdvantageScore = circleBoardAdv - crossBoardAdv;
		
		int circleExtremeAdvantageScore = 0;
		int crossExtremeAdvantageScore = 0;
		
		
		for (int i = 0 ; i <= 8; i++) {
			if (circleBoardAdv >= 1) {
				if (circleAdvantageBoard.get(i)) {
					int circlePieces = BitSetUtils.getPotentialCaptureMoves(state.getCirclePieces()[i], state.getCrossPieces()[i]).cardinality();
					if (circlePieces >= 1) {
						circleExtremeAdvantageScore += 1;
					}
				}
			}
			if (crossBoardAdv >= 1) {
				if (crossAdvantageBoard.get(i)) {
					int crossPieces = BitSetUtils.getPotentialCaptureMoves(state.getCrossPieces()[i], state.getCirclePieces()[i]).cardinality();
					if (crossPieces >= 1) {
						crossExtremeAdvantageScore += 1;
					}
				}
			}
		}
		
		int ExtremeAdvantageScore = circleExtremeAdvantageScore - crossExtremeAdvantageScore;
		
		int boardCenterScore = state.getBoardsCapturedByCircle().get(4) ? 1 : 
			(state.getBoardsCapturedByCross().get(4) ? -1 : 0);
//		int boardCaptureDiffScore = state.getBoardsCapturedByCircle().cardinality() - 
//				state.getBoardsCapturedByCross().cardinality();
		
		return boardAdvantageScore * 200 + boardCenterScore * 10
			//	+ boardCaptureDiffScore * 2
				+ advantageScore + 2000 * ExtremeAdvantageScore;

	}

}
