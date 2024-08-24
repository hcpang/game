package ultimatetictactoe.evaluation;

import java.util.BitSet;

import ultimatetictactoe.UltimateTicTacToeGameState;

public class BitSetUtils {

	public static BitSet getPotentialCaptureMoves(BitSet myBoard, BitSet opponentBoard) {
		
		BitSet result = new BitSet(9);
		BitSet flipBoard = myBoard.get(0, 9);
		
		flipBoard.flip(0, 9);
		for (BitSet winConfig : UltimateTicTacToeGameState.WINNING_CONFIGURATIONS) {
			BitSet clone = flipBoard.get(0, 9);
			clone.and(winConfig);
			
			if (clone.cardinality() == 1) {
				result.or(clone);
			}
		}
		
		// now remove all positions occupied by opponent
		result.andNot(opponentBoard);
		return result;
	}
	
}
