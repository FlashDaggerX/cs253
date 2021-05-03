/**
 * Describes a positional that holds one node reference. Note that the
 * MatPosition should be used ONLY IF the node in question is used in one list.
 */
public class MatPosition<T> extends Positional<T> {
	private PositionalList<T>.Node noderef;

	public MatPosition(T element) {
		super(element);
	}

	public void setref(PositionalList<T>.Node noderef) {
		this.noderef = noderef;
	}

	public PositionalList<T>.Node getref() {
		return noderef;
	}
}
