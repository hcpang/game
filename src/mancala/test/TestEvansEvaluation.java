package mancala.test;


import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mancala.MancalaGameState;
import mancala.evaluation.EvansEvaluation;

class TestEvansEvaluation {

	private MancalaGameState test1;
	private MancalaGameState test2;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		test1 = new MancalaGameState(false, false, new short[] {0, 5, 5, 5, 0, 5, 5, 1, 5, 4, 4, 4, 4, 4});
		test2 = new MancalaGameState(false, false, new short[] {12, 0, 8, 1, 2, 0, 1, 8, 1, 0, 2, 3, 10, 0});
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Assert.assertEquals(76, new EvansEvaluation(3).evaluate(test1));
		Assert.assertEquals(43, new EvansEvaluation(3).evaluate(test2));
	}

}
