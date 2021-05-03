import java.util.Iterator;

/**
 * Keeps track of a list of positionals in a linked list. It's important to note
 * that positionals are different from a node, in that positionals can be related to
 * a node in a positional linked list. The positional IS NOT a node in a linked list,
 * but an element of it.
 */
public class PositionalList<E> implements Iterable<E> {
	private Node head, tail;
	private int size;

	public PositionalList() {
		this.size = 0;
	}

	/**
	 * @return An iterable of all the nodes in the list.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			private Node current = head;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public E next() {
				E element = current.pos.element;
				current = current.next;
				return element;
			}
		};
	}

	/**
	 * Adds a positional node to the linked list. It's important to note that the
	 * relation of the positional to this list isn't done automagically, and requires
	 * a manual call to {@code Position.connect()}. This ensures that the connections
	 * to each list aren't "opaque" (not hidden from the user).
	 * @param posref A reference to a positional.
	 * @return The node in this list representing {@code posref}
	 */
	public Node add(Positional<E> posref) {
		Node newnode = new Node(posref);
		size++; // Increment here, since both outcomes of the function add a node.

		if (head == null) {
			head = newnode;
			tail = head;
			return newnode;
		}

		tail.next = newnode;
		tail.next.prev = tail;
		tail = tail.next;
		return newnode;
	}

	/**
	 * Removes a node from the linked list. The node is garbage collected when
	 * this function returns.
	 * @param noderef A reference to a node.
	 * @return Did the removal succeed?
	 */
	public boolean remove(Node noderef) {
		if (noderef == null)
			return false;

		Node prevref = noderef.prev;
		boolean hasPrev = (prevref != null);
		Node nextref = noderef.next;
		boolean hasNext = (nextref != null);

		if (hasPrev)
			prevref.next = nextref;

		if (hasNext)
			nextref.prev = prevref;

		if (head == noderef) {
			head = null;
			if (hasNext)
				head = nextref;
			else
				tail = null;
		} else if (tail == noderef && hasPrev) {
			Node current = prevref;
			while (current.next != null)
				current = current.next;

			tail = current;
		}

		size--;
		return true;
	}

	public Node head() {
		return head;
	}

	public Node tail() {
		return tail;
	}

	public E element(Node noderef) {
		return noderef.pos.element;
	}

	public Node next(Node noderef) {
		return noderef.next;
	}

	public Node previous(Node noderef) {
		return noderef.prev;
	}

	public int size() {
		return size;
	}

	/**
	 * A node in a positional linked list. The member {@code pos} holds a
	 * reference to a positional.
	 */
	class Node {
		public Node next, prev;
		public Positional<E> pos;

		public Node(Positional<E> pos) {
			this.pos = pos;
		}

		public Node() {
			this(null);
		}
	}
}
