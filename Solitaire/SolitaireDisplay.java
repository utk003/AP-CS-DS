import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class SolitaireDisplay extends JComponent implements MouseListener
{
    private static final int CARD_WIDTH = 73;
    private static final int CARD_HEIGHT = 97;
    private static final int SPACING = 5;  //distance between cards
    private static final int FACE_UP_OFFSET = 15;  //distance for cascading face-up cards
    private static final int FACE_DOWN_OFFSET = 5;  //distance for cascading face-down cards
    private static final int SIDE_PANEL_WIDTH = 90; // wigth of the side panel
    private static final int RESET_CONF_PANEL_WIDTH = 250;
    private static final int RESET_CONF_PANEL_HEIGHT = 150;

    private JFrame frame;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private int selectedWaste = 0;
    private Solitaire game;
    private boolean isCheckingForRestart = false;

    public SolitaireDisplay(Solitaire game)
    {
        this.game = game;

        frame = new JFrame("Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);

        this.setPreferredSize(new Dimension(CARD_WIDTH * 7 + SPACING * 10 + SIDE_PANEL_WIDTH, CARD_HEIGHT * 2 + SPACING * 3 + FACE_DOWN_OFFSET * 7 + 13 * FACE_UP_OFFSET));
        this.addMouseListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        int x;
        int y;
        
        //background
        g.setColor(new Color(0, 128, 0));
        g.fillRect(0, 0, getWidth() - SIDE_PANEL_WIDTH, getHeight());
        g.setColor(new Color(128, 0, 0));
        g.fillRect(0, getHeight()/2 + 2 * CARD_HEIGHT, getWidth(), getHeight());
        g.setColor(new Color(0, 0, 128));
        g.fillRect(getWidth() - SIDE_PANEL_WIDTH, 0, getWidth(), getHeight());

        //face down - stock
        drawCard(g, game.getStockCard(), SPACING, SPACING);

        //waste
        Stack<Card> waste = game.getWaste();
        Card wasteCard1 = null, wasteCard2 = null, wasteCard3 = null;
        if ( ! waste.isEmpty() )
        {
            wasteCard1 = waste.pop();
            if ( ! waste.isEmpty() )
            {
                wasteCard2 = waste.pop();
                if ( ! waste.isEmpty() )
                    wasteCard3 = waste.peek();
                waste.push( wasteCard2 );
            }
            waste.push( wasteCard1 );
        }
        
        if ( wasteCard3 != null )
        {
            drawCard(g, wasteCard3, SPACING * 3 + CARD_WIDTH * 2, SPACING);
            if ( selectedRow == 0 && selectedCol == 1 && selectedWaste == 3 )
                drawBorder(g, SPACING * 3 + CARD_WIDTH * 2, SPACING);
        }
        if ( wasteCard2 != null )
        {
            drawCard(g, wasteCard2, (int) (SPACING * 2.5 + CARD_WIDTH * 1.5), SPACING);
            if ( selectedRow == 0 && selectedCol == 1 && selectedWaste == 2 )
                drawBorder(g, (int) (SPACING * 2.5 + CARD_WIDTH * 1.5), SPACING);
        }
        drawCard(g, wasteCard1, SPACING * 2 + CARD_WIDTH, SPACING);
        if ( selectedRow == 0 && selectedCol == 1 && selectedWaste == 1 )
            drawBorder(g, SPACING * 2 + CARD_WIDTH, SPACING);
        
        
        //foundations
        for (int i = 0; i < 4; i++)
            drawCard(g, game.getFoundationCard(i), SPACING * (4 + i) + CARD_WIDTH * (3 + i), SPACING);
        if ( isFoundationSelected() )
            drawBorder(g, SPACING + selectedCol *(SPACING + CARD_WIDTH), SPACING);

        //piles
        for (int i = 0; i < 7; i++)
        {
            Stack<Card> pile = game.getPile(i);
            int offset = 0;
            for (int j = 0; j < pile.size(); j++)
            {
                drawCard(g, pile.get(j), SPACING + (CARD_WIDTH + SPACING) * i, CARD_HEIGHT + 2 * SPACING + offset);
                if (selectedRow == 1 && selectedCol == i && j == pile.size() - 1)
                    drawBorder(g, SPACING + (CARD_WIDTH + SPACING) * i, CARD_HEIGHT + 2 * SPACING + offset);

                if (pile.get(j).isFaceUp())
                    offset += FACE_UP_OFFSET;
                else
                    offset += FACE_DOWN_OFFSET;
            }
        }
        
        //undo button
        Image image = new ImageIcon("cards/undoButton.gif").getImage();
        x = (int) (CARD_WIDTH * 6.5 + SIDE_PANEL_WIDTH * 0.5) + SPACING * 10;
        g.drawImage(image, x, SPACING, CARD_WIDTH, CARD_HEIGHT, null);
        
        //restart button
        image = new ImageIcon("cards/restartButton.gif").getImage();
        g.drawImage(image, x, CARD_HEIGHT + SPACING * 2, CARD_WIDTH, CARD_HEIGHT, null);
        
        //"automatically send all possible cards to foundations" button
        image = new ImageIcon("cards/sendToFoundations.gif").getImage();
        g.drawImage(image, x, CARD_HEIGHT * 2 + SPACING * 3, CARD_WIDTH, CARD_HEIGHT, null);
        
        //autocomplete button
        if ( game.isAllRevealed() )
            image = new ImageIcon("cards/autoCompleteButtonActive.gif").getImage();
        else
            image = new ImageIcon("cards/autoCompleteButtonInactive.gif").getImage();
        g.drawImage(image, x, CARD_HEIGHT * 3 + SPACING * 4, CARD_WIDTH, CARD_HEIGHT, null);
        
        //restart confirmation
        if ( isCheckingForRestart )
        {
            x = (getWidth() - SIDE_PANEL_WIDTH/2 - RESET_CONF_PANEL_WIDTH)/2;
            y = (getHeight() - RESET_CONF_PANEL_HEIGHT)/2;
            int z = RESET_CONF_PANEL_HEIGHT/3, w = RESET_CONF_PANEL_WIDTH/2;
            
            g.setColor(Color.BLACK);
            g.drawRect(x, y, RESET_CONF_PANEL_WIDTH, RESET_CONF_PANEL_HEIGHT);
            
            g.setColor(new Color(0, 0, 128));
            g.fillRect(x, y, RESET_CONF_PANEL_WIDTH, RESET_CONF_PANEL_HEIGHT);
            g.setColor(new Color(0, 128, 0));
            g.fillRect(x, y + z, w, RESET_CONF_PANEL_HEIGHT - z);
            g.setColor(new Color(128, 0, 0));
            g.fillRect(x + w, y + z, w, RESET_CONF_PANEL_HEIGHT - z);
            
            image = new ImageIcon("cards/resetConf.png").getImage();
            g.drawImage(image, x+13, y, 225, z, null);
        }
        
        // Win Screen
        if ( game.hasWon() )
        {
            g.setColor(new Color(0, 0, 128));
            g.fillRect(0, 0, getWidth(), getHeight());
            
            x = (getWidth() - 450)/2;
            y = (getHeight() - 200)/2;
            
            image = new ImageIcon("cards/winScreen.png").getImage();
            g.drawImage(image, x, y-50, 450, 200, null);
            
            //replay button
            x = (getWidth() - 200)/2;
            
            image = new ImageIcon("cards/replay.png").getImage();
            g.drawImage(image, x, y+175, 200, 70, null);
        }
    }

    private void drawCard(Graphics g, Card card, int x, int y)
    {
        if (card == null)
        {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        }
        else
        {
            String fileName = card.getFileName();
            if (!new File(fileName).exists())
                throw new IllegalArgumentException("bad file name:  " + fileName);
            Image image = new ImageIcon(fileName).getImage();
            g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
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
        if ( game.hasWon() )
            game.restart();
        if ( isCheckingForRestart )
        {
            if ( e.getX() < getWidth()/2 - SIDE_PANEL_WIDTH/4 )
                game.restart();
            isCheckingForRestart = false;
            repaint();
            return;
        }
        //none selected previously
        int col = e.getX() / (SPACING + CARD_WIDTH);
        int row = e.getY() / (SPACING + CARD_HEIGHT);
        if (col < 7)
        {
            if (row == 0 && col == 0)
                game.stockClicked();
            else if (row == 0 && col == 1)
                game.wasteClicked1();
            else if (row == 0 && e.getX() <= (int) ((SPACING + CARD_WIDTH) * 2.5) )
                game.wasteClicked2();
            else if (row == 0 && col == 2)
                game.wasteClicked3();
            else if (row == 0 && col >= 3)
                game.foundationClicked(col - 3);
            else
                game.pileClicked(col);
        }
        else // My special buttons
        {
            if ( row == 0 )
                game.undo(); // undo button
            else if ( row == 1 )
                isCheckingForRestart = true; // restart button
            else if ( row == 2 )
                game.sendPossibleCardsToFoundation(); // send to foundations
            else if ( row == 3 )
            {
                if ( game.isAllRevealed() )
                    game.autoComplete(); // autocomplete
            }
            else //No other options - Easter Egg
                System.out.println("need more options!!");
        }
        repaint();
    }

    private void drawBorder(Graphics g, int x, int y)
    {
        g.setColor(Color.YELLOW);
        g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        g.drawRect(x + 1, y + 1, CARD_WIDTH - 2, CARD_HEIGHT - 2);
        g.drawRect(x + 2, y + 2, CARD_WIDTH - 4, CARD_HEIGHT - 4);
    }

    public void unselect()
    {
        selectedRow = -1;
        selectedCol = -1;
        selectedWaste = 0;
    }

    public boolean isWasteSelected()
    {
        return selectedRow == 0 && selectedCol == 1;
    }
    
    public int selectedWaste() // Returns which card in waste is selected
    {
        return selectedWaste;
    }
    
    public void selectWaste1() // Selects the first card in wastez
    {
        selectedRow = 0;
        selectedCol = 1;
        selectedWaste = 1;
    }
    
    public void selectWaste2() // Selects the second card in wastez
    {
        selectedRow = 0;
        selectedCol = 1;
        selectedWaste = 2;
    }
    
    public void selectWaste3() // Selects the third card in wastez
    {
        selectedRow = 0;
        selectedCol = 1;
        selectedWaste = 3;
    }

    public boolean isPileSelected()
    {
        return selectedRow == 1;
    }

    public int selectedPile()
    {
        if (selectedRow == 1)
            return selectedCol;
        else
            return -1;
    }

    public void selectPile(int index)
    {
        selectedRow = 1;
        selectedCol = index;
        selectedWaste = 0;
    }
    
    public boolean isFoundationSelected()
    {
        return selectedRow == 0 && selectedCol >= 3;
    }

    public int selectedFoundation()
    {
        if ( isFoundationSelected() )
            return selectedCol - 3;
        else
            return -1;
    }

    public void selectFoundation(int index)
    {
        selectedRow = 0;
        selectedCol = index + 3;
        selectedWaste = 0;
    }
}