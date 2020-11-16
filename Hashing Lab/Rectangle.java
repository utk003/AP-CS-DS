/**
 * The Rectangle class represents a rectangle.
 * The rectangle stores its longer side as its length
 * and its shorter side as its width.
 * The rectangle can return its length or width,
 * return its string form, return its hashcode,
 * and return whether it equals another rectangle.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Rectangle
{
    private int length;
    private int width;
    
    /**
     * A constructor for the Rectangle class
     * Makes the larger input the length and the smaller input the width
     * @param len   one side of the rectangle object
     * @param w     the other side of the rectangle object
     */
    public Rectangle(int len, int w)
    {
        length = Math.max(len, w);
        width = Math.min(len, w);
    }
    
    /**
     * Returns the length of the rectangle object
     * @return  the length (longer side) of the rectangle
     */
    public int getLength()
    {
        return length;
    }
    
    /**
     * Returns the width of the rectangle object
     * @return  the width (shorter side) of the rectangle
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * Returns the String form of the Rectangle (length x width)
     * @return  the String form of the Rectangle
     */
    public String toString()
    {
        return width + "x" + length;
    }
    
    /**
     * Returns whether or not this (Rectangle) equals Rectanlge obj
     * @param obj   the rectangle object to be compared to
     * @return      true if this equals obj (lengths and widths are equals); otherwise,
     *              false
     */
    @Override
    public boolean equals( Object obj )
    {
        Rectangle r = (Rectangle) obj;
        return length == r.getLength() && width == r.getWidth();
    }
    
    /**
     * Returns the rectangle's hashcode (its area)
     * @return  the rectangle's hashcode
     */
    @Override
    public int hashCode()
    {
        return length * width;
    }
}