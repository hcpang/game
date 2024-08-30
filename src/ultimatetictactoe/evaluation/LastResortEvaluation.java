package ultimatetictactoe.evaluation;

import java.util.BitSet;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class LastResortEvaluation extends AbstractUltimateTicTacToeEvaluation {


	
	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		// number of boards captured by o - number of boards captured by x
		
		int circleScore = 0;
		int crossScore = 0;
		
		//2. Boards Captured
		int boardsCapturedByCircle = state.getBoardsCapturedByCircle().cardinality();
		int boardsCapturedByCross = state.getBoardsCapturedByCross().cardinality();
		
		circleScore += boardsCapturedByCircle * 100;
		crossScore += boardsCapturedByCross * 100;
		
		//4. Winning board that blocks opponent three in a row
		BitSet circleAdvantageBoard = BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCircle(), state.getBoardsCapturedByCross());
		BitSet crossAdvantageBoard =  BitSetUtils.getPotentialCaptureMoves(state.getBoardsCapturedByCross(), state.getBoardsCapturedByCircle());
		for (int i = 0; i <= 8; i++) {
			if (crossAdvantageBoard.get(i) && state.getBoardsCapturedByCircle().get(i)) {
				circleScore += 150;
			}
			if (circleAdvantageBoard.get(i) && state.getBoardsCapturedByCross().get(i)) {
				crossScore += 150;
			}
		}
		
		//3. Two in a row Boards Captured
				int circleBoardAdv = circleAdvantageBoard.cardinality();
				int crossBoardAdv = crossAdvantageBoard.cardinality();
				
				circleScore += circleBoardAdv * 200;
				crossScore += crossBoardAdv * 200;
		
		//6. Two in a row Pieces
		for (int i = 0; i<=8; i++) {
			if (!state.getBoardsCapturedByCircle().get(i) && 
					!state.getBoardsCapturedByCross().get(i)) {
				int circlePieces = BitSetUtils.getPotentialCaptureMoves(state.getCirclePieces()[i], state.getCrossPieces()[i]).cardinality();
				int crossPieces = BitSetUtils.getPotentialCaptureMoves(state.getCrossPieces()[i], state.getCirclePieces()[i]).cardinality();
				circleScore += circlePieces * 5;
				crossPieces += crossPieces * 5;
			}
		}
		
		//7. Blocking opponent win on small board
//		for (int i = 0; i <= 8; i++) {
//			if (state.getCrossPieces()[i].get(i) == )
//		}
		
		if (state.isCirclesTurn()) {
			return 1 * circleScore - 3 * crossScore;
		} else {
			return 3 * circleScore - 1 * crossScore;
		}
	}

}
