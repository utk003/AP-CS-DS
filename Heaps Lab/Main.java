

/**
 * This is the "main class" which is currently only being used to test the HeapDisplay.
 * 
 * @author Utkarsh Priyam
 * @version 1-10-19
 */
public class Main 
{
    /**
     * Creates an array of 12 random Integers that represents a heap.
     * Uses HeapDisplay to display aforementioned heap.
     * @param args arguments from the command line
     */
    public static void main(String [ ] args)
    {
        int size = 11;
        Integer[] ints = new Integer[size + 1];
        for ( int i = 1; i < ints.length; i++ )
            ints[i] = new Integer( 1 + (int) (99 * Math.random()) );
        HeapDisplay dOrig = new HeapDisplay("Original");
        HeapDisplay dHeap = new HeapDisplay("Valid Heap");
        HeapDisplay dAdded = new HeapDisplay("Stuff Added");
        HeapDisplay dRemoved = new HeapDisplay("Stuff Removed");
        dOrig.displayHeap( ints, size );
        HeapUtils h = new HeapUtils();
        h.buildHeap( ints, size );
        dHeap.displayHeap( ints, size );
        int val;
        Comparable[] temp;
        for ( int i = 0; i < 10; i++ )
        {
            val = new Integer( 1 + (int) (99 * Math.random()) );
            System.out.println(val);
            temp = h.insert(ints, val, size);
            if ( ints.length != temp.length )
                ints = new Integer[temp.length];
            size++;
            for ( int j = 1; j <= size; j++ )
                ints[j] = (Integer) temp[j];
        }
        dAdded.displayHeap( ints, size );
        System.out.println();
        System.out.println();
        for ( int i = 0; i < 10; i++ )
        {
            System.out.println( h.remove(ints, size) );
            size--;
        }
        dRemoved.displayHeap( ints, size );
    }

}
