import java.awt.Color;
/**
 * The Block class encapsulates a Block abstraction which can be placed into a Gridworld style grid
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    /**
     * Constructs a blue block, because blue is the greatest color ever!
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * Returns the color of the block
     * @return  the block's color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of the block to newColor
     * @param newColor  the block's new color
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Returns the grid of the block
     * @return  the block's grid
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Returns the location of the block
     * @return  the block's location
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Removes the block from grid
     * @postcondition   the block is no longer in a grid,
     *                  and the grid no longer holds the block
     */
    public void removeSelfFromGrid()
    {
        grid.remove( location );
        grid = null;
        location = null;
    }

    /**
     * Puts the block into grid
     * @param gr        the block's new grid
     * @param loc       the block's new location
     * @postcondition   the block is now in a grid,
     *                  and the grid holds the block in the location
     *                  (replacing any other block that was there before)
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        grid = gr;
        location = loc;
        Block b = grid.get( location );
        if ( b != null )
            b.removeSelfFromGrid();
        grid.put( location, this );
    }

    /**
     * Moves the block to another location
     * @param newLocation   the block's new location
     * @postcondition       the block is now in a grid,
     *                      and the grid holds the block in the new location
     *                      (replacing any other block that was there before)
     */
    public void moveTo(Location newLocation)
    {
        grid.remove( location );
        location = newLocation;
        Block b = grid.get( location );
        if ( b != null )
            b.removeSelfFromGrid();
        grid.put( location, this );
        grid.put( location, this );
    }

    /**
     * Returns a string with the location and color of this block
     * @return  the string form of the block
     */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}