package mancala.evaluation;

import mancala.MancalaGameState;
import java.io.*;
import java.util.*;
import mancala.MancalaMove;

public class GenerateRandomBoard {
	
	private int numPiecesOutsideMancala;
	
	

	public GenerateRandomBoard(int numPiecesOutsideMancala) {
		super();
		this.numPiecesOutsideMancala = numPiecesOutsideMancala;
	}


	public short[] generateRandomBoard() {
		short[] result = new short[14];
		Random rand = new Random();
		int max = 12, min = 1; 
		short num; 
		for (int i = 0; i < numPiecesOutsideMancala; i++) {
			num = (short) (rand.nextInt(max - min + 1) + min);
			if (num >=7) {
				num +=1;
			}
			result[num] += 1;
		}
		return result;
	}

}
