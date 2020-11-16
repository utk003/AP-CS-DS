import java.awt.*;
import java.util.*;

/**
 * The RandomPlayer randomly plays moves
 * from a collection of all its legal options.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class RandomPlayer extends Player
{
    /**
     * A constructor for RandomPlayer
     *
     * @param b the player's board
     * @param n the player's name
     * @param c the player's color
     */
    public RandomPlayer( Board b, String n, Color c )
    {
        super( b, n, c );
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
        
        int rand = (int) ( Math.random() * moves.size() );
        return moves.get( rand );
    }
}
