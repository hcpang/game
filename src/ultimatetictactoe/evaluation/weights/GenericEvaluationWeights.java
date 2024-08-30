package ultimatetictactoe.evaluation.weights;

public class GenericEvaluationWeights {
	private final int boardsCapturedWeight = 0;
	private final int advantageBoardCardinalityWeight = 10;
	private final int twoInARowPiecesWeight = 0;
	private final int twoInARowAdvantageWeight = 1;
	private final int defenseWeight = 100;

	private final int centerBoardWeight = 10;
	
	public int getDefenseWeight() {
		return defenseWeight;
	}

	public int getTwoInARowAdvantageWeight() {
		return twoInARowAdvantageWeight;
	}


	public int getCenterBoardWeight() {
		return centerBoardWeight;
	}

	public int getTwoInARowPiecesWeight() {
		return twoInARowPiecesWeight;
	}

	public int getBoardsCapturedWeight() {
		return boardsCapturedWeight;
	}

	public int getAdvantageBoardCardinalityWeight() {
		return advantageBoardCardinalityWeight;
	}

	
}
