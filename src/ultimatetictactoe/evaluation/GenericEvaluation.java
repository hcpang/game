package ultimatetictactoe.evaluation;

import java.util.BitSet;

import ultimatetictactoe.UltimateTicTacToeGameState;
import ultimatetictactoe.evaluation.weights.GenericEvaluationWeights;

public class GenericEvaluation extends AbstractUltimateTicTacToeEvaluation {

	private GenericEvaluationWeights weights;
	
	public GenericEvaluation(GenericEvaluationWeights weights) {
		this.weights = weights;
	}
	
	
	
	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		// number of boards captured by o - number of boards captured by x
		
		int circleScore = 0;
		int crossScore = 0;
		
		//2. Boards Captured
		int boardsCapturedByCircle = state.getBoardsCapturedByCircle().cardinality();
		int boardsCapturedByCross = state.getBoardsCapturedByCross().cardinality();
		
		circleScore += boardsCapturedByCircle * weights.getBoardsCapturedWeight();
		crossScore += boardsCapturedByCross * weights.getBoardsCapturedWeight();
		
		//4. Winning board that blocks opponent three in a row
		
		BitSet circleAdvantageBoard = BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCircle(), state.getBoardsCapturedByCross());
		BitSet crossAdvantageBoard =  BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCross(), state.getBoardsCapturedByCircle());
		/*
		for (int i = 0; i <= 8; i++) {
			if (crossAdvantageBoard.get(i) && state.getBoardsCapturedByCircle().get(i)) {
				circleScore += 150;
			}
			if (circleAdvantageBoard.get(i) && state.getBoardsCapturedByCross().get(i)) {
				crossScore += 150;
			}
		}
		*/
		
		//3. Two in a row Boards Captured
		int circleBoardAdv = circleAdvantageBoard.cardinality();
		int crossBoardAdv = crossAdvantageBoard.cardinality();
				
		circleScore += circleBoardAdv * weights.getAdvantageBoardCardinalityWeight();
		crossScore += crossBoardAdv * weights.getAdvantageBoardCardinalityWeight();
		
		//6. Two in a row Pieces
		for (int i = 0; i<=8; i++) {
			if (!state.getBoardsCapturedByCircle().get(i) && 
					!state.getBoardsCapturedByCross().get(i)) {
				int circlePieces = BitSetUtils.getPotentialCaptureMoves(state.getCirclePieces()[i], state.getCrossPieces()[i]).cardinality();
				int crossPieces = BitSetUtils.getPotentialCaptureMoves(state.getCrossPieces()[i], state.getCirclePieces()[i]).cardinality();
				circleScore += circlePieces * weights.getTwoInARowPiecesWeight();
				crossPieces += crossPieces * weights.getTwoInARowPiecesWeight();
				
				if (circlePieces > crossPieces) {
					circleScore += weights.getTwoInARowAdvantageWeight();
				} else if (crossPieces > circlePieces) {
					crossScore += weights.getTwoInARowAdvantageWeight();
				}
				
			}
		}
		
		circleScore += state.getBoardsCapturedByCircle().get(4) ? weights.getCenterBoardWeight() : 0;
		crossScore += state.getBoardsCapturedByCross().get(4) ? weights.getCenterBoardWeight() : 0;
		
		//7. Blocking opponent win on small board
//		for (int i = 0; i <= 8; i++) {
//			if (state.getCrossPieces()[i].get(i) == )
//		}
		
		if (state.isCirclesTurn()) {
			return 100 * circleScore - weights.getDefenseWeight() * crossScore;
		} else {
			return weights.getDefenseWeight() * circleScore - 100 * crossScore;
		}
	}

}
