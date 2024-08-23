package mancala;

import mancala.evaluation.DaddysEvaluation;
import mancala.evaluation.EvansEvaluation;
import mancala.evaluation.SimpleDiffEvaluation;
import mancala.evaluation.TotalMarblesEvaluation;

public class MancalaMainComputerVsComputer extends MancalaMain {



	@Override
	protected MancalaMove getMoveForTop(MancalaGameState state) {
		return getMachineMove(state, new EvansEvaluation(3), 15);
	}

	@Override
	protected MancalaMove getMoveForBottom(MancalaGameState state) {
		return getMachineMove(state, new DaddysEvaluation(), 15);
	}



	public static void main(String[] args) {


		new MancalaMainComputerVsComputer().playGame();
	}

}
