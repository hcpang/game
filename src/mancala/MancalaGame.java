package mancala;

import java.util.List;
import java.util.Stack;

import cb.alphabeta.Move;
import cb.alphabeta.Position;

public class MancalaGame implements Position {

	private final Stack<MancalaGameState> states;
	private MancalaGameState currentState;
	private final AbstractMancalaEvaluation eval;
	
	public MancalaGame(MancalaGameState initialState, AbstractMancalaEvaluation eval) {
		this.states = new Stack<MancalaGameState>();
		this.currentState = initialState;
		this.eval = eval;
	}
	
	
	@Override
	public List<Move> getMoves() {
		return currentState.getMoves();
	}

	@Override
	public void doMove(Move move) {
		MancalaGameState nextState = currentState.makeMove((MancalaMove)move);
		states.push(currentState);
		currentState = nextState;
	}

	@Override
	public void undoMove() {
		currentState = states.pop();
	}

	@Override
	public int evaluate() {
		return currentState.evaluate(this.eval);
	}

	@Override
	public boolean currentlyMaximizing() {
		return currentState.currentlyMaximizing();
	}

}
