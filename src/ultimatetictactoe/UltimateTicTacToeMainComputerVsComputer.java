package ultimatetictactoe;

import java.io.IOException;

import common.Evaluation;
import ultimatetictactoe.evaluation.DaddysUltimateTicTacToeEvaluation;
import ultimatetictactoe.evaluation.DaddysUltimateTicTacToeEvaluationV2;
import ultimatetictactoe.evaluation.EvansUltimateTicTacToeEvaluation;
import ultimatetictactoe.evaluation.GenericEvaluation;
import ultimatetictactoe.evaluation.SimpleUltimateTicTacToeEvaluation;
import ultimatetictactoe.evaluation.weights.DaddyEvaluationWeights;
import ultimatetictactoe.evaluation.weights.GenericEvaluationWeights;
import ultimatetictactoe.evaluation.weights.SimpleEvaluationWeights;
import ultimatetictactoe.evaluation.weights.WeightMutationsAndCrossovers;
import ultimatetictactoe.evaluation.AlexEvaluation;
import ultimatetictactoe.evaluation.LastResortEvaluation;

public class UltimateTicTacToeMainComputerVsComputer extends UltimateTicTacToeMain {
	
	private final GenericEvaluationWeights circleWeights;
	private final GenericEvaluationWeights crossWeights;
	private final int depth;

	public UltimateTicTacToeMainComputerVsComputer(GenericEvaluationWeights circleWeights,
			GenericEvaluationWeights crossWeights, int depth) {
		super();
		this.circleWeights = circleWeights;
		this.crossWeights = crossWeights;
		this.depth = depth;
	}

	@Override
	protected UltimateTicTacToeMove getMoveForCircle(UltimateTicTacToeGameState state) {

		return getMachineMove(state,
				new GenericEvaluation(circleWeights)
				
				, depth);
	}

	@Override
	protected UltimateTicTacToeMove getMoveForCross(UltimateTicTacToeGameState state) {

		return getMachineMove(state,
				new GenericEvaluation(crossWeights)
				
				, depth);
	}



	public static void main(String[] args) {


		MatchResult result = new UltimateTicTacToeMainComputerVsComputer(new DaddyEvaluationWeights(), 
				new SimpleEvaluationWeights(), 7).playGame();
		
		System.out.println(result);
		
	}

}
