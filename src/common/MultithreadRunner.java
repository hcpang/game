package common;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadRunner {

	private final int numThreads;
	private final List<Runnable> runnables;

	public MultithreadRunner(int numThreads, List<Runnable> runnables) {
		super();
		this.numThreads = numThreads;
		this.runnables = runnables;
	}

	public void runMultiThread() {

		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		for (Runnable runnable : runnables) {
			executor.execute(runnable);
		}

		// shutdown(): Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
		// Invocation has no additional effect if already shut down.
		// This method does not wait for previously submitted tasks to complete execution. Use awaitTermination to do that.
		executor.shutdown();

		// Wait until all threads are finish
		// Returns true if all tasks have completed following shut down.
		// Note that isTerminated is never true unless either shutdown or shutdownNow was called first.
		while (!executor.isTerminated()) {
			// empty body
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("\nFinished all threads");

	}


}
