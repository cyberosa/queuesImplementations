import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Class to implement this structure similar to a stack or queue, 
 * except that the item removed is chosen uniformly at random from items in the data structure
 * @author Castila
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /** The array of items */
    private Item[] a;
    
    /** Size of the queue: currently in use */
    private int n;
    
    /** construct an empty randomized queue
     * 
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }
    
    /**
     *  resize the underlying array holding the elements
     * @param capacity
     */
    private void resize(int capacity) {
        if (capacity < n) {
            return; // it is not possible to reduce the size
        }
        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;

       // alternative implementation
       // a = java.util.Arrays.copyOf(a, capacity);
    }
    
    /**
     * Method to shuffle only the elements that are not null
     */
    private void myShuffle() {
        for (int i = 0; i < n; i++) {
            int r = i + StdRandom.uniform(n-i);     // between i and n-1
            Item temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    /**
     * Is the randomized queue empty?
     * @return
     */
    public boolean isEmpty() {
        return n <= 0;
    }
    
    /**
     * return the number of items on the randomized queue, currently in use
     * @return the size
     */
    public int size() {
        return n;
    }
    
    /** Method to add the item
     * 
     * @param item
     */
    public void enqueue(Item item) {
        if (n == a.length) {
            resize(2*a.length);    // double size of array if necessary
        }
        a[n++] = item;  
    }
    
    /** Method to remove and return a random item
     * 
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        if (n > 1) {
            myShuffle();
        }
        Item item = a[n-1];
        a[n-1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) {
            resize(a.length/2);
        }
        return item;
    }
    
    /** Method to return a random item (but do not remove it)
     * 
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        if (n > 1) {
            myShuffle();
        }
        Item item = a[n-1];
        return item;
    }
    
    /** Method to  return an independent iterator over items in random order
     * 
     */
    public Iterator<Item> iterator() {
        return new IndependentIterator();
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class IndependentIterator implements Iterator<Item> {
        private int i;
        private final Item[] copy;

        public IndependentIterator() {
            if (n > 1) {
                myShuffle();
            }
            i = n-1;
            copy = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                copy[j] = a[j];
            }
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy[i--];
        }
    }
    
    /**
     * unit testing (optional)
     * @param args
     */
    public static void main(String[] args) {
        // size of the permutation
        int p = Integer.parseInt(args[0]);
        if (p > args.length) {
            throw new IllegalArgumentException("Permutation parameter out of bounds");
        }
        // System.out.println("number of permutations " + p);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        for (int i = 1; i < args.length; i++) {
            String s = args[i];
            rq.enqueue(s);
        }
        Iterator<String> iter = rq.iterator();
        int count = 0;
        while (count < p && iter.hasNext()) {
            System.out.println(iter.next());
            count++;
        }
    }
}
