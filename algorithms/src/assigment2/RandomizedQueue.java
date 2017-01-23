package assigment2;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Item[] itemQueue;
    public RandomizedQueue()  {
        itemQueue = (Item[]) new Object[1];
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = itemQueue[i];
        itemQueue = copy;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    public void enqueue(Item item) {
        if (N == itemQueue.length) resize(2 * itemQueue.length);
        itemQueue[N++] = item;
    }
    public Item dequeue() {
        int randomNumber = StdRandom.uniform(N);
        Item item = itemQueue[randomNumber];
        itemQueue[randomNumber] = itemQueue[--N];
        return item;
    }
    public Item sample() {
        int  randomNumber = StdRandom.uniform(N);
        return itemQueue[randomNumber];
    }
    @Override
    public Iterator<Item> iterator() {
        return new ItemsIterator();
    }

    private class ItemsIterator implements Iterator<Item> {
        private int i = N;
        private Item[] copy = itemQueue;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            int randomNumber = StdRandom.uniform(i);
            Item item = itemQueue[randomNumber];
            itemQueue[randomNumber] = itemQueue[--i];
            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> testQueue = new RandomizedQueue<String>();
        testQueue.enqueue("1");
        testQueue.enqueue("3");
        testQueue.enqueue("4");
        testQueue.enqueue("6");
        testQueue.dequeue();
        testQueue.dequeue();
        testQueue.dequeue();
        String ss = testQueue.dequeue();
        StdOut.println(ss);
        testQueue.enqueue("1");
        testQueue.enqueue("3");
        Iterator<String> testIterator = testQueue.iterator();
        while (testIterator.hasNext()) {
            String s = testIterator.next();
            StdOut.println(s);
        }
    }
}
