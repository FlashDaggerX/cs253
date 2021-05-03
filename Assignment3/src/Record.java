import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * This program logs the given set of data to a number of files in a folder.
 *
 * @author Kyle Guarco
 */
public class Record implements Closeable, Flushable {
	private File dir;
	private HashMap<String, PrintWriter> handles;

	/**
	 * Creates a new record pointing to specified path. Records are WRITE-ONLY!
	 *
	 * @param filepath   The filepath for this record.
	 * @param recordname The name for the record.
	 * @throws IOException If the record couldn't be created.
	 */
	public Record(String filepath) throws IOException {
		this.dir = new File(filepath);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		this.handles = new HashMap<>();
	}

	@Override
	public void close() throws IOException {
		for (PrintWriter writer : handles.values())
			writer.close();
	}

	@Override
	public void flush() throws IOException {
		for (PrintWriter writer : handles.values())
			writer.flush();
	}

	/**
	 * Creates a new part of the record
	 *
	 * @param name The name of this record handle.
	 * @return Was the record created successfully?
	 * @throws IOException
	 */
	public boolean createNewHandle(String name) throws IOException {
		if (handles.containsKey(name))
			return false;

		File file = new File(dir, name);

		if (!file.exists())
			file.createNewFile();

		PrintWriter writer = new PrintWriter(file);

		handles.put(name, writer);

		return true;
	}

	/**
	 * Writes to the specified record handle. It must exist.
	 * See {@code Record.createNewHandle}.
	 *
	 * @param name The name of the record handle to write to.
	 * @param objects The data to write to the handle (specified by {@code Object.toString()})
	 */
	public void write(String name, Object... objects) {
		PrintWriter writer = handles.get(name);

		for (Object obj : objects)
			writer.println(obj.toString());
	}
}
