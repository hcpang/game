package ultimatetictactoe;

import java.io.IOException;

import ultimatetictactoe.evaluation.DaddysUltimateTicTacToeEvaluationV2;
import ultimatetictactoe.evaluation.SimpleUltimateTicTacToeEvaluation;

public class UltimateTicTacToeMainHumanVsComputer extends UltimateTicTacToeMain {


	@Override
	protected UltimateTicTacToeMove getMoveForCircle(UltimateTicTacToeGameState state) {
		try {
			return getHumanMove(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected UltimateTicTacToeMove getMoveForCross(UltimateTicTacToeGameState state) {
		return getMachineMove(state,
				new DaddysUltimateTicTacToeEvaluationV2(), 10);
	}



	public static void main(String[] args) {


		new UltimateTicTacToeMainHumanVsComputer().playGame();
	}

}
