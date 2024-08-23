package mancala.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mancala.MancalaGameState;
import mancala.MancalaMove;

class TestSkipTurn {

	private MancalaGameState gameState;
	private MancalaGameState gameStateMinimizer;
	@BeforeEach
	void setUp() throws Exception {
		gameState = new MancalaGameState();
		
		gameStateMinimizer = new MancalaGameState(false, false, new short[] {0, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4});
	}

	@AfterEach
	void tearDown() throws Exception {
	
	}
	
	@Test
	void test() {
		MancalaGameState newState = (MancalaGameState) gameState.makeMove(MancalaMove.ALL_MOVES[3]);
		newState.print();
		newState = (MancalaGameState) gameStateMinimizer.makeMove(MancalaMove.ALL_MOVES[10]);
		newState.print();
	}

}
