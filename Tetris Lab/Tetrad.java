import java.awt.*;
import java.util.concurrent.Semaphore;
/**
 * The tetrad class represents 1 tetrad in the tetris game.
 * A tetrad is a group of 4 blocks in a specific orientation
 * that remembers where the blocks are on the grid and what color they are.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Tetrad
{
    private Block[] blocks;
    private MyBoundedGrid<Block> board;
    private int[][] startCoords;
    private int[][][] offsets;
    private Color[] colors;
    private Semaphore lock;
    private boolean inPlay;

    /**
     * Constructor for objects of class Tetrad
     * @param b The board the tetrad will start off on.
     */
    public Tetrad( MyBoundedGrid<Block> b )
    {
        inPlay = false;
        lock = new Semaphore(1,true);
        blocks = new Block[4];
        for ( int i = 0; i < blocks.length; i++ )
            blocks[i] = new Block();
        offsets = new int[][][]{
            { {0,0}, {-1,0}, {1,0}, {2,0} },
            { {0,0}, {1,0}, {0,1} ,{0,-1} },
            { {0,0}, {1,0}, {0,1} ,{1,1} },
            { {0,0}, {-1,0}, {1,0} ,{1,1} },
            { {0,0}, {-1,0}, {1,0} ,{1,-1} },
            { {0,0}, {1,0}, {1,-1} ,{0,1} },
            { {0,0}, {1,0}, {1,1} ,{0,-1} }
        };
        colors = new Color[]{Color.RED, new Color(80,80,80), Color.CYAN, Color.YELLOW,
            Color.MAGENTA, new Color(0,50,255), Color.GREEN};
        int rand = (int) (7 * Math.random());
        placeTetrad( b, rand );
        if ( rand == 0 || rand == 3 || rand == 4 )
            rotate();
        if ( rand == 0 )
            translate(-1,1);
        if ( rand == 3 )
            translate(-1,0);
        if ( rand == 4 || rand == 5 )
            translate(0,-1);
        translate(1,0);
    }

    /**
     * Helper method for Tetrad constructor
     * @param b     The board the tetrad will be on
     * @param color The color of the tetrad
     */
    public void placeTetrad( MyBoundedGrid<Block> b, int color )
    {
        if ( color < 0 || color > 6 )
            throw new IllegalArgumentException("There are only 7 types of tetrads");
        int midCol = (b.getNumCols() - 1) / 2;
        startCoords = new int[][] {
            { 1, midCol },
            { 0, midCol },
            { 0, midCol },
            { 1, midCol },
            { 1, midCol+1 },
            { 0, midCol+1 },
            { 0, midCol }
        };
        board = b;
        Location[] locs = new Location[blocks.length];
        int r, c;
        for ( int i = 0; i < blocks.length; i++ )
        {
            r = startCoords[color][0] + offsets[color][i][0];
            c = startCoords[color][1] + offsets[color][i][1];
            if ( ! inPlay )
                r += 8;
            locs[i] = new Location(r,c);
            blocks[i].setColor( colors[color] );
        }

        if ( ! areEmpty(b, locs) )
            throw new RuntimeException("You Are DED");
        addToLocations( b, locs );
    }

    /**
     * Adds the blocks of the tetrad to the given grid at the given locations.
     * @param grid      The grid to which the blocks will be added
     * @param locs      The locations where the blocks will be added
     * @precondition    Blocks are not in any grid;
     *                  locs.length = 4.
     * @postcondition   The locations of blocks match locs,
     *                  and blocks have been put in the grid.
     */
    private void addToLocations(MyBoundedGrid<Block> grid, Location[] locs)
    {
        for ( int i = 0; i < 4; i++ )
            blocks[i].putSelfInGrid( grid, locs[i] );
    }

    /**
     * Removes the blocks from their grid and returns their original locations.
     * @precondition    The blocks are in the grid.
     * @postcondition   The blocks have been removed from grid.
     * @return          The blocks' old locations
     */
    private Location[] removeBlocks()
    {
        Location[] locs = new Location[4];
        for ( int i = 0; i < locs.length; i++ )
        {
            locs[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return locs;
    }

    /**
     * Returns whether or not the given locations
     * are empty on the given grid.
     * @param grid      The grid to check
     * @param locs      The locations to check
     * @return          true if the given locations are all valid
     *                  and empty locations in grid; otherwise,
     *                  false
     */
    private boolean areEmpty(MyBoundedGrid<Block> grid,
    Location[] locs)
    {
        for ( Location loc: locs )
            if ( ! grid.isValid( loc ) || grid.get( loc ) != null )
                return false;
        return true;
    }

    /**
     * Shifts the blocks of the tetrad down deltaRow rows and
     * to the right deltaCol columns if those locations are valid and empty.
     * Returns true if the translation was successful, and false otherwise.
     * @param deltaRow  The number of rows to shift (down is positive)
     * @param deltaCol  The number of columns to shift (right is positive)
     * @postcondition   Attempts to move this tetrad deltaRow
     *                  rows down and deltaCol columns to the
     *                  right, if those positions are valid
     *                  and empty
     * @return          true if the move was successful; otherwise,
     *                  false
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        try
        {
            lock.acquire();
            // your lousy code here
            Location[] oldLocs = removeBlocks();
            Location[] newLocs = new Location[oldLocs.length];
            int r, c;
            for ( int i = 0; i < oldLocs.length; i++ )
            {
                r = oldLocs[i].getRow() + deltaRow;
                c = oldLocs[i].getCol() + deltaCol;
                newLocs[i] = new Location(r, c);
            }
            boolean b = areEmpty( board, newLocs );
            if ( b )
                addToLocations( board, newLocs );
            else
                addToLocations( board, oldLocs );
            return b;
        }
        catch (InterruptedException e)
        {
            // did not modify the tetrad
            return false;
        }
        catch (NullPointerException e)
        {
            // Do nothing - only happens if you try to move when you die
            return false;
        }
        finally
        {
            lock.release();
        }
    }

    /**
     * Rotates the tetrad 90 degrees counter-clockwise around blocks[0].
     * Returns true if the rotation was successful, and false otherwise.
     * @postcondition   Attempts to rotate this tetrad
     *                  90 degrees counter-clockwise
     *                  around blocks[0], if
     *                  those positions are
     *                  valid and empty
     * @return          true if the rotation was succesful; otherwise,
     *                  false
     */
    public boolean rotate()
    {
        try
        {
            lock.acquire();
            Location[] oldLocs = removeBlocks();
            Location[] newLocs = new Location[oldLocs.length];
            int r, c;
            for ( int i = 0; i < oldLocs.length; i++ )
            {
                r = oldLocs[0].getRow() - oldLocs[0].getCol() + oldLocs[i].getCol();
                c = oldLocs[0].getRow() + oldLocs[0].getCol() - oldLocs[i].getRow();
                newLocs[i] = new Location(r, c);
            }
            boolean b = areEmpty( board, newLocs );
            if ( b )
                addToLocations( board, newLocs );
            else
                addToLocations( board, oldLocs );
            return b;
        }
        catch (InterruptedException e)
        {
            // did not modify the tetrad
            return false;
        }
        catch (NullPointerException e)
        {
            // Do nothing - only happens if you try to rotate when you die
            return false;
        }
        finally
        {
            lock.release();
        }
    }

    /**
     * Sets the baord of the current tetrad to b.
     * Is used to shift the tetrads
     * from the up next board to the game board,
     * from the game board to the hold board, and
     * from the hold baord to the game board.
     * @param b         The new board for the tetrad
     * @precondition    The new board has enough space
     *                  to accomodate the the tetrad.
     * @postcondition   Places the tetrad in the new board
     */
    public void setBoard( MyBoundedGrid<Block> b )
    {
        try
        {
            lock.acquire();
            inPlay = true;
            for ( int i = 0; i < colors.length; i++ )
                if ( blocks[0].getColor().equals( colors[i] ) )
                {
                    removeBlocks();
                    placeTetrad( b, i );
                    return;
                }
        }
        catch (InterruptedException e)
        {
            // Do nothing
        }
        finally
        {
            lock.release();
        }
    }
}
