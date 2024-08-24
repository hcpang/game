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

class TestBitSetUtils {

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
		BitSet myBoard = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {true, false, true, false, true, false, false, true, false});
		BitSet opponentBoard = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {false, false, false, false, false, false, false, false, false});

		Assert.assertEquals(3, BitSetUtils.getNumPotentialCaptureMoves(myBoard, opponentBoard));
		
		opponentBoard = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {false, true, false, true, false, false, false, false, false});
		Assert.assertEquals(2, BitSetUtils.getNumPotentialCaptureMoves(myBoard, opponentBoard));
		opponentBoard = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {false, true, false, true, false, false, true, false, false});
		Assert.assertEquals(1, BitSetUtils.getNumPotentialCaptureMoves(myBoard, opponentBoard));
		opponentBoard = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {false, true, false, true, false, false, true, false, true});
		Assert.assertEquals(0, BitSetUtils.getNumPotentialCaptureMoves(myBoard, opponentBoard));
		

	}

}
