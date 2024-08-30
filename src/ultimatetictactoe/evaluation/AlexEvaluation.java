package ultimatetictactoe.evaluation;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class AlexEvaluation extends AbstractUltimateTicTacToeEvaluation {


	
	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		// number of boards captured by o - number of boards captured by x
		
		int circleScore = 0;
		int crossScore = 0;
		
		for (int i = 0; i<=8; i++) {
			if (!state.getBoardsCapturedByCircle().get(i) && 
					!state.getBoardsCapturedByCross().get(i)) {
				int circlePieces = BitSetUtils2.getPotentialCaptureandDefenseMoves(state.getCirclePieces()[i], state.getCrossPieces()[i]).cardinality();
				int crossPieces = BitSetUtils2.getPotentialCaptureandDefenseMoves(state.getCrossPieces()[i], state.getCirclePieces()[i]).cardinality();
				if (circlePieces > crossPieces) {
					circleScore+=5;
				} else if (crossPieces > circlePieces) {
					crossScore += 5;
				}
			}
		}
		
		int advantageScore;
		if(state.isCirclesTurn()) {
			advantageScore =  1 * circleScore - 2 * crossScore;
		}else {
			advantageScore = 2 * circleScore - 1 * crossScore;
		}
		
		int circleBoardAdv = BitSetUtils2.getPotentialCaptureandDefenseMoves(state.getBoardsCapturedByCircle(), state.getBoardsCapturedByCross()).cardinality();
		int crossBoardAdv = BitSetUtils2.getPotentialCaptureandDefenseMoves(state.getBoardsCapturedByCross(), state.getBoardsCapturedByCircle()).cardinality();
		
		int boardAdvantageScore;
		if(state.isCirclesTurn()) {
			boardAdvantageScore = circleBoardAdv -  2 * crossBoardAdv;
		}else {
			boardAdvantageScore = 2 * circleBoardAdv -  crossBoardAdv;
		}
		
		int boardCenterScore = state.getBoardsCapturedByCircle().get(4) ? 1 : 
			(state.getBoardsCapturedByCross().get(4) ? -1 : 0);
		/*int boardCaptureDiffScore = state.getBoardsCapturedByCircle().cardinality() - 
				state.getBoardsCapturedByCross().cardinality();*/
		
		return boardAdvantageScore * 200 
				+ boardCenterScore * 2
			//	+ boardCaptureDiffScore * 2
				+ advantageScore * 2;

	}

}
