/**
 * This class provides functions that can record the execution time of a given
 * function.
 *
 * @author Kyle Guarco
 */
public class FunctionTimer {

	/**
	 * Returns the execution time of the function, in nanoseconds.
	 *
	 * @param function
	 * @param args Arguments for the function.
	 * @return
	 */
	public static Long time(VaridicFunction function, Object... args) {
		Long starttime = System.nanoTime();

		function.call(args);

		return System.nanoTime() - starttime;
	}

	/**
	 * Repeats {@code FunctionTimer.time()} according to {@code count}.}
	 *
	 * @param count
	 * @param function
	 * @return
	 */
	public static Long repeatTime(int count, VaridicFunction function, Object... args) {
		Long total = 0L;

		for (int i = 0; i < count + 1; i++)
			total += FunctionTimer.time(function, args);

		return total;
	}

	@FunctionalInterface
	static interface VaridicFunction {
		void call(Object... args);
	}
}
