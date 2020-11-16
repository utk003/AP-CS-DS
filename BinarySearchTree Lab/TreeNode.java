/**
 * A TreeNode can hold an object and a reference to its left and right children.
 * @author Given in Project
 * @version Given in Project
 */
public class TreeNode
{
    private Object value;
    private TreeNode left;
    private TreeNode right;
    
    /**
     * Creates a TreeNode with the object initValue and null children.
     * @param initValue the object of the TreeNode
     */
    public TreeNode(Object initValue)
    { 
        this(initValue, null, null);
    }
    
    /**
     * Creates a TreeNode with the object initValue and children initLeft and initRight.
     * @param initValue the object of the TreeNode
     * @param initLeft  the left child of the TreeNode
     * @param initRight the right child of the TreeNode
     */
    public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
    { 
        value = initValue; 
        left = initLeft; 
        right = initRight; 
    }
    
    /**
     * Returns the object of the TreeNode
     * @return  the object of the TreeNode
     */
    public Object getValue()
    {
        return value;
    }
    
    /**
     * Returns the left child of the TreeNode
     * @return  the left child of the TreeNode
     */
    public TreeNode getLeft()
    {
        return left;
    }
    
    /**
     * Returns the right child of the TreeNode
     * @return  the right child of the TreeNode
     */
    public TreeNode getRight()
    {
        return right;
    }
    
    /**
     * Sets the value of the TreeNode to theNewValue
     * @param theNewValue   the new value of the TreeNode
     * @postcondition       the value of the TreeNode is now theNewValue
     */
    public void setValue(Object theNewValue)
    {
        value = theNewValue;
    }
    
    /**
     * Sets the left child of the TreeNode to theNewLeft
     * @param theNewLeft    the new left child of the TreeNode
     * @postcondition       the left child of the TreeNode is now theNewLeft
     */
    public void setLeft(TreeNode theNewLeft)
    {
        left = theNewLeft;
    }
    
    /**
     * Sets the right child of the TreeNode to theNewRight
     * @param theNewRight   the new right child of the TreeNode
     * @postcondition       the right child of the TreeNode is now theNewRight
     */
    public void setRight(TreeNode theNewRight)
    {
        right = theNewRight;
    }
}