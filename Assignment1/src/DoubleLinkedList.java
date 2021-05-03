/**
 * The double-linked version of the linked list for assignment #1
 *
 * @author Kyle Guarco
 * @version dll-1
 */
public class DoubleLinkedList<T extends Comparable<T>>
{
    public static void main(String[] args)
    {
        DoubleLinkedList<Integer> dll = new DoubleLinkedList<>();

        for (int i = 100; i > 0; i -= 10)
            dll.add(i);

        dll.print();
        dll.add(5);
        dll.print();
        dll.add(112);
        dll.print();
        dll.add(72);
        dll.print();
    }

    private Node<T> first;
    private Node<T> tail;
    private int size;

    public void add(T data)
    {
        Node<T> newnode = new Node<T>(data);

        if (first == null)
        {
            first = newnode;
            tail = first;
            size = 1;
            return;
        }

        boolean checkedFirst = false;
        if (newnode.data.compareTo(first.data) > 0)
        {
            newnode.next = first;
            first = newnode;
            checkedFirst = true;
        }

        if (!checkedFirst)
        {
            Node<T> noderef = tail;

            while (noderef.previous != null)
            {
                if (newnode.data.compareTo(noderef.data) < 0)
                    break;

                noderef = noderef.previous;
            }

            newnode.previous = noderef;
            newnode.next = noderef.next;
            noderef.next = newnode;

            while (noderef.next != null)
                noderef = noderef.next;

            tail = noderef;
        }

        size++;
        if (size > 10)
            remove(tail);
    }

    public boolean remove(T data)
    {
        Node<T> noderef = first;

        while (noderef != null)
        {
            if (noderef.data == data)
                return remove(noderef);

            noderef = noderef.next;
        }

        return false;
    }

    /**
     * @param noderef
     * @return Was the specified node removed from the list?
     */
    public boolean remove(Node<T> noderef)
    {
        if (noderef == null)
            return false;

        Node<T> nextref = noderef.next;
        Node<T> prevref = noderef.previous;

        if (nextref != null)
            nextref.previous = prevref;
        if (prevref != null)
            prevref.next = nextref;

        // Since noderef hasn't been garbage-collected yet, check and
        // see if either end of the list has been tampered with.
        if (noderef.data == first.data)
            first = nextref;
        else if (noderef.data == tail.data)
            tail = prevref;

        size--;
        return true;
    }

    /**
     * Replaces the node at the given position 'noderef' with 'newnode'
     * @param noderef The node to replace
     * @param newnode The node to take the place of 'noderef'
     * @return Was the node replaced successfully?
     */
    public boolean replace(Node<T> noderef, Node<T> newnode)
    {
        if (noderef == null)
            return false;

        Node<T> nextref = noderef.next;
        Node<T> prevref = noderef.previous;

        if (nextref != null)
            nextref.previous = newnode;
        if (prevref != null)
            prevref.next = newnode;

        newnode.next = nextref;
        newnode.previous = prevref;

        // Check and see if either end of the list has been tampered with.
        if (noderef.data == first.data)
            first = newnode;
        else if (noderef.data == tail.data)
            tail = newnode;

        return true;
    }

    public void print()
    {
        Node<T> noderef = first;

        System.out.println("Printing out the double-linked list");
        while (noderef != null)
        {
            System.out.print(" " + noderef.data);
            noderef = noderef.next;
        }
        System.out.println();
    }

    static class Node<T>
    {
        public Node<T> previous, next;
        public T data;

        public Node(T data)
        {
            this.data = data;
        }
    }
}
