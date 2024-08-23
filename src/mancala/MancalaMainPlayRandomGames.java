package mancala;

import mancala.evaluation.EvansEvaluation;
import mancala.evaluation.GenerateRandomBoard;
import mancala.evaluation.SimpleDiffEvaluation;
import mancala.evaluation.TotalMarblesEvaluation;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MancalaMainPlayRandomGames extends MancalaMain {



	@Override
	protected MancalaMove getMoveForTop(MancalaGameState state) {
		return getMachineMove(state, new SimpleDiffEvaluation(), 15);
	}

	@Override
	protected MancalaMove getMoveForBottom(MancalaGameState state) {
		return getMachineMove(state, new SimpleDiffEvaluation(), 15);
	}




	public static void main(String[] args) {
		int numGames = 500;

		try {
			FileWriter myWriter = new FileWriter("C:\\Users\\yharm\\git\\game\\output\\filename.txt");
			for (int i = 0; i < numGames; i++) {
				GenerateRandomBoard g = new GenerateRandomBoard(15);
				short[] board = g.generateRandomBoard();
				boolean bottomTurn = (i % 2 == 0);
				MancalaGameState state = new MancalaGameState(bottomTurn, false, board);
				int topScore = new MancalaMainPlayRandomGames().playGame(state);

				EvansEvaluation eval = new EvansEvaluation(3);
				
				int marbleDiff = eval.calculateMarbleDiff(board);
				int numVulnurableMarbles = eval.calculateNumCapturableByBottom(board);
				int numEmptyPocketsDiff = eval.calculateEmptyPocketsTop(board) - eval.calculateEmptyPocketsBottom(board);
				int h4 = eval.calculateH4(bottomTurn, board);
				
				myWriter.write(marbleDiff + "," + h4 + "," + numEmptyPocketsDiff + "," + bottomTurn + "," + topScore + "\n");


			}
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
