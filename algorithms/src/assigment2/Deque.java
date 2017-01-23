package assigment2;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null, last = null;
    private int size;
    public Deque() {

    }
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public boolean isEmpty() { return first == null; }

    public int size()
    {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node newfirst = new Node();
        newfirst.item = item;
        newfirst.previous = null;
        newfirst.next = first;

        if (isEmpty()) last = newfirst;
        else first.previous = newfirst;
        first = newfirst;
        size ++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        size ++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        else first.previous = null;
        size --;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if (last == null) first = null;
        else last.next = null;
        size --;
        return item;
    }
    @Override
    public Iterator<Item> iterator() {
        return new ItemsIterator();
    }

    private class ItemsIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {

    }
}
