import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;


/**
 * Class to test the Deque and RandomizedQueue classes 
 * it takes an integer k as a command-line argument; 
 * then reads in a sequence of strings from standard input using StdIn.readString(); 
 * and prints exactly k of them, uniformly at random.
 * @author Castila
 *
 */
public class Permutation {

    /** Class to print k uniformly random permutations of a list of strings
     * 
     * @param args
     */
    public static void main(String[] args) {
        int p = Integer.parseInt(args[0]);
        if (p > args.length) {
            throw new IllegalArgumentException("Permutation parameter out of bounds");
        }
        Deque<String> d = new Deque<String>();
        for (int i = 1; i < args.length; i++) {
            String s = args[i];
            int position = StdRandom.uniform(2);
            if (position == 1) {
                d.addFirst(s);
            } else {
                d.addLast(s);
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
