
/**
 * The Tetris Class runs and manages the tetris game.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Tetris implements ArrowListener
{
    private MyBoundedGrid<Block> gameBoard;
    private MyBoundedGrid<Block> nextUpBoard;
    private MyBoundedGrid<Block> holdBoard;
    private BlockDisplay display;
    private Tetrad activeTetrad;
    private Tetrad tetrad1;
    private Tetrad tetrad2;
    private Tetrad tetrad3;
    private Tetrad tetradHold;
    private boolean isDead;
    private boolean isHolding;
    private int rowsCleared;
    private int level;
    private int score;
    private int fallDelay;
    private double e3;
    private double e4;
    private double e7;
    private double[] eTable;
    private boolean isPaused;

    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        gameBoard = new MyBoundedGrid(20,10);
        nextUpBoard = new MyBoundedGrid(12,6);
        holdBoard = new MyBoundedGrid(7,6);
        rowsCleared = 0;
        level = 0;
        score = 0;
        fallDelay = 1000;
        e3 = 20.0855369232;
        e4 = 54.5981500331;
        e7 = 1096.6331584285;
        eTable = new double[21];
        eTable[0] = 1;
        for ( int i = 1; i <= 20; i++ )
            eTable[i] = 1.6487212707 * eTable[i-1];
        display = new BlockDisplay( gameBoard, nextUpBoard, holdBoard );
        display.setTitle( "Tetris\tLevel: " + level + "\t   Score: " + score );
        display.setArrowListener( this );
        isDead = false;
        isHolding = false;
        activeTetrad = new Tetrad( nextUpBoard );
        activeTetrad.setBoard( gameBoard );
        tetrad1 = new Tetrad( nextUpBoard );
        tetrad1.translate(-8,0);
        tetrad2 = new Tetrad( nextUpBoard );
        tetrad2.translate(-4,0);
        tetrad3 = new Tetrad( nextUpBoard );
        display.showBlocks();
        isPaused = false;
        play();
        System.out.println("You lost on Level " + level + " with a final score of " + score + ".");
    }

    /**
     * Does the required action (counter-clockwise rotation) when the up arrow is pressed.
     */
    public void upPressed()
    {
        if ( isPaused )
            return;
        activeTetrad.rotate();
        display.showBlocks();
    }

    /**
     * Does the required action (translation down) when the down arrow is pressed.
     */
    public void downPressed()
    {
        if ( isPaused )
            return;
        activeTetrad.translate(1,0);
        display.showBlocks();
    }

    /**
     * Does the required action (translation left) when the left arrow is pressed.
     */
    public void leftPressed()
    {
        if ( isPaused )
            return;
        activeTetrad.translate(0,-1);
        display.showBlocks();
    }

    /**
     * Does the required action (translation right) when the right arrow is pressed.
     */
    public void rightPressed()
    {
        if ( isPaused )
            return;
        activeTetrad.translate(0,1);
        display.showBlocks();
    }

    /**
     * Does the required action
     * (holds the current piece and releases a new piece)
     * when the space is pressed.
     */
    public void spacePressed()
    {
        if ( isPaused )
            return;
        if ( ! isHolding )
        {
            tetradHold = activeTetrad;
            tetradHold.setBoard( holdBoard );
            tetradHold.translate(2,0);
            activeTetrad = tetrad1;
            activeTetrad.setBoard( gameBoard );
            tetrad1 = tetrad2;
            tetrad1.translate(-4,0);
            tetrad2 = tetrad3;
            tetrad2.translate(-4,0);
            tetrad3 = new Tetrad( nextUpBoard );
            isHolding = true;
        }
        else
        {
            MyBoundedGrid<Block> tempHold = new MyBoundedGrid(7,6);
            Tetrad tempTetrad;
            tempTetrad = tetradHold;
            tempTetrad.setBoard( tempHold );
            tetradHold = activeTetrad;
            tetradHold.setBoard( holdBoard );
            tetradHold.translate(2,0);
            activeTetrad = tempTetrad;
            activeTetrad.setBoard( gameBoard );
        }
    }
    
    /**
     * Does the required action
     * (sends the current piece all the way to the bottom of the board)
     * when the shift is pressed.
     */
    public void shiftPressed()
    {
        if ( isPaused )
            return;
        boolean notDone = true;
        while ( notDone )
            notDone = activeTetrad.translate(1,0);
        display.showBlocks();
    }
    
    /**
     * Does the required action (pauses the game) when "P" is pressed.
     */
    public void pPressed()
    {
        if ( isPaused )
        {
            
        }
        else
        {
            
        }
        isPaused = ! isPaused;
        display.togglePause();
    }

    /**
     * Handles the automatic dropping of the tetrads.
     * Speeds up as the game progresses to further levels.
     * @postcondition   the active tetrad is as far down as it can possibly go
     */
    private void dropTetrad()
    {
        boolean notDone = true;
        while ( notDone )
        {
            try
            {
                // Pause for 1000 milliseconds.
                Thread.sleep(fallDelay);
            }
            catch(InterruptedException e)
            {
                // Ignore
            }
            if ( ! isPaused )
                notDone = activeTetrad.translate(1,0);
            display.showBlocks();
        }
        clearCompletedRows();
        activeTetrad = tetrad1;
        activeTetrad.setBoard( gameBoard );
        tetrad1 = tetrad2;
        tetrad1.translate(-4,0);
        tetrad2 = tetrad3;
        tetrad2.translate(-4,0);
        tetrad3 = new Tetrad( nextUpBoard );
        display.showBlocks();
        while ( rowsCleared >= 10 )
        {
            level++;
            rowsCleared -= 10;
        }
        display.setTitle( "Tetris\tLevel: " + level + "\t   Score: " + score );
        if ( level > 20 )
            fallDelay = 100;
        else
            fallDelay = (int) (50 * ( 2.0 - (9.0/e3) + 9.0*(e4 + 2*e7)/(e7 + 2*eTable[level]) ));
    }

    /**
     * Runs the tetris game as long as possible.
     * Ends when the player dies.
     * @postcondition   the player is dead
     */
    public void play()
    {
        while ( ! isDead )
            try
            {
                dropTetrad();
            }
            catch ( RuntimeException e )
            {
                if ( e.getMessage() != null && e.getMessage().equals("You Are DED") )
                    isDead = true;
                else
                    e.printStackTrace();
            }            
    }

    /**
     * Returns whether or not the row at index row is completely full.
     * @param row       the index of the row to check
     * @precondition    0 <= row < number of rows
     * @return          true if every cell in the given row is occupied; otherwise,
     *                  false
     */
    private boolean isCompletedRow(int row)
    {
        for ( int c = 0; c < gameBoard.getNumCols(); c++ )
            if ( gameBoard.get( new Location(row,c) ) == null )
                return false;
        return true;
    }

    /**
     * Clears the the row at index row and shifts all the blocks above it 1 block down.
     * @param row       the index of the row to clear
     * @precondition    0 <= row < number of rows
     *                  the given row is full of blocks
     *                  ( isCompletedRow(row) returns true )
     * @postcondition   Every block in the given row has been
     *                  removed, and every block above row
     *                  has been moved down one row.
     */            
    private void clearRow(int row)
    {
        for ( int c = 0; c < gameBoard.getNumCols(); c++ )
            gameBoard.remove( new Location(row,c) );
    }

    /**
     * Goes through every row and clears all completed rows.
     * @precondition    No tetrad is currently falling
     * @postcondition   All completed rows have been cleared.
     */
    private void clearCompletedRows()
    {
        int r = gameBoard.getNumRows() - 1;
        int c = 0;
        while ( r >= 0 )
            if ( isCompletedRow(r) )
            {
                clearRow(r);
                doGravity(r-1);
                c++;
            }
            else
                r--;
        int addend = 50 * c;
        for ( int i = 1; i < c; i++ )
            addend *= i;
        score += addend;
        rowsCleared += c;
    }

    /**
     * Manages the gravity for the game.
     * This method can be modified at a later date in order to handle chain gravity.
     * @param row   the index of the row to shift down
     * @precondition    0 <= row < number of rows
     *                  the row below is completely empty
     * @postcondition   the row at index row as been shifted down by 1 block,
     *                  and all rows above this row have been shifted down by 1 as well.
     */
    private void doGravity(int row)
    {
        if ( row < 0 )
            return;
        for ( int r = row; r >= 0; r-- )
            for ( int c = 0; c < gameBoard.getNumCols(); c++ )
            {
                Block b = gameBoard.get( new Location(r,c) );
                if ( b != null )
                    b.moveTo( new Location(r+1,c) );
            }
    }
}
