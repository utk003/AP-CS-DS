import java.awt.*;
import java.util.*;

/**
 * The SmartestPlayer can do an any move look-ahead
 * in order to pick its best legal move option.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class SmartestPlayer extends Player
{
    private int maxDepth;
    
    /**
     * A constructor for SmartestPlayer
     *
     * @param b the player's board
     * @param n the player's name
     * @param c the player's color
     */
    public SmartestPlayer( Board b, String n, Color c )
    {
        super( b, n, c );
        maxDepth = 2;
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
        
        int safety;
        if ( getColor().equals(Color.WHITE) )
            safety = b.getGameStatus()[0];
        else
            safety = b.getGameStatus()[1];
        
        Move move = moves.get(rand);
        b.executeMove( move );
        int max = valueOfMeanestResponse( maxDepth );
        b.undoMove( move );
        
        for ( int i = 0; i < moves.size(); i++ )
        {
            Move m = moves.get(i);
            b.executeMove( m );
            int score = valueOfMeanestResponse( maxDepth );
            if ( max < score )
            {
                max = score;
                move = m;
            }
            b.undoMove( m );
        }
        if ( max > -200 )
            return move;
        if ( safety == 0 )
            throw new RuntimeException("Stalemate");
        String results;
        if ( getColor().equals(Color.WHITE) )
            results = "0-1";
        else
            results = "1-0";
        throw new RuntimeException(results);
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
     * Calculates the best response this can make
     * @param depth the depth of the score check
     * @return      the score of the board after the best possible move
     */
    private int bestMove( int depth )
    {
        Board b = getBoard();
        ArrayList<Move> moves = b.allMoves( getColor() );
        int max = -10000;
        for ( int i = 0; i < moves.size(); i++ )
        {
            Move m = moves.get(i);
            b.executeMove( m );
            if ( score() > 200 )
            {
                b.undoMove( m );
                return 1000;
            }
            int score = valueOfMeanestResponse( depth );
            if ( max < score )
                max = score;
            b.undoMove( m );
        }
        return max;
    }
    
    /**
     * Calculates the meanest response the opponent can make.
     * @param depth the depth of the score check
     * @return      the score of the board after the opponent's meanest response
     */
    private int valueOfMeanestResponse( int depth )
    {
        Board b = getBoard();
        ArrayList<Move> moves = null;
        if ( getColor().equals( Color.WHITE ) )
            moves = b.allMoves( Color.BLACK );
        else
            moves = b.allMoves( Color.WHITE );
        
        int min = 10000;
        for ( int i = 0; i < moves.size(); i++ )
        {
            Move m = moves.get(i);
            b.executeMove( m );
            int score = score();
            if ( score < -200 )
            {
                b.undoMove( m );
                return -1000;
            }
            if ( depth > 1 )
                score = bestMove( depth - 1 );
            if ( min > score )
                min = score;
            b.undoMove( m );
        }
        return min;
    }
}
