import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Class to implement a generalization of a stack and a queue that supports adding and removing items from either 
 * the front or the back of the data structure
 * @author Castila
 *
 */
public class Deque<Item> implements Iterable<Item> {
    /** Size of the queue */
    private int n;
    
    /** First element */
    private Node first;
    
    /** Last element */
    private Node last;
    
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }
    
    /**  construct an empty deque */
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }
    
    /** Method to check if the structure is empty
     * 
     * @return true if emtpy
     */
    public boolean isEmpty() {
        return first == null;
    }
    
    /** Method to return the size of the structure, number of items 
     * */
    public int size() {
        return n;
    }
    
    /** Method to add the item to the front
     * @param item the item to add
     *  */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        }
        n++;
        // check if there is no last
        if (n == 1 && last == null) { // last and first are the same
            last = first;
        }
    }
    
    /** Method to add the item to the end
     * 
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (oldLast != null) { 
            oldLast.next = last;
        }
        n++;
        // check if there is no first
        if (n == 1 && first == null) { // last and first are the same
            first = last;
        }
    }
    
    /** Method to remove the item from the front
     * 
     * @return the first item
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        if (first != null) {
            first.prev = null;             // no previous item for the new first
        }
        n--;
        // check clearing last possible spurious reference
        if (n == 0) { // first = last
            last = first;
        }
        return item;                   // return the saved item
    }
    
    /** Method to remove the last item
     * 
     * @return the last item
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        Item item = last.item;        // save item to return
        last = last.prev;            // delete last node
        if (last != null) {
            last.next = null;             // no next item for the new last
        }
        n--;
        // check clearing last possible spurious reference
        if (n == 0) { // first = last
            first = last;
        }
        return item;
    }
    
    /**
     * Method to return an iterator over the items in order from front to end
     * @return the iterator 
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next; 
          //  System.out.println("Iterator returning " + item.toString());
            return item;
        }
    }
    
    /** Method for testing */
    public static void main(String[] args) {
        // size of the permutation
        int p = Integer.parseInt(args[0]);
        if (p > args.length) {
            throw new IllegalArgumentException("Permutation parameter out of bounds");
        }
        // System.out.println("number of permutations "+p);
        Deque<String> d = new Deque<String>();
        for (int i = 1; i < args.length; i++) {
            String s = args[i];
            int position = StdRandom.uniform(2);
            if (position == 1) {
                d.addFirst(s);
               // System.out.println("Adding " + s + " at first position");
            } else {
                d.addLast(s);
               // System.out.println("Adding " + s + " at last position");
            }
        }

        Iterator<String> iter = d.iterator();
        int count = 0;
        while (count < p && iter.hasNext()) {
            System.out.println(iter.next());
            count++;
        } 
    }
}
