import java.util.HashMap;
import java.util.Iterator;

/**
 * Describes a position on a graph. Can keep track of its position in any
 * positional linked list (see {@code PossitionalList<E>}).
 *
 * @author Kyle Guarco
 */
public class ListPosition<E> extends Positional<E> implements Iterable<PositionalList<E>.Node>{
	private HashMap<PositionalList<E>, PositionalList<E>.Node> connections;

	public ListPosition(E element) {
		super(element);
		this.connections = new HashMap<>();
	}

	public ListPosition() {
		this(null);
	}

	/**
	 * Iterates over all the connected nodes for this position.
	 * @return The nodes this positional is connected to.
	 */
	@Override
	public Iterator<PositionalList<E>.Node> iterator() {
		return connections.values().iterator();
	}

	/**
	 * @param list
	 * @return The connection this positional has to the {@code list}
	 */
	public PositionalList<E>.Node get(PositionalList<E> list) {
		return connections.get(list);
	}

	/**
	 * Connects this positional to a positional linked list.
	 * @param list
	 * @param node The node to relate the positional to.
	 */
	public void connect(PositionalList<E> list, PositionalList<E>.Node node) {
		connections.put(list, node);
	}

	/**
	 * Disconnects this positional from a postional linked list.
	 * @param list
	 */
	public void disconnect(PositionalList<E> list) {
		connections.remove(list);
	}
}
