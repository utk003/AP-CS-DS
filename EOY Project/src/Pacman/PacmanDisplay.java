package Pacman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Abstract class Display - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public class PacmanDisplay extends JComponent implements KeyListener, MouseListener
{
    private static final Color BACKGROUND = Color.BLACK;
    private static final Color WALL = new Color(50, 50, 50);
    private static final Color SIDEBAR = Color.GRAY;
    
    private static final int TILESIZE = 15;

    private JFrame frame;
    private PacmanGame game;
    private BoardPlayer[] players;

    // Constructs a new display for displaying the given board
    public PacmanDisplay(PacmanGame pacman)
    {
        game = pacman;
        players = pacman.getPlayers();
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
                TILESIZE * (game.getNumCols()),
                TILESIZE * game.getNumRows()
            ));

        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0,  TILESIZE * game.getNumCols(), 
                        TILESIZE * game.getNumRows());
        
        for (int row = 0; row < game.getNumRows(); row++)
        {
            for (int col = 0; col < game.getNumCols(); col++)
            {
                BoardPiece piece = game.getBoardPiece(row, col);
                if (piece != null)
                {
                    drawGamePiece(g, piece.getImage(), TILESIZE*col, 
                                        TILESIZE*row);
                }
            }
        }
        
        for (int i = 0; i < players.length; i++)
        {
            BoardPlayer player = players[i];
            int xCoord = (int) ((TILESIZE - 1) * player.getLocation().getCol());
            int yCoord = (int) ((TILESIZE - 1) * player.getLocation().getRow());
            drawGamePlayer(g, player.getImage(), xCoord, yCoord);
        }
    }

    private void drawGamePiece(Graphics g, Image image, int x, int y)
    {
        g.drawImage(image, x, y, TILESIZE, TILESIZE, null);
    }
    
    private void drawGamePlayer(Graphics g, Image image, int x, int y)
    {
        g.drawImage(image, x, y, TILESIZE*2, TILESIZE*2, null);
    }
    
    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)
        {
            game.leftArrowPressed();
        }
        else if (key == KeyEvent.VK_RIGHT)
        {
            game.rightArrowPressed();
        }
        else if (key == KeyEvent.VK_UP)
        {
            game.upArrowPressed();
        }
        else if (key == KeyEvent.VK_DOWN)
        {
            game.downArrowPressed();
        }
    }

    public void mouseExited(MouseEvent e)
    {
        
    }
    
    public void mouseEntered(MouseEvent e)
    {
    }
    
    public void mouseReleased(MouseEvent e)
    {
    }
    
    public void mousePressed(MouseEvent e)
    {
    }
    
    public void mouseClicked(MouseEvent e)
    {
    }
}
