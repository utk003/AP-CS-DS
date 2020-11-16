import java.awt.*;
import java.util.*;

/**
 * Is a King for the Chess game.
 * Behaves like a King does in a normal game of Chess.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class King extends Piece
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class King
     * @param col       the color of the piece
     * @param fileName  the name of the file of the piece icon
     */
    public King(Color col, String fileName)
    {
        super(col, fileName, 1000);
    }

    /**
     * Gives all possible locations to which the piece can move
     * @return  an ArrayList with all possible destinations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Location loc = getLocation();
        for ( int i = -1; i <= 1; i++ )
            for ( int j = -1; j <= 1; j++ )
            {
                Location newLoc = new Location( loc.getRow() + i, loc.getCol() + j );
                if ( ! newLoc.equals(loc) && isValidDestination(newLoc) )
                    locs.add( newLoc );
            }
        if ( canCastleKingSide() )
            locs.add( new Location( loc.getRow(), 6 ) );
        if ( canCastleQueenSide() )
            locs.add( new Location( loc.getRow(), 2 ) );
        return locs;
    }

    /**
     * Returns whether or not the king can castle kingside
     * @return  true if 0-0 is legal; otherwise,
     *          false
     */
    private boolean canCastleKingSide()
    {
        Board b = getBoard();
        boolean[] kingsMoved = b.getKingCastleState();
        boolean[][] rooksMoved = b.getRookCastleState();
        if ( getColor().equals( Color.WHITE ) )
        {
            if ( kingsMoved[0] )
                return false;
            if ( rooksMoved[1][0] )
                return false;
            ArrayList<Move> moves = b.allMovesExceptKing( Color.BLACK );
            for ( Move m: moves )
            {
                Location l = m.getDestination();
                for ( int i = 4; i <= 7; i++ )
                    if ( l.equals( new Location(7,i) ) )
                        return false;
            }
        }
        else
        {
            if ( kingsMoved[1] )
                return false;
            if ( rooksMoved[1][1] )
                return false;
            ArrayList<Move> moves = b.allMovesExceptKing( Color.WHITE );
            for ( Move m: moves )
            {
                Location l = m.getDestination();
                for ( int i = 4; i <= 7; i++ )
                    if ( l.equals( new Location(0,i) ) )
                        return false;
            }
        }
        ArrayList<Location> locs = new ArrayList<Location>();
        sweep( locs, 90 );
        return locs.size() == 2;
    }
    
    /**
     * Returns whether or not the king can castle queenside
     * @return  true if 0-0-0 is legal; otherwise,
     *          false
     */
    private boolean canCastleQueenSide()
    {
        Board b = getBoard();
        boolean[] kingsMoved = b.getKingCastleState();
        boolean[][] rooksMoved = b.getRookCastleState();
        if ( getColor().equals( Color.WHITE ) )
        {
            if ( kingsMoved[0] )
                return false;
            if ( rooksMoved[0][0] )
                return false;
            ArrayList<Move> moves = b.allMovesExceptKing( Color.BLACK );
            for ( Move m: moves )
            {
                Location l = m.getDestination();
                for ( int i = 0; i <= 4; i++ )
                    if ( l.equals( new Location(7,i) ) )
                        return false;
            }
        }
        else
        {
            if ( kingsMoved[1] )
                return false;
            if ( rooksMoved[0][1] )
                return false;
            ArrayList<Move> moves = b.allMovesExceptKing( Color.WHITE );
            for ( Move m: moves )
            {
                Location l = m.getDestination();
                for ( int i = 0; i <= 4; i++ )
                    if ( l.equals( new Location(0,i) ) )
                        return false;
            }
        }
        ArrayList<Location> locs = new ArrayList<Location>();
        sweep( locs, 270 );
        return locs.size() == 3;
    }
}
