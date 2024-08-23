package common;

import java.util.List;
import java.util.Stack;

import cb.alphabeta.Move;
import cb.alphabeta.Position;

public class Game<T extends GameState<M>, M extends Move> implements Position {

	private final Stack<T> states;
	private T currentState;
	private final Evaluation<T> eval;

	public Game(T initialState, Evaluation<T> eval) {
		this.states = new Stack<>();
		this.currentState = initialState;
		this.eval = eval;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Move> getMoves() {
		return (List<Move>) currentState.getMoves();
	}

	@Override
	public void doMove(Move move) {
		@SuppressWarnings("unchecked")
		T nextState = (T) currentState.makeMove((M)move);
		states.push(currentState);
		currentState = nextState;
	}

	@Override
	public void undoMove() {
		currentState = states.pop();
	}

	@Override
	public int evaluate() {
		return eval.evaluate(currentState);
	}

	@Override
	public boolean currentlyMaximizing() {
		return currentState.currentlyMaximizing();
	}

}
