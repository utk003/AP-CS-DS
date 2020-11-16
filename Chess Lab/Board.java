import java.awt.*;
import java.util.*;

// Represesents a rectangular game board, containing Piece objects.
public class Board extends BoundedGrid<Piece>
{
    private BoardDisplay display;
    
    private boolean[] kingMoved; // White, Black
    private boolean[] kingMovedOnlyOnce; // White, Black
    
    private boolean[] rookAMoved; // White, Black
    private boolean[] rookAMovedOnlyOnce; // White, Black
    
    private boolean[] rookHMoved; // White, Black
    private boolean[] rookHMovedOnlyOnce; // White, Black
    // Constructs a new Board with the given dimensions
    public Board()
    {
        super(8, 8);
        kingMoved = new boolean[] {false, false};
        kingMovedOnlyOnce = new boolean[] {true, true};
        
        rookAMoved = new boolean[] {false, false};
        rookAMovedOnlyOnce = new boolean[] {true, true};
        
        rookHMoved = new boolean[] {false, false};
        rookHMovedOnlyOnce = new boolean[] {true, true};
        
        kingSafety = new int[2];
    }

    // Precondition:  move has already been made on the board
    // Postcondition: piece has moved back to its source,
    //                and any captured piece is returned to its location
    public void undoMove(Move move)
    {
        Piece piece = move.getPiece();
        Location source = move.getSource();
        Location dest = move.getDestination();
        Piece victim = move.getVictim();
        
        if ( piece instanceof Pawn )
        {
            if ( piece.getColor().equals( Color.WHITE ) && dest.getRow() == 0 || 
                 piece.getColor().equals( Color.BLACK ) && dest.getRow() == 7 )
                piece.putSelfInGrid( this, dest );
        }
        
        piece.moveTo(source);

        if (victim != null)
            victim.putSelfInGrid(piece.getBoard(), dest);
        
        // display.showBoard();
        
        if ( piece instanceof King )
        {
            int diff = move.getDestination().getCol() - move.getSource().getCol();
            if ( Math.abs( diff ) == 2 )
            {
                int r = piece.getLocation().getRow();
                if ( diff > 0 )
                    get( new Location(r,5) ).moveTo( new Location(r,7) );
                else
                    get( new Location(r,3) ).moveTo( new Location(r,0) );
            }
        }
        
        if ( piece instanceof King )
            if ( piece.getColor().equals( Color.WHITE ) )
            {
                if ( kingMovedOnlyOnce[0] )
                    kingMoved[0] = false;
            }
            else
            {
                if ( kingMovedOnlyOnce[1] )
                    kingMoved[1] = false;
            }
        
        if ( piece instanceof Rook )
            if ( ((Rook) piece).getHomeFile().equals("A") )
            {
                if ( piece.getColor().equals( Color.WHITE ) )
                {
                    if ( rookAMovedOnlyOnce[0] )
                        rookAMoved[0] = false;
                }
                else
                {
                    if ( rookAMovedOnlyOnce[1] )
                        rookAMoved[1] = false;
                }
            }
            else
            {
                if ( piece.getColor().equals( Color.WHITE ) )
                {
                    if ( rookHMovedOnlyOnce[0] )
                        rookHMoved[0] = false;
                }
                else
                {
                    if ( rookHMovedOnlyOnce[1] )
                        rookHMoved[1] = false;
                }
            }
    }

    /**
     * 
     */
    public ArrayList<Move> allMoves(Color color)
    {
        ArrayList<Location> locs = getOccupiedLocations();
        ArrayList<Move> moves = new ArrayList<Move>();
        for ( Location l: locs )
        {
            Piece p = get(l);
            if ( p.getColor().equals(color) )
            {
                ArrayList<Location> dests = p.destinations();
                for ( Location d: dests )
                    moves.add( new Move(p,d) );
            }
        }
        return moves;
    }
    
    /**
     * 
     */
    public ArrayList<Move> allMovesExceptKing(Color color)
    {
        ArrayList<Location> locs = getOccupiedLocations();
        ArrayList<Move> moves = new ArrayList<Move>();
        for ( Location l: locs )
        {
            Piece p = get(l);
            if ( ! (p instanceof King) && p.getColor().equals(color) )
            {
                ArrayList<Location> dests = p.destinations();
                for ( Location d: dests )
                    moves.add( new Move(p,d) );
            }
        }
        return moves;
    }
    
