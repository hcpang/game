package ultimatetictactoe;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import common.GameState;

public class UltimateTicTacToeGameState implements GameState<UltimateTicTacToeMove> {

	public BitSet[] getCirclePieces() {
		return circlePieces;
	}


	public BitSet[] getCrossPieces() {
		return crossPieces;
	}


	public BitSet getBoardsCapturedByCircle() {
		return boardsCapturedByCircle;
	}


	public BitSet getBoardsCapturedByCross() {
		return boardsCapturedByCross;
	}



	private final BitSet[] circlePieces;
	private final BitSet[] crossPieces;
	private final int boardIndexForCurrentMove;
	private final boolean isCirclesTurn;

	public int getBoardIndexForCurrentMove() {
		return boardIndexForCurrentMove;
	}



	private final BitSet boardsCapturedByCircle;
	private final BitSet boardsCapturedByCross;
	
	private List<UltimateTicTacToeMove> cachedMoves;

	public static BitSet[] WINNING_CONFIGURATIONS = new BitSet[] {
			initializeBitSet(new boolean[] {true, true, true, false, false, false, false, false, false}),
			initializeBitSet(new boolean[] {false, false, false, true, true, true, false, false, false}),
			initializeBitSet(new boolean[] {false, false, false, false, false, false, true, true, true}),
			initializeBitSet(new boolean[] {true, false, false, true, false, false, true, false, false}),
			initializeBitSet(new boolean[] {false, true, false, false, true, false, false, true, false}),
			initializeBitSet(new boolean[] {false, false, true, false, false, true, false, false, true}),
			initializeBitSet(new boolean[] {true, false, false, false, true, false, false, false, true}),
			initializeBitSet(new boolean[] {false, false, true, false, true, false, true, false, false})
	};

	public static BitSet initializeBitSet(boolean[] values) {
		BitSet result = new BitSet(values.length);

		for (int i = 0; i < values.length; i++) {
			if (values[i])
				result.set(i);
		}
		return result;
	}


	public static boolean hasWinner(BitSet pieces) {
		for (BitSet winningConfig : WINNING_CONFIGURATIONS) {
			BitSet clone = pieces.get(0, pieces.length());
			clone.and(winningConfig);
			if (clone.equals(winningConfig)) {
				return true;
			}
		}
		return false;
	}

	public UltimateTicTacToeGameState() {
		isCirclesTurn = true;
		boardIndexForCurrentMove = 4;
		circlePieces = new BitSet[9];
		crossPieces = new BitSet[9];

		for (int i = 0; i < 9; i++) {
			circlePieces[i] = new BitSet(9);
			crossPieces[i] = new BitSet(9);
		}
		boardsCapturedByCircle = new BitSet(9);
		boardsCapturedByCross = new BitSet(9);
	}

	public UltimateTicTacToeGameState(int boardIndexForCurrentMove, 
			BitSet[] circlePieces,
			BitSet[] crossPieces, 
			boolean isCirclesTurn, 
			BitSet boardsCapturedByCircle,
			BitSet boardsCapturedByCross) {
		this.isCirclesTurn = isCirclesTurn;
		this.boardIndexForCurrentMove = boardIndexForCurrentMove;
		this.circlePieces = circlePieces;
		this.crossPieces = crossPieces;
		this.boardsCapturedByCircle = boardsCapturedByCircle;
		this.boardsCapturedByCross = boardsCapturedByCross;
	}

	public boolean hasCircleWon() {
		return hasWinner(boardsCapturedByCircle);

	}

	public boolean hasCrossWon() {
		return hasWinner(boardsCapturedByCross);
	}

	@Override
	public List<UltimateTicTacToeMove> getMoves() {
		if (cachedMoves == null) {
			cachedMoves = new ArrayList<>(9);
			if (!hasCircleWon() && !hasCrossWon()) {
				for (int i = 0; i<=8; i++) {
					if (!circlePieces[boardIndexForCurrentMove].get(i) && 
							!crossPieces[boardIndexForCurrentMove].get(i)) {
						cachedMoves.add(new UltimateTicTacToeMove(boardIndexForCurrentMove, i));
					}
				}
			}
		}
		return cachedMoves;

	}

