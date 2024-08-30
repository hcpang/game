package ultimatetictactoe.evaluation;

import java.util.BitSet;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class NumPiecesinLines {

	public static int getNumPiecesinLines(BitSet myBoard, BitSet opponentBoard) {
		
		int total = 0;
//		BitSet result = new BitSet(9);
//		BitSet flipBoard = myBoard.get(0, 9);
		
//		flipBoard.flip(0, 9);
		for (BitSet winConfig : UltimateTicTacToeGameState.WINNING_CONFIGURATIONS) {
			BitSet clone = myBoard.get(0, 9);
			BitSet oppClone = opponentBoard.get(0, 9);
			clone.and(winConfig);
			oppClone.and(winConfig);
			if (clone.cardinality() >= 1 && oppClone.cardinality() == 0) {
				total+=clone.cardinality();
			}
		}
		
		return total;
	}
	
}
