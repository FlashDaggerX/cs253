import java.util.Iterator;

public class AdjacencyMatrixGraph<E> extends Graph<Integer, E> {
	private PositionalList<Vertex> vertexlist;
	private PositionalList<Edge> edgelist;
	private Edge[][] edges;

	@SuppressWarnings("unchecked")
	public AdjacencyMatrixGraph(int size) {
		this.vertexlist = new PositionalList<>();
		this.edgelist = new PositionalList<>();
		this.edges = new Graph.Edge[size][size];

		for (int i = 0; i < size; i++)
			insertVertex();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Vertex vertex : vertexlist) {
			builder.append("Edges on vertex \"" + vertex.toString() + "\": ");

			for (Edge edge : edges[vertex.data]) {
				if (edge != null)
					builder.append(edge.data.toString() + " ");
			}

			builder.append('\n');
		}

		return builder.toString();
	}

	@Override
	public int numVertices() {
		return vertexlist.size();
	}

	@Override
	public Iterator<Vertex> vertices() {
		return vertexlist.iterator();
	}

	@Override
	public int numEdges() {
		return edgelist.size();
	}

	@Override
	public Iterator<Edge> edges() {
		return edgelist.iterator();
	}

	@Override
	public Edge getEdge(Vertex uref, Vertex vref) {
		return edges[uref.data][vref.data];
	}

	@Override
	public int outDegree(Vertex vref) {
		int count = 0;

		for (Edge edge : edges[vref.data]) {
			if (edge != null)
				count++;
		}

		return count;
	}

	@Override
	public int inDegree(Vertex vref) {
		return outDegree(vref);
	}

	@Override
	public Iterator<Edge> outgoingEdges(Vertex vref) {
		return new Iterator<Edge>(){

			Edge[] edgeit = edges[vref.data];
			int index = 0;

			@Override
			public boolean hasNext() {
				return index < edgeit.length;
			}

			@Override
			public Edge next() {
				return edgeit[index++];
			}
		};
	}

	@Override
	public Iterator<Edge> incomingEdges(Vertex vref) {
		return outgoingEdges(vref);
	}

	@Override
	public Vertex insertVertex(Integer data) {
		Vertex vertex = new Vertex(data);
		MatPosition<Vertex> vertexpos = new MatPosition<>(vertex);
		setvertexpos(vertex, vertexpos);

		vertexpos.setref(vertexlist.add(vertexpos));

		return vertex;
	}

	public Vertex insertVertex() {
		return insertVertex(vertexlist.size());
	}

	@Override
	public Edge insertEdge(Vertex uref, Vertex vref, E data) {
		MatPosition<Vertex> upos = vertexpos(uref), vpos = vertexpos(vref);

		Edge edge = new Edge(upos, vpos, data);
		MatPosition<Edge> edgepos = new MatPosition<>(edge);
		setedgepos(edge, edgepos);

		edgepos.setref(edgelist.add(edgepos));

		edges[uref.data][vref.data] = edge;
		edges[vref.data][uref.data] = edge;

		return edge;
	}

	@Override
	public void removeVertex(Vertex vref) {
		//MatPosition<Vertex> vertexpos = vertexpos(vref);

		for (int y = 0; y < vertexlist.size(); y++)
			edges[y][vref.data] = null;

		for (int x = 0; x < vertexlist.size(); x++)
			edges[vref.data][x] = null;

		//vertexlist.remove(vertexpos.getref());
		//vertexlist.forEach(v -> v.data--);
	}

	@Override
	public void removeEdge(Edge eref) {
		MatPosition<Edge> edgepos = edgepos(eref);

		Positional<Vertex> upos = eref.ends[0], vpos = eref.ends[1];

		edges[upos.element.data][vpos.element.data] = null;
		edges[vpos.element.data][upos.element.data] = null;

		edgelist.remove(edgepos.getref());
	}
}
