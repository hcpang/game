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
import ultimatetictactoe.evaluation.NumPiecesinLines;

class TestNumPiecesinLines {

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

		Assert.assertEquals(12, NumPiecesinLines.getNumPiecesinLines(myBoard, opponentBoard));
		
		opponentBoard = UltimateTicTacToeGameState.initializeBitSet(new boolean[] {false, true, false, false, false, false, false, false, false});
		Assert.assertEquals(8, NumPiecesinLines.getNumPiecesinLines(myBoard, opponentBoard));

	}

}
