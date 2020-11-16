import java.util.*;
/**
 * A bounded grid (2D array) which can hold objects of type E.
 * 
 * @author Utkarsh Priyam
 * @version v1
 * 
 * @param <E> the grid holds objects of type E
 */
public class MyBoundedGrid<E>
{
    private Object[][] grid;

    /**
     * Constructor for objects of class MyBoundedGrid
     * @param r the number of rows in the grid
     * @param c the number of columns in the grid
     */
    public MyBoundedGrid( int r, int c )
    {
        grid = new Object[r][c];
    }

    /**
     * Returns the number of rows in the grid
     * @return  the number of rows in the grid
     */
    public int getNumRows()
    {
        return grid.length;
    }
    
    /**
     * Returns the number of columns in the grid
     * @return  the number of columns in the grid
     */
    public int getNumCols()
    {
        return grid[0].length;
    }
    
    /**
     * Returns whether or not the given location is in the grid
     * @param loc   the location to check
     * @return      true, if the location is valid; otherwise,
     *              false
     */
    public boolean isValid( Location loc )
    {
        int r = loc.getRow();
        int c = loc.getCol();
        return 0 <= r && r < getNumRows() && 0 <= c && c < getNumCols();
    }
    
    /**
     * Puts the object at location loc
     * @param obj   the object
     * @param loc   the location
     * @return      the object that was in that spot
     */
    public E put( Location loc, E obj )
    {
        if ( ! isValid(loc) )
            return null;
        int r = loc.getRow();
        int c = loc.getCol();
        E old = (E) grid[r][c];
        grid[r][c] = obj;
        return old;
    }
    
    /**
     * Removes any object at location loc
     * @param loc   the location
     * @return      the object that was in that spot
     */
    public E remove( Location loc )
    {
        if ( ! isValid(loc) )
            return null;
        int r = loc.getRow();
        int c = loc.getCol();
        E old = (E) grid[r][c];
        grid[r][c] = null;
        return old;
    }
    
    /**
     * Returns the object at location loc
     * @param loc   the location
     * @return      the object that is at loc
     */
    public E get( Location loc )
    {
        if ( ! isValid(loc) )
            return null;
        int r = loc.getRow();
        int c = loc.getCol();
        return (E) grid[r][c];
    }
    
    /**
     * Returns an arraylist of all occupied locations
     * @return  an arraylist of all occupied locations
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> list = new ArrayList<Location>();
        for ( int r = 0; r < grid.length; r++ )
            for ( int c = 0; c < grid[0].length; c++ )
                if ( grid[r][c] != null )
                    list.add( new Location(r,c) );
        return list;
    }
}
