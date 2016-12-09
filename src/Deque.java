
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *
 * @author christian
 */
public class Deque<Item> implements Iterable<Item> {
    
    private Node<Item> head;    // left beginning of deque
    private Node<Item> tail;     // right begining of deque
    
      
    private int n;               // number of elements on deque
    
    
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;          
        private Node<Item> previous;
    }
    
   public Deque()    // construct an empty deque
   {
       
   }
   
   /**
     * Returns true if is empty.
     *
     * @return {@code true} if this deque is empty; {@code false} otherwise
     */
   public boolean isEmpty()    // is the deque empty?
   {
       return n == 0;
   }
   
   /**
     * Returns the number of items in this deque.
     *
     * @return the number of items in this deque
     */
   public int size()   // return the number of items on the deque
   {
       return n;
   }
   
   public void addFirst(Item item)   // add the item to the front
   {
       if (item == null)
            throw new java.lang.NullPointerException();
       
       Node<Item> oldfront = head;
       head = new Node<Item>();
       head.item = item;
       head.next = oldfront;
       head.previous = null;
       
               
       if (isEmpty()){
           tail = head;           
       }
       else{
           oldfront.previous = head;
       }
       
       n++;
   }
   public void addLast(Item item)     // add the item to the end
   {
       if (item == null)
           throw new java.lang.NullPointerException();
       
        Node<Item> oldlast = tail;        
        tail = new Node<Item>();
        tail.item = item;
        tail.next = null;
        tail.previous = oldlast;
        
        if (isEmpty()){
            head = tail;        
        }
        else{
            oldlast.next = tail;            
        }
        n++;
   }
   public Item removeFirst()  // remove and return the item from the front
   {       
       if (isEmpty()) throw new NoSuchElementException("Deque underflow");
       
       Item item = head.item;
       
       if (n == 1){           
           head = null;
           tail = null;           
       }
       else{
       
       head = head.next;
       head.previous = null;
       
       }
       n--;   
       return item;
       
   }
   public Item removeLast()   // remove and return the item from the end
   {       
       if (isEmpty()) throw new NoSuchElementException("Deque underflow");
       
       Item item = tail.item;        
       if (n == 1){           
           head = null;
           tail = null;           
       }
       else{           
        tail = tail.previous;
        tail.next = null; //  to avid loitering       
       }       
       n--;
       return item;
   }
   
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new ListIterator<Item>(head);
   }
   
   
   //public static void main(String[] args)   // unit testing
           
      
           
           
   // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    public static void main(String[] args)
    { 
        Deque<Double> x = new Deque<Double>();
        x.addFirst(0.4);
        x.addLast(0.4);
        System.out.println(x.size());
        x.removeFirst();
        x.removeLast();
        System.out.println(x.size());
        System.out.println(x.isEmpty());
        //x.addFirst(null);
        
        
    }
           
}

