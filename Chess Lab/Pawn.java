import java.awt.*;
import java.util.*;

/**
 * Is a Pawn for the Chess game.
 * Behaves like a Pawn does in a normal game of Chess.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Pawn extends Piece
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Pawn
     * @param col       the color of the piece
     * @param fileName  the name of the file of the piece icon
     */
    public Pawn(Color col, String fileName)
    {
        super(col, fileName, 1);
    }

    /**
     * Gives all possible locations to which the piece can move
     * @return  an ArrayList with all possible destinations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Location loc = getLocation();
        int rowShift = 1;
        if ( getColor().equals( Color.WHITE ) )
            rowShift = -1;
        for ( int i = -1; i <= 1; i++ )
        {
            Location newLoc = new Location( loc.getRow() + rowShift, loc.getCol() + i );
            if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
                locs.add( newLoc );
        }
        if ( getColor().equals( Color.WHITE ) && loc.getRow() == 6 ||
             getColor().equals( Color.BLACK ) && loc.getRow() == 1 )
        {
            Location newLoc = new Location( loc.getRow() + rowShift, loc.getCol() );
            if ( isValidDestination(newLoc) )
            {
                newLoc = new Location( loc.getRow() + 2 * rowShift, loc.getCol() );
                if ( isValidDestination(newLoc) )
                    locs.add( newLoc );
            }
        }
        return locs;
    }

    /**
     * Checks if the given destination is valid or not
     * (ie on the board and empty, or on the board and with an opposite color piece).
     * Adds functionality for pawn's diagonal capture.
     * 
     * @param dest  the location to check
     * @return      true if the location is valid; otherwise,
     *              false
     */
    @Override
    public boolean isValidDestination(Location dest)
    {
        int deltaCol = dest.getCol() - getLocation().getCol();
        Board board = getBoard();
        if ( ! board.isValid(dest) )
            return false;
        Piece p = board.get(dest);
        if ( p == null )
            return deltaCol == 0;
        return deltaCol != 0 && ! p.getColor().equals( getColor() );
    }
}
