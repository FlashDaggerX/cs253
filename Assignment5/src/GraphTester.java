import java.util.ArrayList;

public class GraphTester {
	public static void main(String[] args) {
		list();
		matrix();
	}

	public static void matrix() {
		AdjacencyMatrixGraph<Integer> graph = new AdjacencyMatrixGraph<>(4);

		ArrayList<Graph<Integer, Integer>.Vertex> verticies = new ArrayList<>();
		// Since you can't get the vertex references directly, this should be enough.
		graph.vertices().forEachRemaining(v -> verticies.add(v));

		graph.insertEdge(verticies.get(0), verticies.get(1), 8);
		Graph<Integer, Integer>.Edge e9 = graph.insertEdge(verticies.get(3), verticies.get(1), 9);

		System.out.println("Testing the AdjacencyMatrixGraph structure");
		System.out.println(graph);
		System.out.println("Removing edge 9");
		graph.removeEdge(e9);
		System.out.println(graph);
		System.out.println("Removing vertex 0");
		graph.removeVertex(verticies.get(0));
		System.out.println(graph);

		// Gotta clear the vertex references, since they've changed
		verticies.clear();
		graph.vertices().forEachRemaining(v -> verticies.add(v));

		System.out.println("Inserting edge 10");
		graph.insertEdge(verticies.get(2), verticies.get(1), 10);
		System.out.println(graph);
	}

	public static void list() {
		AdjacencyListGraph<Integer, Integer> graph = new AdjacencyListGraph<>();

		System.out.println("Testing the AdjacencyListGraph structure");
		Graph<Integer, Integer>.Vertex v1 = graph.insertVertex(1);
		Graph<Integer, Integer>.Vertex v2 = graph.insertVertex(2);
		Graph<Integer, Integer>.Vertex v3 = graph.insertVertex(3);
		Graph<Integer, Integer>.Vertex v4 = graph.insertVertex(4);
		Graph<Integer, Integer>.Vertex v5 = graph.insertVertex(5);
		System.out.println("Add 5 verticies, adding edges 5,6,7,8");
		graph.insertEdge(v1, v2, 5);
		graph.insertEdge(v1, v3, 6);
		Graph<Integer, Integer>.Edge e7 = graph.insertEdge(v3, v4, 7);
		graph.insertEdge(v5, v1, 8);
		System.out.println(graph);
		System.out.println("Removing vertex 2");
		graph.removeVertex(v2);
		System.out.println(graph);
		System.out.println("Removing edge 7");
		graph.removeEdge(e7);
		System.out.println(graph);
	}
}
