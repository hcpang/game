package mancala;

public class SimpleDiffEvaluation2 extends AbstractMancalaEvaluation {

	@Override
	public int evaluate(MancalaGameState state) {
		short[] board = state.getBoard();
		
		int score = -board[0];
		
		for (int i = 1; i <= 7; i++) {
			score += board[i];
		}
		for (int i = 8; i <= 13; i++) {
			score -= board[i];
		}	
						
		return score;
	}

}
