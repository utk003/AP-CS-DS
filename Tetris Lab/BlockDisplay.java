import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * @author Anu Datar
 * 
 * Changed block size and added a split panel display for next block and Score
 * 
 * @author Ryan Adolf
 * @version 1.0
 * 
 * Fixed the lag issue with block rendering 
 * Removed the JPanel
 */
// Used to display the contents of a game board
public class BlockDisplay extends JComponent implements KeyListener
{
    private static final Color BACKGROUND = Color.BLACK;
    private static final Color BORDER = Color.BLACK;
    private static final Color GAME_BORDER = Color.LIGHT_GRAY;

    private static final int OUTLINE = 2;
    private static final int BLOCKSIZE = 20;

    private MyBoundedGrid<Block> board;
    private MyBoundedGrid<Block> nextUp;
    private MyBoundedGrid<Block> hold;
    private JFrame frame;
    private ArrowListener listener;
    private boolean isPaused;

    // Constructs a new display for displaying the given board
    public BlockDisplay(MyBoundedGrid<Block> board, MyBoundedGrid<Block> next, MyBoundedGrid<Block> h)
    {
        this.board = board;
        nextUp = next;
        hold = h;
        isPaused = false;

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.addKeyListener(this);

        //Display the window.
        this.setPreferredSize(new Dimension(
                BLOCKSIZE * ( board.getNumCols() + 3 + nextUp.getNumCols() ),
                BLOCKSIZE * ( board.getNumRows() + 2 )
            ));

        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        // Background
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        if ( isPaused )
            return;
        // Tile Border
        g.setColor(BORDER);
        g.fillRect(0, 0, BLOCKSIZE * ( board.getNumCols() + 2 ) + OUTLINE,
            BLOCKSIZE * ( board.getNumRows() + 2 ));
        // Section Border
        g.setColor(GAME_BORDER);
        int r = board.getNumRows(), c = board.getNumCols();
        for ( int row = 0; row < r + 2; row++ )
        {
            g.fillRect(OUTLINE/2, row * BLOCKSIZE + OUTLINE/2,
                BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            g.fillRect((c + 1) * BLOCKSIZE + OUTLINE/2, row * BLOCKSIZE + OUTLINE/2,
                BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            g.fillRect((c + 8) * BLOCKSIZE + OUTLINE/2, row * BLOCKSIZE + OUTLINE/2,
                BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
        }
        for ( int col = 0; col < c + 3 + nextUp.getNumCols(); col++ )
        {
            g.fillRect(col * BLOCKSIZE + OUTLINE/2, OUTLINE/2,
                BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            g.fillRect(col * BLOCKSIZE + OUTLINE/2, (r + 1) * BLOCKSIZE + OUTLINE/2,
                BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
        }
        for ( int col = c + 2; col < c + nextUp.getNumCols() + 2; col++ )
            g.fillRect(col * BLOCKSIZE + OUTLINE/2,
                (nextUp.getNumRows() + 1) * BLOCKSIZE + OUTLINE/2,
                BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
        // Game Board
        for (int row = 0; row < r; row++)
            for (int col = 0; col < c; col++)
            {
                Location loc = new Location(row, col);

                Block square = board.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect((col + 1) * BLOCKSIZE + OUTLINE/2, (row + 1) * BLOCKSIZE + OUTLINE/2,
                    BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            }
        // Up Next Board
        r = nextUp.getNumRows();
        c = nextUp.getNumCols();
        for (int row = 0; row < r; row++)
            for (int col = 0; col < c; col++)
            {
                Location loc = new Location(row, col);

                Block square = nextUp.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect((col + 2 + board.getNumCols()) * BLOCKSIZE + OUTLINE/2,
                    (row + 1) * BLOCKSIZE + OUTLINE/2, BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            }
        // Hold Board
        r = hold.getNumRows();
        c = hold.getNumCols();
        for (int row = 0; row < r; row++)
            for (int col = 0; col < c; col++)
            {
                Location loc = new Location(row, col);

                Block square = hold.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect((col + 2 + board.getNumCols()) * BLOCKSIZE + OUTLINE/2,
                    (row + 2 + nextUp.getNumRows()) * BLOCKSIZE + OUTLINE/2,
                    BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            }
    }

    //Redraws the board to include the pieces and border colors.
    public void showBlocks()
    {
        repaint();
    }

    // Sets the title of the window.
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        else if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
        else if (code == KeyEvent.VK_SHIFT)
            listener.shiftPressed();
        else if (code == KeyEvent.VK_P)
            listener.pPressed();
    }

    public void setArrowListener(ArrowListener listener)
    {
        this.listener = listener;
    }

    public void togglePause()
    {
        isPaused = ! isPaused;
    }
}
