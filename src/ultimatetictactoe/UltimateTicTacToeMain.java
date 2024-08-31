package ultimatetictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import cb.alphabeta.AlphaBeta;
import common.Evaluation;
import common.Game;

public class UltimateTicTacToeMain {

	private final BufferedReader br;
	
	public enum MatchResult {CIRCLE_WIN, CROSS_WIN, DRAW};

	public UltimateTicTacToeMain() {
		InputStreamReader isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}

	public MatchResult playGame() {
		return playGame(true);
	}
	
	public MatchResult playGame(boolean printBoards) {
		return playGame(new UltimateTicTacToeGameState(), printBoards);
	}

	public MatchResult playGame(UltimateTicTacToeGameState state, boolean printBoards) {

		while(true) {

			if (printBoards) {
				System.out.println(state);
			}

			if (state.getMoves().isEmpty()) {
				break;
			}

			UltimateTicTacToeMove bestMove;

			if (state.isCirclesTurn()) {
				bestMove = getMoveForCircle(state);
			} else {
				bestMove = getMoveForCross(state);
			}
			if (printBoards) {
				System.out.println(state.getCurrentPlayer() +  " making move for: " + bestMove);
				System.out.println();
			}
			state = (UltimateTicTacToeGameState) state.makeMove(bestMove);
		}
		MatchResult result;
		if (state.hasCircleWon()) {
			if (printBoards)
				System.out.println("Winner is o!!");
			result = MatchResult.CIRCLE_WIN;
		} else if (state.hasCrossWon()) {
			if (printBoards)
				System.out.println("Winner is x!!");
			result = MatchResult.CROSS_WIN;
		} else {
			if (printBoards)
				System.out.println("It's a draw!");
			result = MatchResult.DRAW;
		}
		
		return result;


	}

	protected UltimateTicTacToeMove getMoveForCircle(UltimateTicTacToeGameState state) {
		try {
			return getHumanMove(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected UltimateTicTacToeMove getMoveForCross(UltimateTicTacToeGameState state) {
		try {
			return getHumanMove(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	protected UltimateTicTacToeMove getHumanMove(UltimateTicTacToeGameState state)
			throws IOException {
		List<UltimateTicTacToeMove> moves = state.getMoves();

		while (true) {
			System.out.print("Available moves are " );
			for (UltimateTicTacToeMove m : moves) {
				System.out.print(m + " ");
			}


			System.out.print(". Please enter your move: ");

			String moveInString = br.readLine();
			int movePosition = Integer.parseInt(moveInString);

			UltimateTicTacToeMove move = new UltimateTicTacToeMove(state.getBoardIndexForCurrentMove(),
					movePosition);

			if (state.isValidMove(move)) {
				return move;
			} else {
				System.out.println("Invalid move. Please try again.");
			}
		}
	}


	protected UltimateTicTacToeMove getMachineMove(UltimateTicTacToeGameState state,
			Evaluation<UltimateTicTacToeGameState> eval, int depth) {
		AlphaBeta ab = new AlphaBeta(new Game<>(state, eval));
		return (UltimateTicTacToeMove) ab.analyzeDepth(depth);
	}

	public static void main(String[] args) {


		new UltimateTicTacToeMain().playGame();
	}

}
