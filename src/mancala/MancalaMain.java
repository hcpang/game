package mancala;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import cb.alphabeta.AlphaBeta;
import cb.alphabeta.Move;
import mancala.evaluation.MancalaEvaluation;
import mancala.evaluation.SimpleDiffEvaluation;

public class MancalaMain {

	public static void main(String[] args) throws IOException {

		MancalaGameState state = new MancalaGameState(true, false, new short[] {0, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4});

		boolean humanPlayFirst = true;

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		MancalaEvaluation eval = new SimpleDiffEvaluation();

		while(true) {


			state.print();

			if (state.getMoves().isEmpty()) {
				break;
			}
			AlphaBeta ab = new AlphaBeta(new MancalaGame(state, eval));
			MancalaMove bestMove = MancalaMove.SKIP_TURN_MOVE;

			if (!state.isSkipTurn()) {
				int depth = 15;
				if (humanPlayFirst == state.currentlyMaximizing()) {
					List<Move> moves = state.getMoves();

					while (true) {
						System.out.print("Available moves are " );
						for (Move m : moves) {
							System.out.print( ((MancalaMove)m).getPosition() + " ");
						}


						System.out.print(". Please enter your move: ");

						String moveInString = br.readLine();
						int movePosition = Integer.parseInt(moveInString);

						if (state.isValidMove(movePosition)) {

							bestMove = MancalaMove.getMove(movePosition);
							break;
						} else {
							System.out.println("Invalid move. Please try again.");
						}
					}
				} else {
					bestMove = (MancalaMove) ab.analyzeDepth(depth);
				}
			}


			System.out.println(state.getCurrentPlayer() +  " making move for: " + bestMove.getPosition());
			state = state.makeMove(bestMove);
		}

	}

}
