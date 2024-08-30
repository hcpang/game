package ultimatetictactoe.evaluation;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class EvansUltimateTicTacToeEvaluation2 extends AbstractUltimateTicTacToeEvaluation {

	
	@Override
	protected int evaluateNonTerminalState(UltimateTicTacToeGameState state) {
		// number of boards captured by o - number of boards captured by x
		
		int circleScore = 0;
		int crossScore = 0;
		
		for (int i = 0; i<=8; i++) {
			if (!state.getBoardsCapturedByCircle().get(i) && 
					!state.getBoardsCapturedByCross().get(i)) {
				circleScore+=NumPiecesinLines.getNumPiecesinLines(state.getCirclePieces()[i], state.getCrossPieces()[i]);
				crossScore+=NumPiecesinLines.getNumPiecesinLines(state.getCrossPieces()[i], state.getCirclePieces()[i]);
			}
		}
		
		int advantageScore = 0;
		if (state.isCirclesTurn()) {
			advantageScore = 1 * circleScore - 200 * crossScore;
		} else {
			advantageScore = 200 * circleScore - 1 * crossScore;
		}
//		
//		int circleBoardAdv = NumPiecesinLines.getNumPiecesinLines(state.getBoardsCapturedByCircle(), state.getBoardsCapturedByCross());
//		int crossBoardAdv = NumPiecesinLines.getNumPiecesinLines(state.getBoardsCapturedByCross(), state.getBoardsCapturedByCircle());
//		
//		int boardAdvantageScore = 0;
////		if (state.isCirclesTurn()) {
//		boardAdvantageScore = circleBoardAdv - crossBoardAdv;
////		} else {
////			boardAdvantageScore = 2 * circleBoardAdv - circleBoardAdv;
////		}
//		int boardCenterScore = state.getBoardsCapturedByCircle().get(4) ? 1 : 
//			(state.getBoardsCapturedByCross().get(4) ? -1 : 0);
//	
//		return boardAdvantageScore * 30 + + boardCenterScore * 10 + advantageScore;
		return advantageScore;
	}

}
