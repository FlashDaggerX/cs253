/**
 * The single-linked version of the linked list for assignment #1
 *
 * @author Kyle Guarco
 * @version sll-1
 */
public class SingleLinkedList<T extends Comparable<T>>
{
    public static void main(String[] args)
    {
        SingleLinkedList<Integer> sll = new SingleLinkedList<>();

		for (int i = 100; i > 0; i -= 10)
			sll.add(i);

        sll.print();
        sll.add(67);
        sll.print();
        sll.add(23);
        sll.print();
    }

    private Node<T> first;
    private Node<T> head;
    private int size;

    public void add(T data)
    {
		Node<T> newnode = new Node<>(data);

        if (first == null)
        {
            first = newnode;
            head = first;
            size = 1;
            return;
        }

        boolean checkedFirst = false;
        if (first.data.compareTo(newnode.data) < 0)
        {
            newnode.next = first;
            first = newnode;
            checkedFirst = true;
        }

        if (!checkedFirst)
        {
            Node<T> noderef = first;

            while (noderef.next != null)
            {
                if (noderef.next != null && newnode.data.compareTo(noderef.next.data) > 0)
                    break;

                noderef = noderef.next;
            }

            newnode.next = noderef.next;
            noderef.next = newnode;

            while (noderef.next.next != null)
                noderef = noderef.next;

            head = noderef;
        }

        size++;
        if (size > 10)
            remove(head);
    }

    /**
     * Removes the first node containing the specified data from the list. Nodes in front of it are preserved.
     * @param data The data to scan for
     * @return Was the node removed?
     */
    public boolean remove(T data)
    {
        Node<T> noderef = first;

        // If the data is present in the first node, remove it and set first to the next node.
        if (noderef.data == data)
        {
            // Don't reset head here, since we're only checking the first node.
            first = noderef.next;
            return true;
        }

        // Check the other node if otherwise.
        while (noderef.next != null)
        {
            if (noderef.next.data == data)
            {
                return remove(noderef);
            }

            noderef = noderef.next;
        }

        return false;
    }

    public boolean remove(Node<T> noderef)
    {
        if (noderef == null)
            return false;

        Node<T> nextref = noderef.next;

        // Set the node to the one after it, if it exists.
        if (nextref != null)
            noderef.next = nextref.next;

        // Set the head to the new "front" of the list, using the current position
        // to traverse the rest of the list.
        while (noderef.next != null)
            noderef = noderef.next;

        head = noderef;

        size--;
        return true;
    }

    public void print()
    {
        Node<T> noderef = first;

        System.out.println("Printing out the single-linked list");
        while (noderef != null)
        {
            System.out.print(" " + noderef.data);
            noderef = noderef.next;
        }
        System.out.println();
    }

    static class Node<T>
    {
        public Node<T> next;
        public T data;

        public Node(T data)
        {
            this.data = data;
        }
    }
}

