package mancala;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cb.alphabeta.Move;
import mancala.evaluation.MancalaEvaluation;


public class MancalaGameState {
	private final boolean isMaximizerTurn;
	private final boolean isSkipTurn;
	private final short board[]; // array of size 14
	private final List<Move> availableMoves;

	// generate initial board
	public MancalaGameState() {
		this(true, false, initialBoardHelper());

	}

	private static short[] initialBoardHelper() {
		short[] board = new short[14];
		for (int i = 1; i <= 6; i++) {
			board[i] = 4;
		}
		for (int i = 8; i <= 13; i++) {
			board[i] = 4;
		}
		return board;
	}

	public MancalaGameState(boolean isMaximizerTurn, boolean isSkipTurn, short[] board) {

		this.isMaximizerTurn = isMaximizerTurn;
		this.isSkipTurn = isSkipTurn;
		this.board = board;
		this.availableMoves = getMovesHelper();
	}


	public short[] getBoard() {
		return this.board;
	}

	public List<Move> getMoves() {
		return this.availableMoves;	
	}
	
	public boolean isValidMove(int move) {
		for (Move m : availableMoves) {
			if (((MancalaMove)m ).getPosition() == move) {
				return true;
			}
		}
		return false;
	}

	private List<Move> getMovesHelper() {
		if (isSkipTurn) {
			return Collections.singletonList(MancalaMove.SKIP_TURN_MOVE);
		} else {
			List<Move> moves = new ArrayList<Move>(6);
			boolean isOtherPlayerDone = true;

			int myStartIndex = isMaximizerTurn ? 1 : 8;
			int otherStartIndex = isMaximizerTurn ? 8 : 1;

			for (int i = otherStartIndex; i <= otherStartIndex + 5; i++) {
				if (board[i] > 0) {
					isOtherPlayerDone = false;
					break;
				}
			}
			if (!isOtherPlayerDone) {
				for (int i = myStartIndex; i <= myStartIndex + 5; i++) {
					if (board[i] > 0) {
						moves.add(MancalaMove.getMove(i));
					}
				}
			}

			return moves;			
		}
	}

	public boolean isSkipTurn() {
		return this.isSkipTurn;
	}

	public MancalaGameState makeMove(MancalaMove move) {
		if (isSkipTurn) {
			return new MancalaGameState(!isMaximizerTurn, false, board.clone());
		} else {
			short[] newBoard = board.clone();
			int position = move.getPosition();
			int numGems = newBoard[position];
			newBoard[position] = 0;
			while (numGems > 0) {
				position = (position + 1) % 14;

				if ((isMaximizerTurn && position != 0) || (!isMaximizerTurn && position != 7)) {
					// drop a gem
					newBoard[position] += 1;
					numGems -= 1;
				}							
			}

			boolean isAtBasket = (isMaximizerTurn && position == 7) || (!isMaximizerTurn && position == 0);

			if (!isAtBasket && newBoard[position] == 1) { // landed at an empty spot
				if ((isMaximizerTurn && position < 7) || (!isMaximizerTurn && position > 7)) { // on my side
					int numGemsToTake = newBoard[position] + newBoard[MancalaMove.OPPOSITE_LOOKUP[position]];
					newBoard[position] = 0;
					newBoard[MancalaMove.OPPOSITE_LOOKUP[position]] = 0;					
					newBoard[isMaximizerTurn ? 7 : 0] += numGemsToTake;					
				} 
			}

			return new MancalaGameState(!isMaximizerTurn, 
					isAtBasket, // make another move, hence skipping the other player 
					newBoard);

		}
	}

	public int evaluate(MancalaEvaluation eval) {

		if (this.availableMoves.isEmpty()) {

			int score = -board[0];

			for (int i = 1; i <= 7; i++) {
				score += board[i];
			}
			for (int i = 8; i <= 13; i++) {
				score -= board[i];
			}	

			return score;
		} else {



			return eval.evaluate(this);

		}


	}
	
	public boolean isTerminalState() {
		return (board[8] == 0 && 
				board[9] == 0 && board[10] == 0 &&
				board[11] == 0 && board[12] == 0 && board[13] == 0) ||
				(board[1] == 0 && 
				board[2] == 0 && board[3] == 0 &&
				board[4] == 0 && board[5] == 0 && board[6] == 0);
	}
	
	public int getBottomScore() {
		return board[1] + board[2] + board[3] + board[4] + board[5] + board[6] + board[7];
	}
	
	public int getTopScore() {
		return board[8] + board[9] + board[10] + board[11] + board[12] + board[13] + board[0];
	}

	public boolean currentlyMaximizing() {
		return this.isMaximizerTurn;
	}

	public String getCurrentPlayer() {
		return currentlyMaximizing() ? "Bottom" : "Top";
	}

	private String printCell(short cell) {
		if (cell < 10) {
			return "|  " + cell + " ";
		} else {
			return "| " + cell + " ";
		}
	}

	private String boardToString(short[] board) {
		StringBuilder sb = new StringBuilder();

		sb.append("-----------------------------------------\n" );
		sb.append("|    ");
		for (int i = 13; i >= 8; i--) {
			sb.append( printCell(board[i]));
		}
		sb.append("|    |\n");
		sb.append(printCell(board[0]));
		sb.append("|-----------------------------");
		sb.append(printCell(board[7]));
		sb.append("|\n"); 
		sb.append("|    ");
		for (int i = 1; i <= 6; i++) {
			sb.append(printCell(board[i]));
		}
		sb.append("|    |\n");
		sb.append("-----------------------------------------\n");

		return sb.toString();
	}

	public void print() {
		System.out.println("Current turn = " + getCurrentPlayer() + "; Skip Turn = " + isSkipTurn );
		System.out.println(boardToString(board));
	}				

}
