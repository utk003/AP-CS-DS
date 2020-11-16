import java.awt.*;
import java.util.*;

/**
 * The SmarterPlayer does a 1 move look-ahead
 * in order to pick its best legal move option.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class SmarterPlayer extends Player
{
    /**
     * A constructor for SmarterPlayer
     *
     * @param b the player's board
     * @param n the player's name
     * @param c the player's color
     */
    public SmarterPlayer( Board b, String n, Color c )
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
        int rand = (int) ( Math.random() * moves.size() );
        Move move = moves.get( rand );
        b.executeMove(move);
        if ( score() > 200 )
        {
            b.undoMove( move );
            return move;
        }
        int max = valueOfMeanestResponse();
        b.undoMove(move);
        for ( int i = 0; i < moves.size(); i++ )
        {
            Move m = moves.get(i);
            b.executeMove( m );
            if ( score() > 200 )
            {
                b.undoMove( m );
                return m;
            }
            int score = valueOfMeanestResponse();
            if ( max < score )
            {
                max = score;
                move = m;
            }
            b.undoMove( m );
        }
        if ( max < -200 )
        {
            int ind = 0;
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
    
    /**
     * Calculates the meanest response the opponent can make.
     * @return  the score of the board after the opponent's meanest response
     */
    private int valueOfMeanestResponse()
    {
        Board b = getBoard();
        ArrayList<Move> moves = null;
        if ( getColor().equals( Color.WHITE ) )
            moves = b.allMoves( Color.BLACK );
        else
            moves = b.allMoves( Color.WHITE );
        
        int rand = (int) ( Math.random() * moves.size() );
        Move move = moves.get( rand );
        b.executeMove(move);
        int min = score();
        b.undoMove(move);
        for ( int i = 0; i < moves.size(); i++ )
        {
            Move m = moves.get(i);
            b.executeMove( m );
            int score = score();
            if ( min > score )
            {
                min = score;
                move = m;
            }
            b.undoMove( m );
        }
        return min;
    }
}
