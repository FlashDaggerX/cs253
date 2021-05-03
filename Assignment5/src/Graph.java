import java.util.Iterator;

/**
 * The Graph ADT
 *
 * @author Kyle Guarco
 */
public abstract class Graph<V, E> {

	public abstract int numVertices();
	public abstract Iterator<Vertex> vertices();
	public abstract int numEdges();
	public abstract Iterator<Edge> edges();

	public abstract Edge getEdge(Vertex uref, Vertex vref);
	public abstract int outDegree(Vertex vref);
	public abstract int inDegree(Vertex vref);
	public abstract Iterator<Edge> outgoingEdges(Vertex vref);
	public abstract Iterator<Edge> incomingEdges(Vertex vref);

	public abstract Vertex insertVertex(V data);
	public abstract Edge insertEdge(Vertex uref, Vertex vref, E data);
	public abstract void removeVertex(Vertex vref);
	public abstract void removeEdge(Edge eref);

	public Positional<Vertex>[] endVertices(Edge eref) {
		return eref.ends;
	}

	public Vertex opposite(Vertex vref, Edge eref) {
		Positional<Vertex> vpos = vertexpos(vref);

		return eref.ends[0] == vpos ? eref.ends[1].element : vref;
	}

	/** Sets the positional {@code posref} for this vertex. */
	protected void setvertexpos(Vertex vref, Positional<Vertex> posref) {
		vref.position = posref;
	}

	/** @return The positional related to vertex {@code vref} */
	@SuppressWarnings("unchecked")
	protected <P extends Positional<Vertex>> P vertexpos(Vertex vref) {
		return (P) vref.position;
	}

	/** Sets the positional {@code posref} for this edge. */
	protected void setedgepos(Edge eref, Positional<Edge> posref) {
		eref.position = posref;
	}


	/** @return The positional related to edge {@code eref} */
	@SuppressWarnings("unchecked")
	protected <P extends Positional<Edge>> P edgepos(Edge eref) {
		return (P) eref.position;
	}

	/**
	 * A general vertex on a graph.
	 */
	class Vertex {
		protected Positional<Vertex> position;

		public V data;

		public Vertex(V data) {
			this.data = data;
		}

		public Vertex() {
			this(null);
		}

		@Override
		public String toString() {
			return data.toString();
		}
	}

	/**
	 * A general edge on a graph.
	 */
	@SuppressWarnings("unchecked")
	class Edge {
		protected Positional<Edge> position;

		public E data;
		public final Positional<Vertex>[] ends;

		public Edge(Positional<Vertex> uref, Positional<Vertex> vref, E data) {
			this.ends = new Positional[] {uref, vref};
			this.data = data;
		}

		public Edge(Positional<Vertex> uref, Positional<Vertex> vref) {
			this(uref, vref, null);
		}

		@Override
		public String toString() {
			return data.toString();
		}
	}
}
