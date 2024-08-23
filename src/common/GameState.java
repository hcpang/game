package common;

import java.util.List;

import cb.alphabeta.Move;

public interface GameState<M extends Move> {
	
	public List<M> getMoves();
	public GameState<M> makeMove(M move);
	
	public boolean currentlyMaximizing();
}
