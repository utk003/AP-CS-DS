import java.awt.*;
import java.util.*;

/**
 * Is a Knight for the Chess game.
 * Behaves like a Knight does in a normal game of Chess.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Knight extends Piece
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Knight
     * @param col       the color of the piece
     * @param fileName  the name of the file of the piece icon
     */
    public Knight(Color col, String fileName)
    {
        super(col, fileName, 3);
    }

    /**
     * Gives all possible locations to which the piece can move
     * @return  an ArrayList with all possible destinations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Location loc = getLocation();
        
        Location newLoc = new Location( loc.getRow() + 1, loc.getCol() + 2 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        newLoc = new Location( loc.getRow() + 2, loc.getCol() + 1 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        
        newLoc = new Location( loc.getRow() - 1, loc.getCol() - 2 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        newLoc = new Location( loc.getRow() - 2, loc.getCol() - 1 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        
        newLoc = new Location( loc.getRow() + 1, loc.getCol() - 2 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        newLoc = new Location( loc.getRow() + 2, loc.getCol() - 1 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        
        newLoc = new Location( loc.getRow() - 1, loc.getCol() + 2 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        newLoc = new Location( loc.getRow() - 2, loc.getCol() + 1 );
        if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
            locs.add( newLoc );
        return locs;
    }
}
