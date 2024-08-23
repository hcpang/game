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
		
		int numMarblesLeft = 15;
		try {
			FileWriter myWriter = new FileWriter("C:\\Users\\yharm\\git\\game\\output\\filename.txt");
			for (int i = 0; i < numGames; i++) {
				GenerateRandomBoard g = new GenerateRandomBoard(numMarblesLeft);
				short[] board = g.generateRandomBoard();
				boolean bottomTurn = (i % 2 == 0);
				MancalaGameState state = new MancalaGameState(bottomTurn, false, board);
				int topScore = new MancalaMainPlayRandomGames().playGame(state);

				EvansEvaluation eval = new EvansEvaluation(3);
				int numBottomMarbles = eval.calculateNumBottomMarbles(board);
				int numTopMarbles = eval.calculateNumTopMarbles(board);
				int numMarblesDiff = numTopMarbles - numBottomMarbles;
				int scoreDiff = 2 * topScore - numMarblesLeft;
				myWriter.write(numMarblesDiff + "," + bottomTurn + "," +  scoreDiff + "\n");


			}
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
