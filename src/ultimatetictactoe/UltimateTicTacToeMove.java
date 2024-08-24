package ultimatetictactoe;

import java.util.Objects;

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
	
	public String toString() {
		return "(" + boardIndex + "," + positionOnBoard + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardIndex, positionOnBoard);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UltimateTicTacToeMove other = (UltimateTicTacToeMove) obj;
		return boardIndex == other.boardIndex && positionOnBoard == other.positionOnBoard;
	}
	
	
	
}
