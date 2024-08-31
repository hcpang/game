package ultimatetictactoe.evaluation.weights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import common.MultithreadRunner;
import ultimatetictactoe.UltimateTicTacToeMain.MatchResult;
import ultimatetictactoe.UltimateTicTacToeMainComputerVsComputer;

public class WeightMutationsAndCrossovers {

	public static int NUM_WEIGHTS = 7;
	public static int NUM_CONTESTANTS = 50;
	public static int NUM_THREADS = 20;
	public static int NUM_GENERATIONS = 30;
	public static int TOP_PERCENTILE = 10;
	public static int MUTATE_PERCENTILE = 20;

	private class TournamentRunnable implements Runnable {

		private final PlayerWeightsandScores grandmasters[];
		private PlayerWeightsandScores result;

		public TournamentRunnable(
				PlayerWeightsandScores grandmasters[],
				PlayerWeightsandScores contestant) {
			this.grandmasters = grandmasters;
			this.result = contestant;
		}


		@Override
		public void run() {
			result = playAgainstGrandmasters(grandmasters, result);

		}



		public PlayerWeightsandScores getResult() {
			return result;
		}


	}


	public static int crossover(int a, int b) {
		return (a+b)/2;
	}

	public static int mutate(int a, int minRange, int maxRange) {
		Random rand = new Random();
		int max = a + Math.max(a/10, 2);
		int min = a - Math.max(a/10, 2);

		max = Math.min(max, maxRange);
		min = Math.max(min, minRange);

		int b = rand.nextInt(max - min + 1) + min;
		return b;
	}

	public static int[] mutateWeights(int[] originalWeights) {
		int[] mutatedWeights = new int[NUM_WEIGHTS];
		for (int i = 0; i < NUM_WEIGHTS; i++) {
			mutatedWeights[i] = mutate(originalWeights[i], GenericEvaluationWeights.getMinRange(i),
					GenericEvaluationWeights.getMaxRange(i));
		}
		return mutatedWeights;
	}

	public static int[] crossoverWeights(int[] daddyWeights, int[] momWeights) {
		int[] crossoveredWeights = new int[NUM_WEIGHTS];
		for (int i = 0; i < NUM_WEIGHTS; i++) {
			crossoveredWeights[i] = crossover(daddyWeights[i], momWeights[i]);
		}
		return crossoveredWeights;
	}

	public PlayerWeightsandScores playAgainstGrandmasters(PlayerWeightsandScores[] grandmasters,
			PlayerWeightsandScores contestant) {

		int score = 0;

		for(PlayerWeightsandScores p : grandmasters) {
			MatchResult result = new UltimateTicTacToeMainComputerVsComputer(new GenericEvaluationWeights(p.getWeights()),
					new GenericEvaluationWeights(contestant.getWeights()), 7).playGame(false);

			if (result.equals(MatchResult.CROSS_WIN)) {
				score++;
			} else if (result.equals(MatchResult.CIRCLE_WIN)) {
				score--;
			}
			MatchResult result1 = new UltimateTicTacToeMainComputerVsComputer(new GenericEvaluationWeights(contestant.getWeights()),
					new GenericEvaluationWeights(p.getWeights()), 7).playGame(false);

			if (result1.equals(MatchResult.CROSS_WIN)) {
				score--;
			} else if (result1.equals(MatchResult.CIRCLE_WIN)) {
				score++;
			}

		}
		return new PlayerWeightsandScores(contestant.getWeights(), score);
	}

	public PlayerWeightsandScores[] breed(PlayerWeightsandScores[] sortedResults) {
		int numContestants = sortedResults.length;
		int topNum = (numContestants * TOP_PERCENTILE) / 100;
		int mutateNum = (numContestants * MUTATE_PERCENTILE) / 100;
		PlayerWeightsandScores[] newWeights = new PlayerWeightsandScores[numContestants];
		for (int i = 0; i < topNum; i++) {
				newWeights[i] = sortedResults[i];
		}
		for (int i = topNum; i < numContestants; i++) {
			Random rand = new Random();
			int j = rand.nextInt(mutateNum + 1);
			newWeights[i] = new PlayerWeightsandScores(mutateWeights(sortedResults[j].getWeights()), 0);
		}
		return newWeights;
	}

	public PlayerWeightsandScores[] runTournamentsAndRank(PlayerWeightsandScores[] grandmasters,
			PlayerWeightsandScores[] contestants) {
		List<Runnable> runnables = new ArrayList<>();

		for (PlayerWeightsandScores contestant : contestants) {
			runnables.add(new TournamentRunnable(grandmasters, contestant));
		}

		MultithreadRunner mr = new MultithreadRunner(NUM_THREADS, runnables);

		mr.runMultiThread();

		List<PlayerWeightsandScores> results = new ArrayList<>();

		// now retrieve results
		for (Runnable r : runnables) {
			TournamentRunnable t = (TournamentRunnable) r;
			results.add(t.getResult());
		}

		results.sort(new Comparator<PlayerWeightsandScores>() {

			@Override
			public int compare(PlayerWeightsandScores o1, PlayerWeightsandScores o2) {
				return o2.getScore() - o1.getScore();
			}
		});
		return results.toArray(new PlayerWeightsandScores[0]);

	}

	public void tournament(){
		PlayerWeightsandScores grandmasters[]= new PlayerWeightsandScores[6];
		PlayerWeightsandScores contestants[] = new PlayerWeightsandScores[NUM_CONTESTANTS];

		int[] stupidDaddy = new DaddyEvaluationWeights().getWeightsAsArray();
		int[] stupidSimple = new SimpleEvaluationWeights().getWeightsAsArray();
		for (int i = 0; i < 2; i++) {
			grandmasters[i] = new PlayerWeightsandScores(mutateWeights(stupidDaddy), 0);
			grandmasters[i+2] = new PlayerWeightsandScores(mutateWeights(stupidSimple), 0);
		}
		grandmasters[4] = new PlayerWeightsandScores(stupidDaddy, 0);
		grandmasters[5] = new PlayerWeightsandScores(stupidSimple, 0);




		for (int i = 0; i < NUM_CONTESTANTS; i++) {
			int[] simpleAlex = new int[NUM_WEIGHTS];
			for (int j = 0; j < NUM_WEIGHTS; j++) {
				Random rand = new Random();
				int max = GenericEvaluationWeights.getMaxRange(j);
				int min = GenericEvaluationWeights.getMinRange(j);
				simpleAlex[j] = rand.nextInt(max - min + 1) + min;
			}
			contestants[i] = new PlayerWeightsandScores(simpleAlex, 0);
		}

		for (int i = 0; i < NUM_GENERATIONS; i++) {

			PlayerWeightsandScores[] results = runTournamentsAndRank(grandmasters, contestants);
			System.out.println("Generation " + i + Arrays.toString(results));

			contestants = breed(results);

			System.out.println("Generation " + i + " after breeding: " + Arrays.toString(contestants));

		}
	}

	public static void main(String[] args) {
		new WeightMutationsAndCrossovers().tournament();
	}
}
