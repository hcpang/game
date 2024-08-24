package ultimatetictactoe.test;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ultimatetictactoe.UltimateTicTacToeGameState;
import ultimatetictactoe.UltimateTicTacToeMove;

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

		Assert.assertEquals(true, UltimateTicTacToeGameState.hasWinner(UltimateTicTacToeGameState.initializeBitSet(new boolean[] {true, true, true, false, true, false, false, false, false})));
		Assert.assertEquals(false, UltimateTicTacToeGameState.hasWinner(UltimateTicTacToeGameState.initializeBitSet(new boolean[] {true, false, true, false, true, false, false, false, false})));
		Assert.assertEquals(true, UltimateTicTacToeGameState.hasWinner(UltimateTicTacToeGameState.initializeBitSet(new boolean[] {true, false, true, false, true, false, true, false, false})));


		System.out.println(state);

		state = (UltimateTicTacToeGameState) state.makeMove(new UltimateTicTacToeMove(4,4));

		System.out.println(state);
		state = (UltimateTicTacToeGameState) state.makeMove(new UltimateTicTacToeMove(4,8));
		System.out.println(state);
	}

}
