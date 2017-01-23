package assigment2;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Item[] itemQueue;
    public RandomizedQueue()  {
        itemQueue = (Item[]) new Object[1];
    }

    private void resize(int capacity)
    {
        String[] copy = new String[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    public void enqueue(Item item) {
        if (N == s.length) resize(2 * s.length);
        itemQueue[N++] = item;
    }
    public Item dequeue() {
        randomNumber = StdRandom.uniform(N);
        item = itemQueue[randomNumber];
        itemQueue[randomNumber] = itemQueue[--N];
        return item;
    }
    public Item sample() {
        randomNumber = StdRandom.uniform(N);
        return itemQueue[randomNumber];
    }
    public Iterator<Item> iterator() {

    }
    public static void main(String[] args) {

    }
}
