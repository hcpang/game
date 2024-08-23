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

	private short[] swapTopAndBottom(short[] board) {
		short[] result = new short[14];
		
		for (int i = 0; i < 14; i++ ) {
			result[i] = board[(i + 7) % 14];
		}
		
		return result;
		
	}
	
	@Test
	void test() {
		//Assert.assertEquals(76, new EvansEvaluation(3).evaluate(test1));
		//Assert.assertEquals(43, new EvansEvaluation(3).evaluate(test2));
		
//		Assert.assertEquals(5, new EvansEvaluation(3).calculateNumVulnurableMarbles(new short[] {0, 4, 4, 4, 4, 0, 4, 0, 4, 4, 4, 4, 4, 4}));
//		
//		Assert.assertEquals(7, new EvansEvaluation(3).calculateNumVulnurableMarbles(new short[] {0, 4, 4, 3, 4, 4, 0, 0, 6, 4, 4, 4, 4, 4}));
//		Assert.assertEquals(9, new EvansEvaluation(3).calculateNumVulnurableMarbles(new short[] {0, 4, 4, 3, 4, 0, 0, 0, 6, 8, 4, 4, 4, 4}));
		testNumCapturable(2, new short[] {0, 17, 4, 3, 4, 0, 0, 0, 13, 0, 0, 0, 0, 0});
		testNumCapturable(18, new short[] {0, 17, 4, 3, 4, 0, 0, 0, 5, 0, 0, 0, 0, 0});
		testNumCapturable(18, new short[] {0, 17, 4, 3, 4, 0, 0, 0, 5, 3, 0, 0, 0, 0});
		testNumCapturable(18, new short[] {0, 4, 17, 3, 4, 0, 0, 0, 5, 3, 0, 0, 0, 0});
		testNumCapturable(5, new short[] {0, 4, 17, 3, 4, 0, 0, 0, 5, 16, 0, 0, 0, 0});
		testNumCapturable(12, new short[] {0, 4, 17, 3, 4, 0, 10, 0, 0, 12, 0, 0, 0, 0});
		testNumCapturable(18, new short[] {0, 4, 17, 3, 4, 0, 10, 0, 0, 12, 0, 1, 0, 0});
		testNumCapturable(8, new short[] {0, 4, 17, 3, 4, 6, 10, 0, 0, 13, 0, 0, 0, 0});
		testNumCapturable(8, new short[] {0, 4, 17, 3, 4, 6, 10, 0, 0, 0, 12, 0, 0, 0});
		testNumCapturable(0, new short[] {0, 4, 17, 3, 4, 6, 10, 0, 0, 1, 12, 0, 0, 0});
		testNumCapturable(0, new short[] {0, 4, 17, 3, 4, 6, 10, 0, 0, 0, 14, 0, 0, 0});

	}
	
	private void testNumCapturable(int expectedForTop, short[] board) {
		Assert.assertEquals(expectedForTop, new EvansEvaluation(3).calculateNumCapturableByTop(board));
		Assert.assertEquals(expectedForTop, new EvansEvaluation(3).calculateNumCapturableByBottom(swapTopAndBottom(board)));

		
	}

}
