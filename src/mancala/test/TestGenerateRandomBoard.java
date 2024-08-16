package mancala.test;


import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mancala.MancalaGameState;
import mancala.evaluation.EvansEvaluation;
import mancala.evaluation.GenerateRandomBoard;

class TestGenerateRandomBoard {


	
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
		GenerateRandomBoard g = new GenerateRandomBoard(10);
		for (int i = 0; i < 10; i++) {
			MancalaGameState state = new MancalaGameState(true, true, g.generateRandomBoard());
			state.print();
		}
	}

}
