
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot

    public RandomizedQueue() // construct an empty randomized queue
    {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() // is the queue empty?
    {
        return n == 0;
    }

    public int size() // return the number of items on the queue
    {
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    public void enqueue(Item item) // add the item
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        
        // double size of array if necessary and recopy to front of array
        if (n == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        n++;
    }


    public Item dequeue() // remove and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = q[first];
        q[first] = null;                            // to avoid loitering
        n--;
        first++;
        if (first == q.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == q.length/4) resize(q.length/2); 
        return item;
        
    }


    public Item sample() // return (but do not remove) a random item
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[StdRandom.uniform(first, n)];
    }


    public Iterator<Item> iterator() // return an independent iterator over items in random order
    {
         return new ArrayIterator();
    }


    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        
        private int i = 0;
        private int[] alreadyChosen = new int[n];
        public boolean hasNext()  { return i < n; }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int position;
            do{
                position = StdRandom.uniform(first, n);
                
            }while (alreadyChosen[position] == 1);
            alreadyChosen[position] = 1;
            Item item = q[position]; // q[(i + first) % q.length];
            i++;
            return item;
        }
    }
           
     public static void main(String[] args)
    { 
        RandomizedQueue<Double> x = new RandomizedQueue<Double>();
        x.enqueue(0.4);
        x.enqueue(0.4);
        System.out.println(x.size());
        x.enqueue(0.0);
        x.dequeue();
        
        
        
    }     
}
