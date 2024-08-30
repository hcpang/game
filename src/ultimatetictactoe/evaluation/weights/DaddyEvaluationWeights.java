package ultimatetictactoe.evaluation.weights;

public class DaddyEvaluationWeights extends GenericEvaluationWeights {

	
	public int getDefenseWeight() {
		return 100;
	}

	public int getTwoInARowAdvantageWeight() {
		return 1;
	}


	public int getCenterBoardWeight() {
		return 10;
	}

	public int getTwoInARowPiecesWeight() {
		return 0;
	}

	public int getBoardsCapturedWeight() {
		return 0;
	}

	public int getAdvantageBoardCardinalityWeight() {
		return 10;
	}

	
}
