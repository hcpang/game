package ultimatetictactoe.evaluation.weights;

import java.util.Arrays;

public class PlayerWeightsandScores {
	private final int[] weights;
	private final int score;
	
	public int[] getWeights() {
		return weights;
	}

	public int getScore() {
		return score;
	}

	public PlayerWeightsandScores(int[] weights, int score) {
		this.weights = weights;
		this.score = score;
	}
	
	public String toString() {
		return Arrays.toString(weights) + "," + score;
	}
	
	
}
