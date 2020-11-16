import java.awt.*;
import java.util.*;

/**
 * The HumanPlayer plays the move indicated on the game board.
 * The HumanPlayer is controlled by a human (hence HumanPlayer).
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class HumanPlayer extends Player
{
    private BoardDisplay display;
    /**
     * A constructor for HumanPlayer
     *
     * @param b the player's board
     * @param n the player's name
     * @param c the player's color
     * @param d the player's game display
     */
    public HumanPlayer( Board b, String n, Color c, BoardDisplay d )
    {
        super( b, n, c );
        display = d;
    }
    
    /**
     * Returns the next move this player will make
     * @return  this player's next move
     */
    public Move nextMove()
    {
        Board b = getBoard();
        ArrayList<Move> moves = b.allMoves( getColor() );
        
        int ind = 0;
        if ( getColor().equals(Color.BLACK) )
            ind = 1;
        
        if ( checkMoves( moves ) == 0 )
        {
            int[] safety = b.evaluateGameStatus();
            if ( safety[ind] == 0 )
                throw new RuntimeException("Stalemate");
            String color = "White";
            if ( ind == 1 )
                color = "Black";
            throw new RuntimeException("Checkmate: " + color + " lost!");
        }
        
        return getNextMove( moves );
    }
    
    /**
     * Gets all inputted move from the board,
     * and uses the move if and only if the move
     * is present in the arraylist of legal moves.
     * @param moves all the legal moves
     * @return      the chosen, legal move
     */
    private Move getNextMove( ArrayList<Move> moves )
    {
        while ( true )
        {
            Move move = display.selectMove();
            display.showBoard();
            for ( Move m: moves )
                if ( move.equals(m) )
                    return move;
                else
                    display.clearColors();
        }
    }
}
