package mancala;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import cb.alphabeta.AlphaBeta;
import cb.alphabeta.Move;
import mancala.evaluation.MancalaEvaluation;
import mancala.evaluation.TotalMarblesEvaluation;

public class MancalaMain {

	private final BufferedReader br;

	public MancalaMain() {
		InputStreamReader isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}

	public void playGame() {
		MancalaGameState state = new MancalaGameState(true, false, new short[] {0, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4});

		while(true) {


			state.print();

			if (state.getMoves().isEmpty()) {
				break;
			}

			MancalaMove bestMove = MancalaMove.SKIP_TURN_MOVE;

			if (!state.isSkipTurn()) {

				if (state.currentlyMaximizing()) {
					bestMove = getMoveForBottom(state);
				} else {
					bestMove = getMoveForTop(state);
				}
			}


			System.out.println(state.getCurrentPlayer() +  " making move for: " + bestMove.getPosition());
			state = state.makeMove(bestMove);
		}
		
		if (state.isTerminalState()) {
			
			int topScore = state.getTopScore();
			int bottomScore = state.getBottomScore();
			
			System.out.println("Top score: " + topScore + " Bottom score: " + bottomScore);
			
		}
		
	}

	protected MancalaMove getMoveForTop(MancalaGameState state) {
		return getMachineMove(state, new TotalMarblesEvaluation(), 15);
	}

	protected MancalaMove getMoveForBottom(MancalaGameState state) {
		try {
			return getHumanMove(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	protected MancalaMove getHumanMove(MancalaGameState state)
			throws IOException {
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

				return MancalaMove.getMove(movePosition);
			} else {
				System.out.println("Invalid move. Please try again.");
			}
		}
	}

	protected MancalaMove getMachineMove(MancalaGameState state,
			MancalaEvaluation eval, int depth) {
		AlphaBeta ab = new AlphaBeta(new MancalaGame(state, eval));
		return (MancalaMove) ab.analyzeDepth(depth);
	}


	public static void main(String[] args) {


		new MancalaMain().playGame();
	}

}
