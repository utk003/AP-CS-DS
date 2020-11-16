import java.util.*;
/**
 * The MyHashSet class is a working hash set. This class does not implement Set<E>
 * because it does not provide implementations for methods such as clear().
 * 
 * A MyHashSet object can store objects using hashcodes (and bucketting). It can also
 * return its size, check if it contains an object, add an object to the set,
 * remove an object to the set, and return a string form of the set.
 * 
 * @author    Utkarsh Priyam
 * @version    Mid January - Sem 2
 * 
 * @param <E> the hash set stores only objects of type E
 */
public class MyHashSet<E>
{
    private static final int NUM_BUCKETS = 5;
    private LinkedList<E>[] buckets;
    private int size;
    private int modCount;
    
    /**
     * A constructor for the MyHashSet class.
     * @postcondition   creates a new, empty hash set
     */
    public MyHashSet()
    {
        buckets = new LinkedList[NUM_BUCKETS];
        size = 0;
        modCount = 0;
        for ( int i = 0; i < buckets.length; i++ )
            buckets[i] = new LinkedList<E>();
    }
    
    /**
     * Returns the index of the bucket where obj will be found
     * @param obj       the object to find the index of
     * @precondition    obj implements hashCode()
     * @return          the bucket index of obj
     */
    private int toBucketIndex(Object obj)
    {
        return Math.abs( ((E) obj).hashCode() ) % NUM_BUCKETS;
    }
    
    /**
     * Returns the size of the hash set
     * @return  the number of elements in the hash set
     */
    public int size()
    {
        return size;
    }
    
    /**
     * Returns whether or not the hash set contains obj
     * @param obj       the object to be searched for
     * @return          true if the hash set contains obj; otherwise,
     *                  false
     */
    public boolean contains(Object obj)
    {
        return buckets[ toBucketIndex(obj) ].contains( obj );
    }
    
    /**
     * Adds the specified object to the hash set if it is not already contained.
     * Returns whether or not the hash set was modified by this method.
     * @param obj       the object to be added
     * @postcondition   the hash set contains obj
     * @return          true if the hash set was modified; otherwise,
     *                  false
     */
    public boolean add(E obj)
    {
        if ( contains(obj) )
            return false;
        size++;
        modCount++;
        return buckets[ toBucketIndex(obj) ].add( obj );
    }
    
    /**
     * Removes the specified object to the hash set if it is contained.
     * Returns whether or not the hash set was modified by this method.
     * @param obj       the object to be removed
     * @postcondition   the hash set does not contain obj
     * @return          true if the hash set was modified; otherwise,
     *                  false
     */
    public boolean remove(Object obj)
    {
        boolean b = buckets[ toBucketIndex(obj) ].remove( obj );
        if ( b )
        {
            size--;
            modCount++;
        }
        return b;
    }
    
    /**
     * Returns the string form of the hash set
     * @return  returns the elements of the hash set as a string
     */
    public String toString()
    {
        String s = "";
        for (int i = 0; i < buckets.length; i++)
            if (buckets[i].size() > 0)
                s += i + ":" + buckets[i] + " ";
        return s;
    }
    
    /**
     * Returns an iterator for the hash set
     * The iterator will randomly iterate over the hash set
     * @return  a new iterator for the hash set
     */
    public Iterator<E> iterator()
    {
        return new MyHashSetIterator();
    }
    
    /**
     * An iterator for the hash set.
     * This iterator is equipped with a variety of exceptions ranging from
     * ConcurrentModificationException to NoSuchElementException.
     * The iterator can check if the hash set has any elements left
     * and can return the next element if one exists.
     * It can also remove the previous iterated element and
     * can throw an IllegalStateException if no such element exists.
     */
    public class MyHashSetIterator implements Iterator<E>
    {
        private int index;
        private ArrayList<E> elements;
        private int modCount;
        private boolean canRemove;
        
        /**
         * A constructor for the hash set's iterators
         */
        public MyHashSetIterator()
        {
            index = 0;
            modCount = MyHashSet.this.modCount;
            elements = new ArrayList<E>();
            for ( LinkedList<E> list : buckets )
                for ( E obj : list )
                    elements.add( obj );
            canRemove = false;
        }
        
        /**
         * Returns whether or not the hash set contains any more elements
         * @return  true if the hash set has more elements; otherwise,
         *          false
         */
        public boolean hasNext()
        {
            return index < elements.size();
        }
        
        /**
         * Returns the next element in the hash set if one exists
         * If no such element exists, the iterator throws a NoSuchElementException
         * If the hash set was modified after the creation of the iterator,
         * the iterator throws a ConcurrentModificationException
         * @return  the next element in the hash set if one exists and hash set
         *          was not modified after the creation of the iterator; otherwise,
         *          null
         */
        public E next()
        {
            /*
            try
            {
            */
            if ( modCount != MyHashSet.this.modCount )
                throw new ConcurrentModificationException();
            if ( ! hasNext() )
                throw new NoSuchElementException();
            index++;
            canRemove = true;
            return elements.get(index - 1);
            /*
            }
            catch ( ConcurrentModificationException e )
            {
                System.out.println("This hash set has been edited " +
                                   "since this iterator was created. " +
                                   "Please create another iterator. \n" +
                                   "This is a ConcurrentModificationException.");
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The iterator has reached " +
                                   "the end of this hash set.\n" +
                                   "This is a NoSuchElementException.");
            }
            catch ( Exception e )
            {
                System.out.println("no .. super rip");
            }
            finally
            {
                System.out.println("rip");
                return null;
            }
            */
        }
        
        /**
         * Removes the previous element in the hash set if one exists
         * If no such element exists, the iterator throws a IllegalStateException
         * If the hash set was modified after the creation of the iterator,
         * the iterator throws a ConcurrentModificationException
         */
        public void remove()
        {
            try
            {
                if ( modCount != MyHashSet.this.modCount )
                    throw new ConcurrentModificationException();
                if ( ! canRemove )
                    throw new IllegalStateException();
                canRemove = false;
                modCount++;
                index--;
                MyHashSet.this.remove( elements.remove(index) );
            }
            catch ( ConcurrentModificationException e )
            {
                System.out.println("This hash set has been edited " +
                                   "since this iterator was created. " +
                                   "Please create another iterator. \n" +
                                   "This is a ConcurrentModificationException.");
            }
            catch ( IllegalStateException e )
            {
                System.out.println("No such element exists. " +
                                   "The iterator remove method was called " +
                                   "with any call to the next method.\n" +
                                   "This is a IllegalStateException.");
            }
        }
    }
}