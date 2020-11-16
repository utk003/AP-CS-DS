import java.awt.*;
import java.util.*;

/**
 * The SmartPlayer does a 1/2 move look-ahead
 * in order to pick its best legal move option.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class SmartPlayer extends Player
{
    /**
     * A constructor for SmartPlayer
     *
     * @param b the player's board
     * @param n the player's name
     * @param c the player's color
     */
    public SmartPlayer( Board b, String n, Color c )
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
        Move move = moves.get( rand );
        b.executeMove(move);
        int max = score();
        b.undoMove(move);
        for ( int i = 0; i < moves.size(); i++ )
        {
            Move m = moves.get(i);
            b.executeMove( m );
            int score = score();
            if ( max < score )
            {
                max = score;
                move = m;
            }
            b.undoMove( m );
        }
        if ( max < -200 )
        {
            ind = 0;
            String color = "White";
            if ( getColor().equals(Color.BLACK) )
            {
                ind = 1;
                color = "Black";
            }
            
            int[] safety = b.evaluateGameStatus();
            if ( safety[ind] == 0 )
                throw new RuntimeException("Stalemate");
            
            throw new RuntimeException("Checkmate: " + color + " lost!");
        }
        return move;
    }
    
    /**
     * Scores a game position to determine how good the position is.
     * @return  the position's score
     */
    private int score()
    {
        Board b = getBoard();
        ArrayList<Location> locs = b.getOccupiedLocations();
        int score = 0;
        for ( Location l: locs )
            if ( b.get(l).getColor().equals( getColor() ) )
                score += b.get(l).getValue();
            else
                score -= b.get(l).getValue();
        return score;
    }
}
