package ultimatetictactoe;

import java.io.IOException;

import ultimatetictactoe.evaluation.DaddysUltimateTicTacToeEvaluation;
import ultimatetictactoe.evaluation.DaddysUltimateTicTacToeEvaluationV2;
import ultimatetictactoe.evaluation.EvansUltimateTicTacToeEvaluation;
import ultimatetictactoe.evaluation.SimpleUltimateTicTacToeEvaluation;

public class UltimateTicTacToeMainComputerVsComputer extends UltimateTicTacToeMain {


	@Override
	protected UltimateTicTacToeMove getMoveForCircle(UltimateTicTacToeGameState state) {

		return getMachineMove(state,
				new DaddysUltimateTicTacToeEvaluationV2()
				, 10);
	}

	@Override
	protected UltimateTicTacToeMove getMoveForCross(UltimateTicTacToeGameState state) {
		return getMachineMove(state,
				new SimpleUltimateTicTacToeEvaluation()
				, 10);
	}



	public static void main(String[] args) {


		new UltimateTicTacToeMainComputerVsComputer().playGame();
	}

}
