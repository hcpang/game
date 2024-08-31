package ultimatetictactoe.evaluation.weights;

public class GenericEvaluationWeights {
	private final int[] weights;

	private static final int[][] ranges = {{200, 0}, 
	          {1000, 0},
	          {40 ,0},
	          {200, 0},
	          {1000, 10},
	          {1000, 10},
	          {300, 0}};
	
	public static int getMinRange(int weightIndex) {
		return ranges[weightIndex][1];
	}
	
	public static int getMaxRange(int weightIndex) {
		return ranges[weightIndex][0];
	}
	public GenericEvaluationWeights () {
		this(0, 10, 0, 1, 100, 100, 10);
	}
	
	public GenericEvaluationWeights(int[] weights) {
		this(weights[0], weights[1], weights[2], weights[3], weights[4], weights[5], weights[6]);
	}
	
	public GenericEvaluationWeights(int boardsCapturedWeight, int advantageBoardCardinalityWeight,
			int twoInARowPiecesWeight, int twoInARowAdvantageWeight, int defenseWeight, 
			int offenseWeight,
			int centerBoardWeight) {
		super();
		this.weights = new int[7];
		
		
		this.weights[0] = boardsCapturedWeight;
		this.weights[1] = advantageBoardCardinalityWeight;
		this.weights[2] = twoInARowPiecesWeight;
		this.weights[3] = twoInARowAdvantageWeight;
		this.weights[4] = defenseWeight;
		this.weights[5] = offenseWeight;
		this.weights[6] = centerBoardWeight;
	}


	public int getDefenseWeight() {
		return weights[4];
	}
	
	public int getOffenseWeight() {
		return weights[5];
	}

	public int getTwoInARowAdvantageWeight() {
		return weights[3];
	}


	public int getCenterBoardWeight() {
		return weights[6];
	}

	public int getTwoInARowPiecesWeight() {
		return weights[2];
	}

	public int getBoardsCapturedWeight() {
		return weights[0];
	}

	public int getAdvantageBoardCardinalityWeight() {
		return weights[1];
	}
	
	public int[] getWeightsAsArray() {
		return weights;
	}


}
