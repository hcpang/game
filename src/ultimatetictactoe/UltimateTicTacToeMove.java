package ultimatetictactoe;

import cb.alphabeta.Move;

public class UltimateTicTacToeMove implements Move {

	private final int boardIndex;
	private final int positionOnBoard;
	
	public UltimateTicTacToeMove(int boardIndex, int positionOnBoard) {
		super();
		this.boardIndex = boardIndex;
		this.positionOnBoard = positionOnBoard;
	}

	public int getBoardIndex() {
		return boardIndex;
	}

	public int getPositionOnBoard() {
		return positionOnBoard;
	}
	
	
	
}
