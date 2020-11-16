import java.awt.*;
import java.util.*;

/**
 * Is a Queen for the Chess game.
 * Behaves like a Queen does in a normal game of Chess.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Queen extends Piece
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Queen
     * @param col       the color of the piece
     * @param fileName  the name of the file of the piece icon
     */
    public Queen(Color col, String fileName)
    {
        super(col, fileName, 9);
    }
    
    /**
     * Gives all possible locations to which the piece can move
     * @return  an ArrayList with all possible destinations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for ( int dir = 0; dir < 360; dir += 45 )
            sweep( locs, dir );
        return locs;
    }
}
