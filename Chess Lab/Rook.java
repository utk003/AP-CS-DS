import java.awt.*;
import java.util.*;

/**
 * Is a Rook for the Chess game.
 * Behaves like a Rook does in a normal game of Chess.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Rook extends Piece
{
    // instance variables - replace the example below with your own
    private String file;
    /**
     * Constructor for objects of class Rook
     * @param col           the color of the piece
     * @param fileName      the name of the file of the piece icon
     * @param fileLetter    the letter of the file the rook started on
     */
    public Rook(Color col, String fileName, String fileLetter)
    {
        super(col, fileName, 5);
        file = fileLetter;
    }
    
    /**
     * Gives all possible locations to which the piece can move
     * @return  an ArrayList with all possible destinations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for ( int dir = 0; dir < 360; dir += 90 )
            sweep( locs, dir );
        return locs;
    }
    
    /**
     * Returns the home file of the rook
     * @return  the rook's home file
     */
    public String getHomeFile()
    {
        return file;
    }
}
