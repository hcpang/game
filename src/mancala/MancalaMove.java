package mancala;

import cb.alphabeta.Move;

public class MancalaMove implements Move {
	public final static short SKIP_TURN = 0;
	private final short position;
	
	public final static MancalaMove SKIP_TURN_MOVE = new MancalaMove(SKIP_TURN);
	public final static MancalaMove ALL_MOVES[] = {new MancalaMove(0), new MancalaMove(1), new MancalaMove(2),new MancalaMove(3),new MancalaMove(4),new MancalaMove(5),new MancalaMove(6),new MancalaMove(7),
			new MancalaMove(8), new MancalaMove(9),new MancalaMove(10),new MancalaMove(11),new MancalaMove(12),new MancalaMove(13)};
	
	public final static int OPPOSITE_LOOKUP[] = {0, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	
	public static MancalaMove getMove(int position) {
		return ALL_MOVES[position];		
	}
	
	public MancalaMove(int position) {
		
		this.position = (short) position;
	}
	
	public int getPosition() {
		return position;
	}
	
	
}
