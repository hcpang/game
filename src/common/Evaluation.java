package common;


public interface Evaluation<T extends GameState<?>> {
	
	public int evaluate(T state);
}