	@Override
	public GameState<UltimateTicTacToeMove> makeMove(UltimateTicTacToeMove move) {
		// TODO Auto-generated method stub
		BitSet[] circlePieces = new BitSet[9];
		BitSet[] crossPieces = new BitSet[9];
		BitSet boardsCapturedByCircle = this.boardsCapturedByCircle.get(0, 9);
		BitSet boardsCapturedByCross = this.boardsCapturedByCross.get(0, 9);
		for (int i = 0; i < 9; i++) {
			circlePieces[i] = this.circlePieces[i].get(0, 9);
			crossPieces[i] = this.crossPieces[i].get(0, 9);
		}

		int boardIndexToMove = move.getBoardIndex();
		int posToMove = move.getPositionOnBoard();

		BitSet boardToMove = isCirclesTurn ? circlePieces[boardIndexToMove] :
			crossPieces[boardIndexToMove];

		boardToMove.set(posToMove);
		if (!boardsCapturedByCross.get(boardIndexToMove) && !boardsCapturedByCircle.get(boardIndexToMove) && hasWinner(boardToMove)) {
			if (isCirclesTurn) {
				boardsCapturedByCircle.set(boardIndexToMove);
			} else {
				boardsCapturedByCross.set(boardIndexToMove);
			}
		}
		return new UltimateTicTacToeGameState(
				posToMove,
				circlePieces,
				crossPieces, 
				!isCirclesTurn, 
				boardsCapturedByCircle,
				boardsCapturedByCross
				);
	}

	@Override
	public boolean currentlyMaximizing() {
		return isCirclesTurn;
	}
	
	public boolean isCirclesTurn() {
		return isCirclesTurn;
	}
	
	public String getCurrentPlayer() {
		return isCirclesTurn ? "o" : "x";
	}
	
	public boolean isValidMove(UltimateTicTacToeMove move) {
		return getMoves().contains(move);
	}
	
	private static char printPiece(BitSet circlePieces, BitSet crossPieces, int pos) {
		if (circlePieces.get(pos)) {
			return 'o';
		} else if (crossPieces.get(pos)) {
			return 'x';
		} else {
			return ' ';
		}
	}
	
	private void printOneRow(StringBuilder sb, int board_switch, int posOffset) {
		for(int j = 0; j < 9; j++) {
			if(j % 3 == 0) {
				board_switch++;
			}
			char piece = printPiece(circlePieces[board_switch], crossPieces[board_switch], j % 3 + posOffset);
			//sb.append(" " + piece + " |")
			if(j == 8) {
				sb.append(" " + piece);
			}else {
				sb.append(" " + piece + " |");
			}
		}
	}
	

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Current player: " + (this.isCirclesTurn ? "o" : "x") + "\n");
		sb.append("Current board: " + this.boardIndexForCurrentMove + "\n\n");
		
		for(int i = 0; i < 3; i++) {
			int boardSwitch = 3 * i - 1;
			printOneRow(sb, boardSwitch, 0);
			sb.append("\n-----------|-----------|-----------\n");
			printOneRow(sb, boardSwitch, 3);
			sb.append("\n-----------|-----------|-----------\n");
			printOneRow(sb, boardSwitch, 6);
			if(i == 2) {
				sb.append('\n');
			}else {
				sb.append("\n===========+===========+===========\n");
			}
				
		}
		
		sb.append("\nBoards captured: \n\n");
		
		for(int i = 0; i < 9; i++) {
				char piece = printPiece(boardsCapturedByCircle, boardsCapturedByCross, i);

				if(i % 3 == 2) {
					sb.append(" " + piece);
					if (i != 8)
						sb.append("\n-----------\n");
				} else {
					sb.append(" " + piece + " |");
				}
			
		}
		sb.append("\n");
		

		return sb.toString();
	}

}
