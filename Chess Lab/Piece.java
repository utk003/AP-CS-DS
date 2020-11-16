import java.awt.*;
import java.util.*;

/**
 * The Piece abstract class is the basis
 * for all of the pieces of the chess game.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public abstract class Piece
{
    //the board this piece is on
    private Board board;

    //the location of this piece on the board
    private Location location;

    //the color of the piece
    private Color color;

    //the file used to display this piece
    private String imageFileName;

    //the approximate value of this piece in a game of chess
    private int value;

    /**
     * Checks if the given destination is valid or not
     * (ie on the board and empty, or on the board and with an opposite color piece).
     * 
     * @param dest  the location to check
     * @return      true if the location is valid; otherwise,
     *              false
     */
    public boolean isValidDestination(Location dest)
    {
        if ( ! board.isValid(dest) )
            return false;
        Piece p = board.get(dest);
        if ( p == null )
            return true;
        return ! p.getColor().equals(color);
    }

    /**
     * Gives all possible locations to which the piece can move
     * @return  an ArrayList with all possible destinations
     */
    public abstract ArrayList<Location> destinations();

    /**
     * Sweeps all squares in a certain direction to check where the piece can move
     * @param dests     an ArrayList with the already found possible destinations
     * @param direction the direction to sweep in
     * @postcondition   all the locations in the sweeped location where the piece can go
     */
    public void sweep(ArrayList<Location> dests, int direction)
    {
        Location loc = location.getAdjacentLocation(direction);
        while ( board.isValid(loc) && board.get(loc) == null )
        {
            dests.add(loc);
            loc = loc.getAdjacentLocation(direction);
        }
        if ( isValidDestination(loc) )
            dests.add(loc);
    }

    //constructs a new Piece with the given attributes.
    /**
     * Constructs a Piece with the given color, name, and value
     * @param col       the color of the piece
     * @param fileName  the name of the file of the piece icon
     * @param val       the value of the piece
     */
    public Piece(Color col, String fileName, int val)
    {
        color = col;
        imageFileName = fileName;
        value = val;
    }

    //returns the board this piece is on
    /**
     * Returns the board of the piece
     * @return  the piece's board
     */
    public Board getBoard()
    {
        return board;
    }

    //returns the location of this piece on the board
    /**
     * Returns the location of the piece
     * @return  the piece's location
     */
    public Location getLocation()
    {
        return location;
    }

    //returns the color of this piece
    /**
     * Returns the color of the piece
     * @return  the piece's color
     */
    public Color getColor()
    {
        return color;
    }

    //returns the name of the file used to display this piece
    /**
     * Returns the file name of the piece
     * @return  the piece's file name
     */
    public String getImageFileName()
    {
        return imageFileName;
    }

    //returns a number representing the relative value of this piece
    /**
     * Returns the value of the piece
     * @return  the piece's value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Puts this piece into a board. If there is another piece at the given
     * location, it is removed. <br />
     * Precondition: (1) This piece is not contained in a grid (2)
     * <code>loc</code> is valid in <code>gr</code>
     * @param brd the board into which this piece should be placed
     * @param loc the location into which the piece should be placed
     */
    public void putSelfInGrid(Board brd, Location loc)
    {
        if (board != null)
            throw new IllegalStateException(
                "This piece is already contained in a board.");

        Piece piece = brd.get(loc);
        if (piece != null)
            piece.removeSelfFromGrid();
        brd.put(loc, this);
        board = brd;
        location = loc;
    }

    /**
     * Removes this piece from its board. <br />
     * Precondition: This piece is contained in a board
     */
    public void removeSelfFromGrid()
    {
        if (board == null)
            throw new IllegalStateException(
                "This piece is not contained in a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                "The board contains a different piece at location "
                + location + ".");

        board.remove(location);
        board = null;
        location = null;
    }

    /**
     * Moves this piece to a new location. If there is another piece at the
     * given location, it is removed. <br />
     * Precondition: (1) This piece is contained in a grid (2)
     * <code>newLocation</code> is valid in the grid of this piece
     * @param newLocation the new location
     */
    public void moveTo(Location newLocation)
    {
        if (board == null)
            throw new IllegalStateException("This piece is not on a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                "The board contains a different piece at location "
                + location + ".");
        if (!board.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                + " is not valid.");

        if (newLocation.equals(location))
            return;
        board.remove(location);
        Piece other = board.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        board.put(location, this);
    }
}