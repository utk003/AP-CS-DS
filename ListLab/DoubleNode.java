/**
 * A DoubleNode class that behaves like a doubly linked node for a doubly linked linked list.
 * It holds its value, the next node, and the previous node.
 * 
 * @author  Utkarsh Priyam
 * @version v1: 10-5-18
 */
public class DoubleNode
{
    private Object value;
    private DoubleNode previous;
    private DoubleNode next;
    
    /**
     * A constructor for the DoubleNode class.
     * @param v         the value of the double node to be created
     * @postcondition   creates a MyArrayList<E> object,
     *                  sets the value of the node to v,
     *                  and sets the next/previous nodes to null.
     */
    public DoubleNode(Object v)
    {
        value = v;
        previous = null;
        next = null;
    }
    
    /**
     * Returns the value of the node.
     * @return  the value of the node
     */
    public Object getValue()
    {
        return value;
    }
    
    /**
     * Returns the previous node.
     * @return  the previous node
     */
    public DoubleNode getPrevious()
    {
        return previous;
    }
    
    /**
     * Returns the next node.
     * @return  the next node
     */
    public DoubleNode getNext()
    {
        return next;
    }
    
    /**
     * Sets the value of the node to v
     * @param v the new value of the node
     */
    public void setValue(Object v)
    {
        value = v;
    }
    
    /**
     * Sets the previous node to p
     * @param p the new previous node
     */
    public void setPrevious(DoubleNode p)
    {
        previous = p;
    }
    
    /**
     * Sets the next node to n
     * @param n the new next node
     */
    public void setNext(DoubleNode n)
    {
        next = n;
    }
}