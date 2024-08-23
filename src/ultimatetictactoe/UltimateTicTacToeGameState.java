package ultimatetictactoe;

import java.util.BitSet;
import java.util.List;

import common.GameState;

public class UltimateTicTacToeGameState implements GameState<UltimateTicTacToeMove> {
	
	private final BitSet[] circlePieces;
	private final BitSet[] crossPieces;
	private final int boardIndexForCurrentMove;
	private final boolean isCirclesTurn;
	
	private final BitSet boardsCapturedByCircle;
	private final BitSet boardsCapturedByCross;
	
	private final static BitSet[] WINNING_CONFIGURATIONS = new BitSet[] {
		BitSet.valueOf(new byte[] {1, 1, 1, 0, 0, 0, 0, 0, 0}),	
		BitSet.valueOf(new byte[] {0, 0, 0, 1, 1, 1, 0, 0, 0}),	
		BitSet.valueOf(new byte[] {0, 0, 0, 0, 0, 0, 1, 1, 1}),	
		// TODO - include the 5 other configs
	};
	
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

	@Override
	public List<UltimateTicTacToeMove> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState<UltimateTicTacToeMove> makeMove(UltimateTicTacToeMove move) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean currentlyMaximizing() {
		return isCirclesTurn;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("-----------------------------------------\n" );
		

		return sb.toString();
	}

}
