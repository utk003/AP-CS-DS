import java.awt.*;
import java.util.*;

/**
 * Is a Bishop for the Chess game.
 * Behaves like a Bishop does in a normal game of Chess.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Bishop extends Piece
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Bishop
     * @param col       the color of the piece
     * @param fileName  the name of the file of the piece icon
     */
    public Bishop(Color col, String fileName)
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
        for ( int dir = 45; dir < 360; dir += 90 )
            sweep( locs, dir );
        return locs;
    }
}
