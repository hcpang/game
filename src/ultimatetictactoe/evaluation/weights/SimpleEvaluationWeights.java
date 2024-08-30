package ultimatetictactoe.evaluation.weights;

public class SimpleEvaluationWeights extends GenericEvaluationWeights {

	
	public int getDefenseWeight() {
		return 100;
	}

	public int getTwoInARowAdvantageWeight() {
		return 0;
	}


	public int getCenterBoardWeight() {
		return 0;
	}

	public int getTwoInARowPiecesWeight() {
		return 0;
	}

	public int getBoardsCapturedWeight() {
		return 1;
	}

	public int getAdvantageBoardCardinalityWeight() {
		return 0;
	}

	
}
