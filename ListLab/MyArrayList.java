import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * An ArrayList class that behaves like an arraylist. It holds elements (order matters).
 * It can be converted to string form, return its size, return its maximum capacity,
 * get an element at any index, set an element at any index to another value,
 * add an element to any position in the list, remove any element from the list,
 * and create iterators / list iterators for the list.
 * 
 * @param <E>   the data type of elements that will be stored in the arraylist.
 * 
 * @author  Utkarsh Priyam
 * @version v1: 10-5-18
 */
public class MyArrayList<E>
{
    private int size;
    private Object[] values;  //(Java doesn't let us make an array of type E)
    private int modCount;
    
    /**
     * A constructor for the MyArrayList class.
     * @postcondition   creates a MyArrayList<E> object,
     *                  sets the size of the arraylist to 0,
     *                  and sets the underlying array to length 1.
     */
    public MyArrayList()
    {
        size = 0;
        values = new Object[1];
        modCount = 0;
    }

    /**
     * Converts the arraylist to string form (for printing, etc.)
     * @return  The string form of the arraylist, enclosed in [] and separated by commas (,).
     */
    public String toString()
    {
        if (size == 0)
            return "[]";

        String s = "[";
        for (int i = 0; i < size - 1; i++)
            s += values[i] + ", ";
        return s + values[size - 1] + "]";
    }

    /**
     * Doubles the length of the the underlying array.
     * @postcondition   replaces the array with one that is
     *                  twice as long, and copies all of the
     *                  old elements into it
     */
    private void doubleCapacity()
    {
        Object[] newVals = new Object[2*size];
        for ( int i = 0; i < size; i++ )
            newVals[i] = values[i];
        values = newVals;
    }

    /**
     * Returns the maximum capacity of the arraylist
     * @return  the length of the underlying array
     */
    public int getCapacity()
    {
        return values.length;
    }
    
    /**
     * Returns the size of the arraylist
     * @return  the size of the arraylist
     */
    public int size()
    {
        return size;
    }
    
    /**
     * Returns the element at index
     * @param index     The index of the element to return
     * @precondition    index is in the interval [0,size)
     * @return          the object at index
     */
    public E get(int index)
    {
        return (E) values[index];
    }
    
    /**
     * Sets the element at index to obj
     * @param index     the index of the element to replace
     * @param obj       the new object
     * @precondition    index is in the interval [0,size)
     * @postcondition   replaces the element at position index with obj
     * @return          the element formerly at the specified position
     */
    public E set(int index, E obj)
    {
        E old = get(index);
        values[index] = obj;
        return old;
    }
    
    /**
     * Adds an element obj to the end of the list
     * @param obj       the object to be appended to the list
     * @postcondition   appends obj to end of list and adjusts size
     * @return          true
     */
    public boolean add(E obj)
    {
        add(size, obj);
        return true;
    }

    /**
     * Removes the element at index
     * @param index     the index of the element to be removed
     * @precondition    index is in the interval [0,size)
     * @postcondition   removes element from position index, moving elements
     *                  at position index + 1 and higher to the left
     *                  (subtracts 1 from their indices) and adjusts size
     * @return          the element formerly at the specified position
     */
    public E remove(int index)
    {
        E obj = get(index);
        size--;
        for ( int i = index; i < size; i++ )
            values[i] = values[i+1];
        return obj;
    }
    
    /**
     * Adds an element obj to the specified position in the list
     * @param obj       the object to be appended to the list
     * @param index     the index where the element will be added
     * @precondition    index is in the interval [0,size]
     * @postcondition   appends obj to the list and adjusts size
     */
    public void add(int index, E obj)
    {
        if ( size == values.length )
            doubleCapacity();
        for ( int i = size; i > index; i-- )
            values[i] = values[i-1];
        values[index] = obj;
        size++;
    }
    
