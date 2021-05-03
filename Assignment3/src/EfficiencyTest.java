import java.io.IOException;
import java.util.ArrayList;

public class EfficiencyTest {
	public static void main(String[] args) {
		final String FILEPATH = "tests/";
		final int NANOS_SECONDS = 1000000000;

		Job.setMaximumThreadCount(6);

		for (int i = 500; i <= 300000; i += 500) {
			Job job = new Job((jobargs) -> {
				int size = (int) jobargs[0];
				System.out.println("Running job with input size " + size);
				try {
					ArrayList<Integer> testlist = new ArrayList<>();

					Record record = new Record(FILEPATH + size);
					record.createNewHandle("size");
					record.createNewHandle("half");
					record.createNewHandle("zero");

					Long time_size = FunctionTimer.repeatTime(size,
							(varargs) -> testlist.add(0));
					testlist.clear();
					Long time_half = FunctionTimer.repeatTime(size,
							(varargs) -> testlist.add(testlist.size()/2, 0));
					testlist.clear();
					Long time_zero = FunctionTimer.repeatTime(size,
							(varargs) -> testlist.add(0, 0));

					record.write("size", time_size.doubleValue() / NANOS_SECONDS);
					record.write("half", time_half.doubleValue() / NANOS_SECONDS);
					record.write("zero", time_zero.doubleValue() / NANOS_SECONDS);

					record.flush();
					record.close();
				} catch (IOException e) {}
			}, i);

			job.queue();
		}

		Job.process();
		// Waits for the other processes to finish.
		Job.join();
	}
}
