package ultimatetictactoe.test;

import java.util.BitSet;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ultimatetictactoe.UltimateTicTacToeGameState;
import ultimatetictactoe.UltimateTicTacToeMove;
import ultimatetictactoe.evaluation.BitSetUtils;
import ultimatetictactoe.evaluation.EvansUltimateTicTacToeEvaluationV3;

class TestEvansUltimateTicTacToeEvaluationV3 {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		BitSet[] circlePieces = new BitSet[9];
		BitSet[] crossPieces = new BitSet[9];
		
		BitSet pieces = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {true, false, true, false, true, false, false, false, false});
		for (int i = 0; i < 9; i++) {
			circlePieces[i] = pieces;
			crossPieces[i] = new BitSet(9);
		}
		
		BitSet boardsCapturedByCircle = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {false, false, false, false, true, false, true, false, false});
		BitSet boardsCapturedByCross = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {false, false, false, false, false, false, false, false, false});

		
		UltimateTicTacToeGameState g = new UltimateTicTacToeGameState(4, 
				circlePieces,
				crossPieces, 
				true, 
				boardsCapturedByCircle,
				boardsCapturedByCross);
//		EvansUltimateTicTacToeEvaluationV3 ev = new EvansUltimateTicTacToeEvaluationV3();
//		Assert.assertEquals(1, ev.evaluateNonTerminalState(g));
		
		

	}

}