    /**
     * 
     */
    public void executeMove(Move move)
    {
        Piece p = move.getPiece();
        Piece v = move.getVictim();
        Location src = move.getSource();
        Location dest = move.getDestination();
        
        if ( p instanceof Pawn )
        {
            if ( p.getColor().equals( Color.WHITE ) && dest.getRow() == 0 || 
                 p.getColor().equals( Color.BLACK ) && dest.getRow() == 7 )
            {
                String color;
                if ( p.getColor().equals( Color.WHITE ) )
                    color = "white_queen.gif";
                else
                    color = "black_queen.gif";
                p = new Queen( p.getColor(), color );
                p.putSelfInGrid( this, src );
            }
        }
        /*
        if ( v != null )
            v.removeSelfFromGrid();
        */
        p.moveTo( dest );
        
        // display.showBoard();
        
        if ( p instanceof King )
        {
            int diff = move.getDestination().getCol() - move.getSource().getCol();
            if ( Math.abs( diff ) == 2 )
            {
                int r = p.getLocation().getRow();
                if ( diff > 0 )
                    get( new Location(r,7) ).moveTo( new Location(r,5) );
                else
                    get( new Location(r,0) ).moveTo( new Location(r,3) );
            }
        }
        
        if ( p instanceof King )
            if ( p.getColor().equals( Color.WHITE ) )
            {
                if ( kingMoved[0] )
                    kingMovedOnlyOnce[0] = false;
                else
                    kingMoved[0] = true;
            }
            else
            {
                if ( kingMoved[1] )
                    kingMovedOnlyOnce[1] = false;
                else
                    kingMoved[1] = true;
            }
        if ( p instanceof Rook )
            if ( ((Rook) p).getHomeFile().equals("A") )
            {
                if ( p.getColor().equals( Color.WHITE ) )
                {
                    if ( rookAMoved[0] )
                        rookAMovedOnlyOnce[0] = false;
                    else
                        rookAMoved[0] = true;
                }
                else
                {
                    if ( rookAMoved[1] )
                        rookAMovedOnlyOnce[1] = false;
                    else
                        rookAMoved[1] = true;
                }
            }
            else
            {
                if ( p.getColor().equals( Color.WHITE ) )
                {
                    if ( rookHMoved[0] )
                        rookHMovedOnlyOnce[0] = false;
                    else
                        rookHMoved[0] = true;
                }
                else
                {
                    if ( rookHMoved[1] )
                        rookHMovedOnlyOnce[1] = false;
                    else
                        rookHMoved[1] = true;    
                }
            }
    }
    
    /**
     * 
     */
    public void setDisplay(BoardDisplay d)
    {
        display = d;
    }
    
    /**
     * 
     */
    public boolean[] getKingCastleState()
    {
        return kingMoved;
    }
    
    /**
     * 
     */
    public boolean[][] getRookCastleState()
    {
        return new boolean[][] { rookAMoved, rookHMoved };
    }
    
    private int[] kingSafety;
    
    /**
     * Returns the king's safety score (same as evaluateGameStatus() method)
     */
    public int[] getGameStatus()
    {
        return new int[] { kingSafety[0], kingSafety[1] };
    }
    
    /**
     * Returns 0 if the king is safe (no check)
     * Returns 1 if the king is in check
     * Returns 2 if the king is in double check
     * Returns 3 or more if the game broke (ie someone broke a rule)
     */
    public int[] evaluateGameStatus()
    {
        ArrayList<Location> locs = getOccupiedLocations();
        Location[] kings = { null, null };
        for ( Location l: locs )
            if ( (get(l) instanceof King) )
            {
                if ( get(l).getColor().equals( Color.WHITE ) )
                    kings[0] = l;
                else
                    kings[1] = l;
            }
        if ( kings[0] == null && kings[1] == null )
            throw new RuntimeException("Both Kings Are DED");
        if ( kings[0] == null )
            throw new RuntimeException("White King Is DED");
        if ( kings[1] == null )
            throw new RuntimeException("Black King Is DED");
        for ( int i = 0; i < 2; i++ )
            kingSafety[i] = getKingSafetyStatus( kings[i] );
        return getGameStatus();
    }
    
    /**
     * 
     */
    private int getKingSafetyStatus( Location k )
    {
        Color col = get(k).getColor();
        int r = k.getRow();
        int c = k.getCol();
        ArrayList<Location> locs = new ArrayList<Location>(40);
        for ( int i = 0; i < 360; i += 45 )
            sweep( k, locs, i );
        int[] multiplier = {-1,1};
        for ( int m1: multiplier )
            for ( int m2: multiplier )
                for ( int i = 1; i <= 2; i++ )
                {
                    Location l = new Location( r + m1 * i, c + m2 * (3 - i) );
                    if ( isValid(l) )
                    {
                        Piece p = get(l);
                        if ( p != null )
                            if ( ! p.getColor().equals(col) )
                                if ( p instanceof Knight )
                                    locs.add(l);
                    }
                }
        int dir = 1;
        if ( col.equals(Color.WHITE) )
            dir = -1;
        for ( int m: multiplier )
        {
            Location l = new Location( r + dir, c + m );
            if ( isValid(l) )
            {
                Piece p = get(l);
                if ( p instanceof Pawn && ! p.getColor().equals(col) )
                    locs.add(l);
            }
        }
        return locs.size();
    }
    
    /**
     * 
     */
    private void sweep(Location l, ArrayList<Location> dests, int direction)
    {
        Location loc = l.getAdjacentLocation(direction);
        while ( isValid(loc) && get(loc) == null )
            loc = loc.getAdjacentLocation(direction);
        if ( ! isValid(loc) )
            return;
        Piece p = get(loc);
        if ( ! p.getColor().equals(get(l).getColor()) )
        {
            if ( direction % 90 == 0 )
                if ( p instanceof Rook || p instanceof Queen )
                    dests.add(loc);
            if ( direction % 90 == 45 )
                if ( p instanceof Bishop || p instanceof Queen )
                    dests.add(loc);
        }
    }
}