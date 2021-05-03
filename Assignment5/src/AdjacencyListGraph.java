import java.util.HashMap;
import java.util.Iterator;

public class AdjacencyListGraph<V, E> extends Graph<V, E> {
	private PositionalList<Vertex> vertexlist;
	private PositionalList<Edge> edgelist;
	private HashMap<ListPosition<Vertex>, PositionalList<Edge>> incidence;

	public AdjacencyListGraph() {
		this.vertexlist = new PositionalList<>();
		this.edgelist = new PositionalList<>();
		this.incidence = new HashMap<>();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		PositionalList<Edge> edges;
		for (Vertex vertex : vertexlist) {
			edges = incidence.get(vertexpos(vertex));

			builder.append("Edges on vertex \"" + vertex.toString() + "\": ");

			for (Edge edge : edges) {
				builder.append(edge.toString() + " ");
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
		PositionalList<Edge> uedges = incidence.get(vertexpos(uref)), vedges = incidence.get(vertexpos(vref));

		for (Edge uedge : uedges) {
			for (Edge vedge : vedges) {
				if (uedge == vedge)
					return uedge;
			}
		}

		return null;
	}

	@Override
	public int outDegree(Vertex vref) {
		PositionalList<Edge> edges = incidence.get(vertexpos(vref));
		return edges.size();
	}

	@Override
	public int inDegree(Vertex vref) {
		return outDegree(vref);
	}

	@Override
	public Iterator<Edge> outgoingEdges(Vertex vref) {
		PositionalList<Edge> edges = incidence.get(vertexpos(vref));
		return edges.iterator();
	}

	@Override
	public Iterator<Edge> incomingEdges(Vertex vref) {
		return outgoingEdges(vref);
	}

	@Override
	public Vertex insertVertex(V data) {
		Vertex vertex = new Vertex(data);

		ListPosition<Vertex> pos = new ListPosition<>(vertex);
		pos.connect(vertexlist, vertexlist.add(pos));
		setvertexpos(vertex, pos);

		PositionalList<Edge> edges = new PositionalList<Edge>();
		incidence.put(pos, edges);

		return vertex;
	}

	@Override
	public Edge insertEdge(Vertex uref, Vertex vref, E data) {
		ListPosition<Vertex> upos = vertexpos(uref), vpos = vertexpos(vref);
		if (upos == vpos)
			return null;

		Edge edge = new Edge(upos, vpos, data);
		ListPosition<Edge> edgepos = new ListPosition<Edge>(edge);
		setedgepos(edge, edgepos);

		PositionalList<Edge> uedges = incidence.get(upos), vedges = incidence.get(vpos);

		edgepos.connect(uedges, uedges.add(edgepos));
		edgepos.connect(vedges, vedges.add(edgepos));

		return edge;
	}

	@Override
	public void removeVertex(Vertex vref) {
		ListPosition<Vertex> vertexpos = vertexpos(vref);

		PositionalList<Edge> vedges = incidence.get(vertexpos);
		for (Edge edge : vedges) {
			removeEdge(edge);
		}

		removefromlist(vertexlist, vertexpos);
		vertexpos.disconnect(vertexlist);

		incidence.remove(vertexpos);
	}

	@Override
	public void removeEdge(Edge eref) {
		ListPosition<Edge> edgepos = edgepos(eref);

		removefromlist(edgelist, edgepos);
		edgepos.disconnect(edgelist);

		PositionalList<Edge> vedges;
		for (Positional<Vertex> vertexpos : eref.ends) {
			vedges = incidence.get(vertexpos);
			removefromlist(vedges, edgepos);
			edgepos.disconnect(vedges);
		}
	}

	/**
	 * Removes a positional from this linked list. The relation to this
	 * linked list isn't undone automagically, and requires a manual call to
	 * {@code Position.disconnect()}.
	 * @param listref The list to delete from
	 * @param posref A reference to a positional (this isn't deleted, only the node).
	 * @return Did the removal succeed?
	 */
	private <T> boolean removefromlist(PositionalList<T> listref, ListPosition<T> posref) {
		return listref.remove(posref.get(listref));
	}
}