    /**
     * Creates an iterator for the arraylist
     * @return  an iterator for the arraylist
     */
    public Iterator<E> iterator()
    {
        return new MyArrayListIterator();
    }
    
    /**
     * Creates a listIterator for the arraylist
     * @return  a listIterator for the arraylist
     */
    public ListIterator<E> listIterator()
    {
        return new MyArrayListListIterator();
    }
    
    /**
     * Returns the modified counter of the arraylist
     * to any iterator or list iterator that calls this method.
     * @return modCount
     */
    private int getModCount()
    {
        return modCount;
    }
    
    /**
     * Increments the modified counter of the arraylist by 1.
     * @postcondition   modCount++
     */
    private void modCountPlusPlus()
    {
        modCount++;
    }
    
    /**
     * An iterator for the MyArrayList<E> class.
     * It can go forwards through the arraylist and
     * can remove the last seen element of the arraylist.
     * 
     * @author  Utkarsh Priyam
     * @version v1: 10-5-18
     */
    private class MyArrayListIterator implements Iterator<E>
    {
        //the index of the value that will be returned by next()
        private int nextIndex;
        private boolean seen;
        private int modCount;
        
        /**
         * A constructor for the MyArrayListIterator class.
         * @postcondition   creates a MyArrayListIterator object;
         *                  sets nextIndex to 0 and seen to false.
         */
        public MyArrayListIterator()
        {
            nextIndex = 0;
            seen = false;
            modCount = MyArrayList.this.getModCount();
        }
        
        /**
         * Returns whether or not the arraylist has any more elements.
         * @return  true if the arraylist has more elements; otherwise,
         *          false
         */
        public boolean hasNext()
        {
            return nextIndex < size;
        }
        
        /**
         * Returns next element and moves pointer forward
         * @return next Object in MyArrayList
         */
        public E next()
        {
            try
            {
                if ( MyArrayList.this.getModCount() != modCount )
                    throw new ConcurrentModificationException();
                if ( ! hasNext() )
                    throw new NoSuchElementException();
                seen = true;
                E obj = (E) values[nextIndex];
                nextIndex++;
                return obj;
            }
            catch ( ConcurrentModificationException e )
            {
                System.out.println("The ArrayList has been edited " +
                                   "since this iterator was created. " +
                                   "Please create another iterator. \n" +
                                   "This is a ConcurrentModificationException.");
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The iterator has reached " +
                                   "the end of the ArrayList.\n" +
                                   "This is a NoSuchElementException.");
            }
            finally
            {
                return null;
            }
        }
        
