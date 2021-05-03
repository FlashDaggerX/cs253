/**
 * This class is the blueprint for a binary tree.
 *
 * @author Kyle Guarco
 */
public class BinaryTree<E> {
	private Node root;

	public Node addRoot(E data) {
		if (root != null)
			return null;

		root = new Node(data);
		return root;
	}

	public Node addLeft(Node parentref, E data) {
		if (parentref == null || parentref.left != null)
			return null;

		parentref.left = new Node(data);
		parentref.left.parent = parentref;
		return parentref.left;
	}

	public Node addRight(Node parentref, E data) {
		if (parentref == null || parentref.right != null)
			return null;

		parentref.right = new Node(data);
		parentref.right.parent = parentref;
		return parentref.right;
	}

	public void set(Node noderef, E data) {
		noderef.data = data;
	}

	public Node remove(Node noderef) {
		if (noderef == null || noderef.childCount() > 1)
			return noderef;

		Node parentref = noderef.parent;

		if (parentref.left != null && parentref.left.data == noderef.data)
			parentref.left = null;
		else if (parentref.right != null && parentref.right.data == noderef.data)
			parentref.right = null;

		if (noderef.childCount() == 1) {
			Node childref = noderef.left != null ? noderef.left : noderef.right;
			parentref.data = childref.data;
		}

		return parentref;
	}

	public String preorder(Node noderef) {
		String s = noderef.data + ",";

		if (noderef.left != null)
			s += preorder(noderef.left);
		if (noderef.right != null)
			s += preorder(noderef.right);

		return s;
	}

	public String inorder(Node noderef) {
		String s = "";

		if (noderef.left != null)
			s += preorder(noderef.left);
		s += noderef.data + ",";
		if (noderef.right != null)
			s += preorder(noderef.right);

		return s;
	}

	public Node root() {
		return root;
	}

	public Node parent(Node noderef) {
		return noderef.parent;
	}

	public Node left(Node noderef) {
		return noderef.left;
	}

	public Node right(Node noderef) {
		return noderef.right;
	}

	class Node {
		public Node parent;
		public Node left, right;

		public E data;

		public Node(E data) {
			this.data = data;
		}

		public Node() {
			this(null);
		}

		public int childCount() {
			return (left != null ? 1 : 0) + (right != null ? 1 : 0);
		}

		public boolean isLeaf() {
			return childCount() == 0;
		}
	}
}
