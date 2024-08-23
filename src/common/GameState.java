package common;

import java.util.List;

import cb.alphabeta.Move;

public interface GameState {
	
	public List<Move> getMoves();
	public GameState makeMove(Move move);
	
	public boolean currentlyMaximizing();
}
