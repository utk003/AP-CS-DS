import java.awt.*;
import java.util.*;

/**
 * The abstract class Player is a player that can play Chess.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public abstract class Player
{
    private Board board;
    private String name;
    private Color color;
    
    /**
     * A constructor for a Player
     *
     * @param b the player's board
     * @param n the player's name
     * @param c the player's color
     */
    public Player( Board b, String n, Color c )
    {
        board = b;
        name = n;
        color = c;
    }
    
    /**
     * Returns the player's board
     * @return  the player's board
     */
    public Board getBoard()
    {
        return board;
    }
    
    /**
     * Returns the player's name
     * @return  the player's name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the player's color
     * @return  the player's color
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * Returns the next move this player will make
     * @return  this player's next move
     */
    public abstract Move nextMove();
    
    /**
     * Checks whether each of the moves in the given
     * arraylist is valid in a normal game of chess.
     * Removes the move from the arraylist if the move puts the king in check.
     * Returns the size of the arraylist after all the moves are checked.
     * @param moves all the moves to check
     * @return      the size of the arraylist of moves after all the moves are checked.
     */
    public int checkMoves( ArrayList<Move> moves )
    {
        int ind = 0;
        if ( color.equals(Color.BLACK) )
            ind = 1;
        
        for ( int i = moves.size() - 1; i >= 0; i-- )
        {
            Move m = moves.get(i);
            board.executeMove( m );
            int[] safety = board.evaluateGameStatus();
            board.undoMove( m );
            if ( safety[ind] != 0 )
                moves.remove(i);
        }
        board.evaluateGameStatus();
        return moves.size();
    }
}
