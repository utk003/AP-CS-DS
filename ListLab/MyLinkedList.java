import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * A LinkedList class that behaves like a linked list. It holds elements (order matters).
 * It can be converted to string form, return its size, get an element at any index,
 * set an element at any index to another value, add an element to any position in the list,
 * remove any element from the list, and create iterators for the list.
 * 
 * @param <E>   the data type of elements that will be stored in the arraylist.
 * 
 * @author  Utkarsh Priyam
 * @version v1: 10-10-18
 */
public class MyLinkedList<E>
{
    private DoubleNode first;
    private DoubleNode last;
    private int size;
    private int modCount;
    
    /**
     * A constructor for the MyLinkedList class.
     * @postcondition   creates a MyLinkedList<E> object,
     *                  sets the size of the linked list to 0,
     *                  and sets the first/last nodes to null.
     */
    public MyLinkedList()
    {
        first = null;
        last = null;
        size = 0;
        modCount = 0;
    }
    
    /**
     * Converts the linked list to string form (for printing, etc.)
     * @return  The string form of the linked list, enclosed in [] and separated by commas (,).
     */
    public String toString()
    {
        DoubleNode node = first;
        if (node == null)
            return "[]";
        String s = "[";
        while (node.getNext() != null)
        {
            s += node.getValue() + ", ";
            node = node.getNext();
        }
        return s + node.getValue() + "]";
    }

    /** 
    * @precondition  0 <= index <= size / 2
    * @postcondition starting from first, returns the node
    *               with given index (where index 0
    *               returns first)
    */
    private DoubleNode getNodeFromFirst(int index)
    {
        DoubleNode node = first;
        while ( index != 0 )
        {
            node = node.getNext();
            index--;
        }
        return node;
    }

    /** 
    * @precondition  size / 2 <= index < size
    * @postcondition starting from last, returns the node
    *               with given index (where index size-1
    *               returns last)
    */
    private DoubleNode getNodeFromLast(int index)
    {
        DoubleNode node = last;
        while ( index != size - 1 )
        {
            node = node.getPrevious();
            index++;
        }
        return node;
    }

    /**
    * @precondition  0 <= index < size
    * @postcondition starting from first or last (whichever
    *               is closer), returns the node with given
    *               index
    */
    private DoubleNode getNode(int index)
    {
        if ( index <= size / 2 )
            return getNodeFromFirst(index);
        return getNodeFromLast(index);
    }
    
    /**
     * Returns the size of the linked list
     * @return  the size of the linked list
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
        return (E) getNode(index).getValue();
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
        DoubleNode node = getNode(index);
        E old = (E) node.getValue();
        node.setValue(obj);
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
        addLast(obj);
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
        DoubleNode node = getNode(index);
        E obj = (E) node.getValue();
        if ( size == 1 )
        {
            first = null;
            last = null;
        }
        else if ( index == 0 )
        {
            first = node.getNext();
            first.setPrevious(null);
        }
        else if ( index == size - 1 )
        {
            last = node.getPrevious();
            last.setNext(null);
        }
        else
        {
            node.getPrevious().setNext( node.getNext() );
            node.getNext().setPrevious( node.getPrevious() );
        }
        size--;
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
        DoubleNode newNode = new DoubleNode(obj);
        if ( size == 0 )
        {
            first = newNode;
            last = newNode;
        }
        else if ( index == 0 )
        {
            newNode.setNext(first);
            first.setPrevious(newNode);
            first = newNode;
        }
        else if ( index == size )
        {
            last.setNext(newNode);
            newNode.setPrevious(last);
            last = newNode;
        }
        else
        {
            DoubleNode node = getNode(index), prev = node.getPrevious();
            prev.setNext(newNode);
            newNode.setPrevious(prev);
            newNode.setNext(node);
            node.setPrevious(newNode);
        }
        size++;
    }
    
    /**
     * Adds an element obj to the front of the list
     * @param obj       the object to be appended to the list
     * @postcondition   appends obj to the list and adjusts size
     */
    public void addFirst(E obj)
    {
        add(0, obj);
    }
    
    /**
     * Adds an element obj to the end of the list
     * @param obj       the object to be appended to the list
     * @postcondition   appends obj to the list and adjusts size
     */
    public void addLast(E obj)
    {
        add(size, obj);
    }
    
    /**
     * Returns the first element
     * @return  the first element in the list
     */
    public E getFirst()
    {
        return (E) first.getValue();
    }
    
    /**
     * Returns the last element
     * @return  the last element in the list
     */
    public E getLast()
    {
        return (E) last.getValue();
    }
    
    /**
     * Removes the first element of the list
     * @postcondition   removes the first element, moving elements
     *                  at position 1 and higher to the left
     *                  (subtracts 1 from their indices) and adjusts size
     * @return          the former first element
     */
    public E removeFirst()
    {
        return remove(0);
    }
    
    /**
     * Removes the last element of the list
     * @postcondition   removes the last element and adjusts size
     * @return          the former last element
     */
    public E removeLast()
    {
        return remove(size-1);
    }
    
    /**
     * Creates an iterator for the linked list
     * @return  an iterator for the linked list
     */
    public Iterator<E> iterator()
    {
        return new MyLinkedListIterator();
    }
    
    /**
     * Returns the modified counter of the linked list
     * to any iterator that calls this method.
     * @return modCount
     */
    private int getModCount()
    {
        return modCount;
    }
    
    /**
     * Increments the modified counter of the linked list by 1.
     * @postcondition   modCount++
     */
    private void modCountPlusPlus()
    {
        modCount++;
    }
    
    /**
     * An iterator for the MyLinkedList<E> class.
     * It can go forwards through the linked list and
     * can remove the last seen element of the linked list.
     * 
     * @author  Utkarsh Priyam
     * @version v1: 10-10-18
     */
    private class MyLinkedListIterator implements Iterator<E>
    {
        private DoubleNode nextNode;
        private int index;
        private boolean seen;
        private int modCount;
        
        /**
         * A constructor for the MyLinkedListIterator class.
         * @postcondition   creates a MyLinkedListIterator object;
         *                  sets index to -1 and nextNode to the first node.
         */
        public MyLinkedListIterator()
        {
            nextNode = first;
            index = -1;
            seen = false;
            modCount = MyLinkedList.this.getModCount();
        }
        
        /**
         * Returns whether or not the linked list has any more elements.
         * @return  true if the linked list has more elements; otherwise,
         *          false
         */
        public boolean hasNext()
        {
            return nextNode != null;
        }
        
        /**
         * Returns next element and moves pointer forward
         * @return next Object in MyLinkedList
         */
        public E next()
        {
            try
            {
                if ( MyLinkedList.this.getModCount() != modCount )
                        throw new ConcurrentModificationException();
                if ( ! hasNext() )
                    throw new NoSuchElementException();
                seen = true;
                E obj = (E) nextNode.getValue();
                nextNode = nextNode.getNext();
                index++;
                return obj;
            }
            catch ( ConcurrentModificationException e )
            {
                System.out.println("The Linked List has been edited " +
                                   "since this iterator was created. " +
                                   "Please create another iterator. \n" +
                                   "This is a ConcurrentModificationException.");
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The iterator has reached " +
                                   "the end of the Linked List.\n" +
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
                MyLinkedList.this.remove(index);
                index--;
                MyLinkedList.this.modCountPlusPlus();
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
}