package mancala.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mancala.MancalaGameState;
import mancala.MancalaMove;

class TestEatOpposite {

	private MancalaGameState gameState;
	private MancalaGameState gameStateMinimizer;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gameState = new MancalaGameState(true, false, new short[] {0, 2, 4, 0, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4});
		
		gameStateMinimizer = new MancalaGameState(false, false, new short[] {0, 4, 4, 4, 4, 4, 4, 0, 2, 4, 0, 4, 4, 4});
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		gameState.makeMove(MancalaMove.ALL_MOVES[1]).print();
		gameStateMinimizer.makeMove(MancalaMove.ALL_MOVES[8]).print();
	}

}
