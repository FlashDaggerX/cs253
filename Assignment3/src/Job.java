import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class schedules any number of tests to be executed, timed and recorded
 * according to a specified function.
 *
 * @author Kyle Guarco
 */
public class Job {
	private static Queue<Job> requestqueue = new ConcurrentLinkedQueue<>();
	private static ThreadGroup testtg = new ThreadGroup("Test ThreadGroup");
	private static Thread processthread;
	private static int threadmax = 1;

	/**
	 * Starts the process that starts jobs in threads of their own.
	 */
	public static void process() {
		Job.processthread = new Thread(() -> {
			while (requestqueue.size() > 0) {
				if (testtg.activeCount() <= Job.threadmax) {
					try {
						Job job = requestqueue.remove();

						new Thread(Job.testtg, () -> job.request.call(job.args)).start();
					} catch (NoSuchElementException e) {
					}
				} else {
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {}
				}
			}
		});

		Job.processthread.start();
	}

	/**
	 * Waits for the processing thread to die (blocking).
	 */
	public static void join() {
		if (Job.processthread != null) {
			try {
				Job.processthread.join();
			} catch (InterruptedException e) {}

			Job.processthread = null;
		}
	}

	/**
	 * Sets the maximum amount of jobs that can run concurrently.
	 *
	 * @param threadcount
	 */
	public static void setMaximumThreadCount(int threadcount) {
		Job.threadmax = threadcount;
	}

	public FunctionTimer.VaridicFunction request;
	public Object[] args;

	public Job(FunctionTimer.VaridicFunction request, Object... args) {
		this.request = request;
		this.args = args;
	}

	/**
	 * Queues this test for execution. The test request and test instructions
	 * must be specified, or this test will not queue.
	 */
	public boolean queue() {
		if (request == null) {
			return false;
		}

		Job.requestqueue.add(this);
		return true;
	}
}
