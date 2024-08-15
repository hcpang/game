package mancala;

import java.io.IOException;

import cb.alphabeta.AlphaBeta;

public class MancalaMain2 {

	public static void main(String[] args) throws IOException {

		//MancalaGameState state = new MancalaGameState(true, false, new short[] {0, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4});
		MancalaGameState state = new MancalaGameState();


		AbstractMancalaEvaluation eval = new SimpleDiffEvaluation();
		AbstractMancalaEvaluation eval2 = new SimpleDiffEvaluation2();

		while(true) {


			state.print();

			if (state.getMoves().isEmpty()) {
				break;
			}
			AlphaBeta ab = new AlphaBeta(new MancalaGame(state, state.currentlyMaximizing() ? eval : eval));
			MancalaMove bestMove = MancalaMove.SKIP_TURN_MOVE;

			if (!state.isSkipTurn()) {
				int depth = 17;

				bestMove = (MancalaMove) ab.analyzeDepth(depth);

			}


			System.out.println("Making move for: " + bestMove.getPosition());
			state = state.makeMove(bestMove);
		}

	}

}
