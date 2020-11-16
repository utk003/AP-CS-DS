
/**
 * HeapUtils can manipulate and utilize heaps.
 * This class has the methods heapify, buildHeap, remove, and insert.
 * Those methods work exactly as described in their repesctive JavaDoc comments.
 * 
 * @author Utkarsh Priyam
 * @version 1-10-19
 */
public class HeapUtils
{
    /**
     * The heapify method heapifies the heap by bubbling down the value in the heap at index.
     * The method first swaps the current element with the larger of its 2 children.
     * Then, the method calls itself recursively to continue the heapification process
     * until the element has been shifted to a "stable" location (it can't bubble further).
     * This method runs in O( log(n) ) and
     * does not modify the structure (size, etc.) of the heap.
     * 
     * @param heap      the heap to be used (in array form)
     * @param index     the index of the element to be bubbled down
     * @param heapSize  the size of the heap
     * @precondition    heapSize < heap.length
     * @precondition    index < heapSize
     * @postcondition   the heap is not modified anywhere except for
     *                  the indices where the element passes through
     *                  during the heapification process
     */
    public static void heapify( Comparable[] heap, int index, int heapSize )
    {
        int clIndex = 2 * index, crIndex = clIndex + 1;
        Comparable temp;
        boolean right;
        if ( clIndex > heapSize )
            return; // the element has no children
        if ( crIndex > heapSize )
        {
            // the element only has a left child
            if ( heap[index].compareTo( heap[clIndex] ) < 0 )
            {
                // the element is less than its left child
                temp = heap[clIndex];
                heap[clIndex] = heap[index];
                heap[index] = temp;
                // no recursive call needed because clIndex is the last spot in the array
            }
        }
        else
        {
            // the element has both children
            if ( heap[crIndex].compareTo( heap[clIndex] ) < 0 )
            {
                // the element's left child is greater than the right child
                if ( heap[index].compareTo( heap[clIndex] ) < 0 )
                {
                    // the element is less than its left child
                    temp = heap[clIndex];
                    heap[clIndex] = heap[index];
                    heap[index] = temp;
                    heapify( heap, clIndex, heapSize );
                    // recursively calls heapify at the location to where the element was moved
                }
            }
            else
            {
                // the element's right child is greater than the left child
                if ( heap[index].compareTo( heap[crIndex] ) < 0 )
                {
                    // the element is less than its right child
                    temp = heap[crIndex];
                    heap[crIndex] = heap[index];
                    heap[index] = temp;
                    heapify( heap, crIndex, heapSize );
                    // recursively calls heapify at the location to where the element was moved
                }
            }
        }
    }
    
    /**
     * The buildHeap method rearranges the order of the elements in the heap array
     * in order to create a valid max heap (each element is greater than both of its 2 children).
     * The method calls the heapify method on each non-leaf node in the heap array,
     * starting with the element at index heapSize/2 and working its way down to 1.
     * This method runs in O( n ) and
     * does not modify the structure (size, etc.) of the heap.
     * 
     * @param heap      the heap to be used (in array form)
     * @param heapSize  the size of the heap
     * @precondition    heapSize < heap.length
     * @postcondition   the heap's size does not change,
     *                  and the heap array represents a valid max heap,
     *                  where each element is greater than both of its children
     */
    public static void buildHeap( Comparable[] heap, int heapSize )
    {
        for ( int i = heapSize/2; i > 0; i-- )
            heapify( heap, i, heapSize );
    }
    
    /**
     * The remove method removes the first element in the heap array.
     * It does so by switching the first element
     * with the only removable element (at index heapSize),
     * removing the extraneous element (by reducing heapSize by 1), and then
     * using the heapify method to bubble down the value at index 1.
     * This method runs in O( log(n) ) and ensures
     * that the returned heap array represents a valid max heap.
     *
     * @param heap      the heap to be used (in array form)
     * @param heapSize  the size of the heap
     * @precondition    heapSize < heap.length
     * @precondition    the heap array represents a valid max heap
     * @postcondition   the heap array repsents a valid max heap where
     *                  the largest element of the original max heap
     *                  has been removed; heapSize has been reduced by 1
     * @return          the value of the element removed
     */
    public static Comparable remove( Comparable[] heap, int heapSize )
    {
        Comparable temp = heap[1];
        heap[1] = heap[heapSize];
        heap[heapSize] = temp;
        heapSize--;
        heapify( heap, 1, heapSize );
        return temp;
    }
    
    /**
     * The insert method inserts the new element item into the heap array.
     * It does so by inserting the new element into the heap array at index heapSize + 1
     * and bubbling that element up until it is smaller than its parent.
     * It also increases the heapSize by 1.
     * This method runs in O( log(n) ) and ensures
     * that the returned heap array represents a valid max heap.
     * 
     * @param heap        the heap to be used (in array form)
     * @param item        the item to be added
     * @param heapSize    the size of the heap
     * @precondition    heapSize < heap.length
     * @precondition    the heap array represents a valid max heap
     * @postcondition    the heap array repsents a valid max heap where
     *                  the new element has been added to the original
     *                  max heap; heapSize has been increased by 1
     * @return          the new array representation of the heap
     */
    public static Comparable[] insert( Comparable[] heap, Comparable item, int heapSize )
    {
        heapSize++;
        if ( heapSize >= heap.length )
        {
            Comparable[] tempHeap = new Comparable[heap.length * 2];
            for ( int i = 0; i < heap.length; i++ )
                tempHeap[i] = heap[i];
            heap = tempHeap;
        }
        heap[heapSize] = item;
        int index = heapSize;
        boolean larger = item.compareTo( heap[index/2] ) > 0;
        Comparable temp;
        while ( larger )
        {
            temp = heap[index/2];
            heap[index/2] = heap[index];
            heap[index] = temp;
            index /= 2;
            if ( index > 1 )
                larger = item.compareTo( heap[index/2] ) > 0;
            else
                larger = false;
        }
        return heap; // So that the code complies
    }
    
    /**
     * The heapSort method sorts the heap in ascending order
     * and stores the values in the heap array.
     * The method works by building a heap out of the elements
     * of the heap array using the buildHeap method.
     * Then, the method repeatedly calls the remove method and stores the "removed" value.
     * Then, the method returns the heap array (fully sorted).
     * This method runs in O( n log(n) ) and returns the heap sorted in ascending order.
     * 
     * @param heap        the heap to be used (in array form)
     * @param heapSize    the size of the heap
     * @precondition    heapSize < heap.length
     * @precondition    the heap array represents a valid max heap
     * @postcondition    the heap array repsents the heap's elements in ascending order
     * @return          the heap's array representation sorted in ascending order
     */
    public static Comparable[] heapSort( Comparable[] heap, int heapSize )
    {
        buildHeap( heap, heapSize );
        while ( heapSize > 1 )
        {
            remove( heap, heapSize );
            heapSize--;
        }
        return heap;
    }
}