        /**
         * Removes the last element seen
         * @precondition    the iterator has "seen" an element
         * @postcondition   removes element from position index, moving elements
         *                  at position index + 1 and higher to the left
         *                  (subtracts 1 from their indices) and adjusts size
         */
        public void remove()
        {
            try
            {
                if ( ! seen )
                    throw new NoSuchElementException();
                seen = false;
                MyArrayList.this.remove(nextIndex - 1);
                MyArrayList.this.modCountPlusPlus();
                modCount++;
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The iterator has not called next() " +
                                   "before calling remove().\n" +
                                   "This is a NoSuchElementException.");
            }
        }
    }
    
    /**
     * An iterator for the MyArrayList<E> class.
     * It can go forwards and backwards through the arraylist,
     * can remove the last seen element of the arraylist, and
     * can replace the last seen element of the arraylist with another.
     * 
     * @author  Utkarsh Priyam
     * @version v1: 10-5-18
     */
    private class MyArrayListListIterator extends MyArrayListIterator implements ListIterator<E>
    {
        // note the extends MyArrayListIterator 
        // Remember this class thus inherits the methods from the parent class.

        private int nextIndex;
        private boolean forward; //direction of traversal
        private boolean seen;
        private int modCount;

        /**
         * A constructor for the MyArrayListIterator class.
         * @postcondition   creates a MyArrayListIterator object;
         *                  sets nextIndex to 0, forward to true, and seen to false.
         */
        public MyArrayListListIterator()
        {
            nextIndex = 0;
            forward = true;
            seen = false;
            modCount = MyArrayList.this.getModCount();
        }
        
        /**
         * Returns next element and moves pointer forward
         * @return  next Object in MyArryaList
         */
        public E next()
        {
            try
            {
                if ( MyArrayList.this.getModCount() != modCount )
                    throw new ConcurrentModificationException();
                if ( ! hasNext() )
                    throw new NoSuchElementException();
                return super.next();
            }
            catch ( ConcurrentModificationException e )
            {
                System.out.println("The ArrayList has been edited " +
                                   "since this list iterator was created. " +
                                   "Please create another list iterator. \n" +
                                   "This is a ConcurrentModificationException.");
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The list iterator has reached " +
                                   "the end of the ArrayList.\n" +
                                   "This is a NoSuchElementException.");
            }
            finally
            {
                return null;
            }
        }

        /**
         * Adds element before element that would be returned by next
         * @param obj   element to add
         */
        public void add(E obj)
        {
            MyArrayList.this.add(nextIndex, obj);
            MyArrayList.this.modCountPlusPlus();
            modCount++;
        }

        /**
         * Determines whether there is another element in MyArrayList
         * while traversing in the backward direction
         * @return true if there is another element, false otherwise
         */
        public boolean hasPrevious()
        {
            return nextIndex > 0;
        }

        /**
         * Returns previous element and moves pointer backwards
         * @return  previous Object in MyArryaList
         */
        public E previous()
        {
            try
            {
                if ( MyArrayList.this.getModCount() != modCount )
                    throw new ConcurrentModificationException();
                if ( ! hasPrevious() )
                    throw new NoSuchElementException();
                seen = true;
                forward = false;
                E obj = (E) values[nextIndex - 1];
                nextIndex--;
                return obj;
            }
            catch ( ConcurrentModificationException e )
            {
                System.out.println("The ArrayList has been edited " +
                                   "since this list iterator was created. " +
                                   "Please create another list iterator. \n" +
                                   "This is a ConcurrentModificationException.");
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The list iterator has reached " +
                                   "the end of the ArrayList.\n" +
                                   "This is a NoSuchElementException.");
            }
            finally
            {
                return null;
            }
        }

        /**
         * Returns index of the next element 
         * @return  index of element that would be 
         *          returned by a call to next()
         */
        public int nextIndex()
        {
            return nextIndex;
        }

        /**
         * Returns index of the previous element 
         * @return  index of element that would be 
         *          returned by a call to previous()
         */
        public int previousIndex()
        {
            return nextIndex - 1;
        }

        /**
         * Removes the last element seen
         * @precondition    the listIterator has "seen" an element
         * @postcondition   removes element from position index, moving elements
         *                  at position index + 1 and higher to the left
         *                  (subtracts 1 from their indices) and adjusts size
         */
        public void remove()
        {
            try
            {
                if ( ! seen )
                    throw new NoSuchElementException();
                seen = false;
                if ( forward )
                    MyArrayList.this.remove(nextIndex - 1);
                else
                    MyArrayList.this.remove(nextIndex);
                MyArrayList.this.modCountPlusPlus();
                modCount++;
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The list iterator has not called next() " +
                                   "before calling remove().\n" +
                                   "This is a NoSuchElementException.");
            }
        }

        /**
         * Replaces the last seen element with obj
         * @precondition    the listIterator has "seen" an element
         * @postcondition   replaces the element at the specified index with obj
         */
        public void set(E obj)
        {
            try
            {
                if ( ! seen )
                    throw new NoSuchElementException();
                seen = false;
                MyArrayList.this.set(nextIndex, obj);
                MyArrayList.this.modCountPlusPlus();
                modCount++;
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The list iterator has not called next() " +
                                   "before calling set().\n" +
                                   "This is a NoSuchElementException.");
            }
        }
    }
}