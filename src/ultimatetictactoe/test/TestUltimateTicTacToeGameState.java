package ultimatetictactoe.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mancala.MancalaGameState;
import mancala.MancalaMove;
import ultimatetictactoe.UltimateTicTacToeGameState;

class TestUltimateTicTacToeGameState {

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
		UltimateTicTacToeGameState state = new UltimateTicTacToeGameState();
		
		Assert.assertEquals(true, UltimateTicTacToeGameState.hasWinner(BitSet.valueOf(new byte[] {1, 1, 1, 0, 0, 0, 0, 0, 0})));
		Assert.assertEquals(false, UltimateTicTacToeGameState.hasWinner(BitSet.valueOf(new byte[] {1, 0, 1, 0, 0, 0, 0, 0, 0})));
		
		System.out.println(state.toString());
	}

}
