import java.util.*;
/**
 * This is a solitaire game.
 * /you can draw 3 from the stock, pull any of the 3 visible cards in waste,
 * move cards to piles and foundations, and move cards from piles, foundations, and the waste.
 * 
 * The game also celebrates a win and has many interesting features
 * (such as undo, auto-complete, send to foundations, restart with confirmation screen).
 * 
 * @author Utkarsh Priyam
 * @version 11/5/18
 */
public class Solitaire
{
    /**
     * The main game method - Creates a new solitaire game.
     * @param args      array of string parameters
     * @postcondition   a Solitaire game now exists
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;
    private Stack<String> moves;
    private int notRevealed;
    
    /**
     * Constructor for the Solitaire class.
     * @postcondition   Creates the stock, waste, piles, and foundations stacks.
     *                  Creates the deck of 52 unique cards and shuffles it into the stock.
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        piles = new Stack[7];
        stock = new Stack<Card>();
        waste = new Stack<Card>();
        for ( int i = 0; i < foundations.length; i++ )
            foundations[i] = new Stack<Card>();
        for ( int i = 0; i < piles.length; i++ )
            piles[i] = new Stack<Card>();
        display = new SolitaireDisplay(this);
        createStock();
        deal();
        notRevealed = 21;
        moves = new Stack<String>();
    }
    
    /**
     * Returns the card on top of the stock or null if the stock is empty.
     * @return  null if the stock is empty; otherwise,
     *          the card on top of the stock
     */
    public Card getStockCard()
    {
        if ( stock.isEmpty() )
            return null;
        return stock.peek();
    }
    
    /**
     * Returns the card on top of the waste or null if the waste is empty.
     * @return  null if the waste is empty; otherwise,
     *          the card on top of the waste
     */
    public Card getWasteCard()
    {
        if ( waste.isEmpty() )
            return null;
        return waste.peek();
    }
    
    /**
     * Returns the waste stack.
     * @return  the waste stack
     */
    public Stack<Card> getWaste()
    {
        return waste;
    }
    
    /**
     * Returns the top card of the indexth foundation or null if that foundation is empty.
     * @param index     the index of the foundation to get the card from
     * @precondition    0 <= index < 4
     * @return          null if the foundation is empty; otherwise,
     *                  the card on top of that foundation
     */
    public Card getFoundationCard(int index)
    {
        if ( foundations[index].isEmpty() )
            return null;
        return foundations[index].peek();
    }
    
    /**
     * Returns the indexth pile.
     * @param index     the index of the pile to return
     * @precondition    0 <= index < 4
     * @return          the indexth pile
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /**
     * Creates a deck of 52 unique cards.
     * @postcondition   there is a standard deck of 52 unique cards
     */
    private void createStock()
    {
        ArrayList<Card> cards = new ArrayList<Card>();
        String[] suits = {"s", "h", "d", "c"};
        for ( int r = 1; r <= 13; r++ )
        {
            cards.add( new Card(r, "s") );
            cards.add( new Card(r, "d") );
            cards.add( new Card(r, "c") );
            cards.add( new Card(r, "h") );
        }
        for ( int i = 52; i > 0; i-- )
        {
            int rand = (int) ( Math.random() * i );
            stock.push( cards.remove(rand) );
        }
    }
    
    /**
     * Deals the necessary cards to the piles.
     * @postcondition   the ith pile contains i face-down cards and 1 face-up card
     */
    private void deal()
    {
        for ( int i = 0; i < piles.length; i++ )
            for ( int j = 0; j < i + 1; j++ )
            {
                piles[i].push( stock.pop() );
                if ( j == i )
                    piles[i].peek().turnUp();
            }
    }
    
    /**
     * Moves 3 new cards to the waste pile or resets the stock.
     * @postcondition   moves up to 3 cards to waste if the stock is not empty; otherwise,
     *                  resets the stock with the cards from the waste
     */
    public void stockClicked()
    {
        if ( stock.isEmpty() )
            resetStock();
        else
            dealThreeCards();
        display.unselect();
    }
    
    /**
     * Deals up to three cards from the stock onto the waste.
     * @precondition    the stock has at least 1 card
     */
    private void dealThreeCards()
    {
        int i = 0;
        while ( i < 3 && ! stock.isEmpty() )
        {
            waste.push( stock.pop() );
            waste.peek().turnUp();
            i++;
        }
        moves.push("s" + String.valueOf(i)); // i tells number of cards put on waste
    }
    
    /**
     * Resets the stock with the cards from the waste pile.
     * @precondition    the stock is empty
     */
    private void resetStock()
    {
        while ( ! waste.isEmpty() )
        {
            stock.push( waste.pop() );
            stock.peek().turnDown();
        }
        moves.push("sR"); // Stock Reset
    }
    
    /**
     * Selects the first card in waste if it is not already selected.
     * @postcondition   selects the first card in waste if it is not already selected;
     *                  tries to send the card to foundations otherwise
     */
    public void wasteClicked1()
    {
        if ( display.selectedWaste() == 1 )
        {
            Card card = popKthWaste(1);
            tryToSendToFoundations("w1", card);
            display.unselect();
        }
        else if ( ! display.isPileSelected() && ! display.isFoundationSelected() )
        {
            if ( ! waste.isEmpty() )
                display.selectWaste1();
        }
    }
    
    /**
     * Selects the second card in waste if it is not already selected.
     * @postcondition   selects the second card in waste if it is not already selected;
     *                  tries to send the card to foundations otherwise
     */
    public void wasteClicked2()
    {
        if ( display.selectedWaste() == 2 )
        {
            Card card = popKthWaste(2);
            tryToSendToFoundations("w2", card);
            display.unselect();
        }
        else if ( ! display.isPileSelected() && ! display.isFoundationSelected() )
        {
            if ( ! waste.isEmpty() )
            {
                Card c1 = waste.pop();
                if ( ! waste.isEmpty() )
                    display.selectWaste2();
                waste.push( c1 );
            }
        }
    }
    
    /**
     * Selects the third card in waste if it is not already selected.
     * @postcondition   selects the third card in waste if it is not already selected;
     *                  tries to send the card to foundations otherwise
     */
    public void wasteClicked3()
    {
        if ( display.selectedWaste() == 3 )
        {
            Card card = popKthWaste(3);
            tryToSendToFoundations("w3", card);
            display.unselect();
        }
        else if ( ! display.isPileSelected() && ! display.isFoundationSelected() )
        {
            if ( ! waste.isEmpty() )
            {
                Card c1 = waste.pop();
                if ( ! waste.isEmpty() )
                {
                    Card c2 = waste.pop();
                    if ( ! waste.isEmpty() )
                        display.selectWaste3();
                    waste.push( c2 );
                }
                waste.push( c1 );
            }
        }
    }
    
    /**
     * Checks to see if the card can be sent to any of the 4 foundations,
     * and sends the card if it can.
     * @param src       the source of the card (only used for undo)
     * @param card      the card to check
     * @precondition    the given source is properly encoded by the "undo" encoding standard
     * @precondition    the card actually came from the src pile
     * @postcondition   the card ends up in 
     */
    private void tryToSendToFoundations( String src, Card card )
    {
        boolean sent = false;
        for ( int i = 0; !sent && i < 4; i++ )
            if ( canAddToFoundation(card, i) )
            {
                foundations[i].push( card );
                moves.push(src + "f" + String.valueOf(i));
                sent = true;
            }
        if ( ! sent )
        {
            String s = src.substring(0,1);
            int ind = Integer.parseInt( src.substring(1,2) );
            if ( s.equals("w") )
                pushKthWaste( card, ind );
            else
                piles[ind].push( card );
        }
    }
    
    /**
     * Gets the kth card in waste (because stacks can't use get(i)).
     * @param k         the index of the card to retrieve
     * @precondition    the waste has at least k cards
     * @postcondition   the waste pile has not changed in any way
     * @return          the kth card in waste
     */
    private Card getKthWaste( int k )
    {
        Card[] cards = new Card[k];
        for ( int i = 0; i < k; i++ )
            cards[i] = waste.pop();
        for ( int i = k - 1; i >= 0; i-- )
            waste.push( cards[i] );
        return cards[k-1];
    }
    
    /**
     * Removes the kth card in waste (because stacks can't use remove(i)).
     * @param k         the index of the card to remove
     * @precondition    the waste has at least k cards
     * @postcondition   the waste pile has not changed in any way
     *                  except that it no longer has that kth card
     * @return          the kth card in waste
     */
    private Card popKthWaste( int k )
    {
        Card[] cards = new Card[k];
        for ( int i = 0; i < k; i++ )
            cards[i] = waste.pop();
        for ( int i = k - 2; i >= 0; i-- )
            waste.push( cards[i] );
        return cards[k-1];
    }
    
    /**
     * Adds the kth card in waste (because stacks can't use add(i)).
     * @param k         the index where the card will be added
     * @param card      the card to add
     * @precondition    the waste has at least k cards
     * @postcondition   the waste pile has not changed in any way
     *                  except that it now has the new card at index k
     */
    private void pushKthWaste( Card card, int k )
    {
        Card[] cards = new Card[k-1];
        for ( int i = 0; i < k-1; i++ )
            cards[i] = waste.pop();
        waste.push(card);
        for ( int i = k - 2; i >= 0; i-- )
            waste.push( cards[i] );
    }
    
    /**
     * This is called when the given foundation is clicked.
     * @param index     the index of the foundation
     * @precondition    0 <= index < 4
     * @postcondition   Selects the indexth foundation
     *                  if it is not already selected.
     *                  If something else is selected,
     *                  tries to move the top card of
     *                  the other selected stack of cards
     *                  to this foundation.
     */
    public void foundationClicked(int index)
    {
        if ( display.isWasteSelected() )
        {
            int ind = display.selectedWaste();
            Card wasteCard = getKthWaste( ind );
            if ( canAddToFoundation(wasteCard, index) )
            {
                foundations[index].push( popKthWaste(ind) );
                display.unselect();
                moves.push("w" + String.valueOf(ind) + "f" + String.valueOf(index));
                // card moved from waste to foundations[index]
            }
        }
        else if ( display.isPileSelected() )
        {
            int ind = display.selectedPile();
            if ( canAddToFoundation(piles[ind].peek(), index) )
            {
                foundations[index].push( piles[ind].pop() );
                display.unselect();
                moves.push("p" + String.valueOf(ind) + "f" + String.valueOf(index));
                // card moved from piles[ind] to foundations[index]
            }
        }
        else if ( display.isFoundationSelected() )
        {
            if ( index == display.selectedFoundation() )
                display.unselect();
            else
                display.selectFoundation(index);
        }
        else
        {
            display.selectFoundation(index);
        }
    }
    
    /**
     * Checks if a given card can be put on the indexth foundation
     * @param card      the card to check
     * @param index     the index of the foundation
     * @precondition    0 <= index < 4
     * @return          true, if the given card can
     *                  legally be moved to the top
     *                  of the given foundation; otherwise,
     *                  false
     */
    private boolean canAddToFoundation(Card card, int index)
    {
        if ( card == null )
            return false;
        Stack<Card> found = foundations[index];
        if ( found.isEmpty() )
            return card.getRank() == 1;
        Card foundCard = found.peek();
        boolean suitMatches = foundCard.getSuit().equals(card.getSuit());
        return suitMatches && ( card.getRank() - foundCard.getRank() == 1 );
    }
    
    /**
     * This is called when the given pile is clicked.
     * @param index     the index of the pile
     * @precondition    0 <= index < 7
     * @postcondition   Selects the indexth pile
     *                  if it is not already selected.
     *                  If something else is selected,
     *                  tries to move the top card(s) of
     *                  the other selected stack of cards
     *                  to this pile.
     */
    public void pileClicked(int index)
    {
        if ( display.isWasteSelected() )
        {
            int ind = display.selectedWaste();
            Card wasteCard = getKthWaste( ind );
            if ( canAddToPile(wasteCard, index) )
            {
                piles[index].push( popKthWaste(ind) );
                display.unselect();
                moves.push("w" + String.valueOf(ind) +
                           "p" + String.valueOf(index));
                // card moved from waste to piles[index]
            }
        }
        else if ( display.isPileSelected() )
        {
            int ind = display.selectedPile();
            if ( ind == index )
            {
                Card card = piles[index].pop();
                tryToSendToFoundations("p" + String.valueOf(index), card);
                display.unselect();
                if ( ! piles[ind].isEmpty() && ! piles[ind].peek().isFaceUp() )
                    pileClicked(ind);
            }
            else
            {
                if ( ! piles[index].isEmpty() && piles[index].peek().isFaceUp() )
                    display.selectPile(index);
                movePileToPile(ind, index, 0);
                // Either moves from piles[ind] to piles[index]
                // or does nothing if the move is not possible
            }
        }
        else if ( display.isFoundationSelected() )
        {
            int ind = display.selectedFoundation();
            if ( foundations[ind].isEmpty() )
            {
                display.selectPile(index);
                return;
            }
            if ( canAddToPile(foundations[ind].peek(), index) )
            {
                piles[index].push( foundations[ind].pop() );
                display.unselect();
                moves.push("f" + String.valueOf(ind) +
                           "p" + String.valueOf(index));
                // card moved from waste to piles[index]
            }
        }
        else if ( ! piles[index].isEmpty() )
        {
            if ( piles[index].peek().isFaceUp() )
                display.selectPile(index);
            else
            {
                piles[index].peek().turnUp();
                notRevealed--;
                moves.push("p" + String.valueOf(index) + "F"); // card at piles[index] flipped
            }
        }
    }
    
    /**
     * Checks if a card can legally be added to the indexth pile
     * @param card      the card to check for "add-ability" to the pile
     * @param index     the index of the pile to check
     * @precondition    0 <= index < 7
     * @precondition    the card is not null
     * @return          true, if the given card can legally be
     *                  moved to the top of the given pile; otherwise,
     *                  false
     */
    private boolean canAddToPile(Card card, int index)
    {
        if ( piles[index].isEmpty() )
            return card.getRank() == 13;
        Card cardOnPile = piles[index].peek();
        boolean isOppColor = card.isRed() != cardOnPile.isRed();
        return isOppColor && ( cardOnPile.getRank() - card.getRank() == 1 );
    }
    
    /**
     * Adds a stack of cards onto the indexth pile
     * @param cards     the stack of cards
     * @param index     the index of the pile
     * @precondition    0 <= index < 7
     * @precondition    cards is in ascending order by rank
     *                  and alternating color-wise
     * @postcondition   removes the elements from cards and
     *                  adds them to the indexth pile
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        Stack<Card> pile = piles[index];
        while ( ! cards.isEmpty() )
            pile.push( cards.pop() );
    }
    
    /**
     * Moves the cards with rank <= r from i1 to i2
     * @param i1        The index of the source pile
     * @param i2        The index of the destination pile
     * @param r         The rank up to which a move undone (0 if not undoing)
     * @precondition    0 <= i1 < 7
     * @precondition    0 <= i2 < 7
     * @precondition    0 <= r <= 13
     * @precondition    if r != 0, then pile i1 must have a card with rank r
     * @precondition    the move from i1 to i2 must be valid
     * @postcondition   the cards with rank <= r are moved from i1 to i2
     */
    private void movePileToPile( int i1, int i2, int r)
    {
        Stack<Card> src = piles[i1], dest = piles[i2]; // source and destination piles
        int rank; // The required rank of the card(s) to destination pile
        if ( r != 0 )
            rank = r;
        else if ( dest.isEmpty() )
            rank = 13;
        else if ( ! dest.peek().isFaceUp() )
            return;
        else
            rank = dest.peek().getRank() - 1;
        Stack<Card> cards = new Stack<Card>();
        while ( ! src.isEmpty() && src.peek().isFaceUp() && src.peek().getRank() <= rank )
            cards.push( src.pop() );
        
        if ( cards.isEmpty() || cards.peek().getRank() != rank )
            addToPile( cards, i1 );
        else if ( r == 0 && ! canAddToPile(cards.peek(), i2) )
            addToPile( cards, i1 );
        else
        {
            addToPile( cards, i2 );
            display.unselect();
            if ( r == 0 ) // Not undoing an action
            {
                moves.push("p" + String.valueOf(i1) +
                           "p" + String.valueOf(i2) +
                                 String.valueOf(rank));
                // cards (whose rank <= rank) moved from piles[i1] to piles[i2]
            }
            if ( ! piles[i1].isEmpty() && ! piles[i1].peek().isFaceUp() )
                pileClicked(i1);
        }
    }
    
    /**
     * Undoes the previous move, which it remembers with a stack.
     * @precondition    the move at the top of the stack must be encoded correctly
     *                  and must be the most recently done move
     * @postcondition   the most recently done move is undone
     */
    public void undo()
    {
        if ( moves.isEmpty() )
            return;
        display.unselect();
        String move = moves.pop(), st1 = move.substring(0,1), st2; // stacks 1 and 2
        if ( st1.equals("s") ) // card came from Stock
        {
            String action = move.substring(1,2);
            if ( action.equals("R") ) // Stock Reset
                while ( ! stock.isEmpty() )
                {
                    waste.push( stock.pop() );
                    waste.peek().turnUp();
                }
            else // Deal 3 cards to waste
            {
                int i = 0, j = Integer.parseInt(action);
                while ( i < j )
                {
                    stock.push( waste.pop() );
                    stock.peek().turnDown();
                    i++;
                }
            }
        }
        else if ( st1.equals("w") ) // card came from Waste (index)
        {
            st2 = move.substring(2,3);
            int index = Integer.parseInt( move.substring(1,2) ),
                ind = Integer.parseInt( move.substring(3,4) );
            if ( st2.equals("f") ) // card went to foundations[ind]
                pushKthWaste( foundations[ind].pop(), index );
            else // card went to piles[ind]
                pushKthWaste( piles[ind].pop(), index );
        }
        else if ( st1.equals("f") ) // card came from foundations[index]
        {
            int index = Integer.parseInt( move.substring(1,2) ),
                ind = Integer.parseInt( move.substring(3,4) );
            foundations[index].push( piles[ind].pop() );
        }
        else // card came from piles[index]
        {
            int index = Integer.parseInt( move.substring(1,2) ), ind;
            st2 = move.substring(2,3);
            if ( st2.equals("f") ) // card went to foundations[ind]
            {    
                ind = Integer.parseInt( move.substring(3) );
                piles[index].push( foundations[ind].pop() );
            }
            else if ( st2.equals("F") ) // card flipped in pile[index]
            {
                piles[index].peek().turnDown();
                notRevealed++;
                undo();
            }
            else // card went to piles[ind]
            {    
                ind = Integer.parseInt( move.substring(3,4) );
                movePileToPile( ind, index, Integer.parseInt(move.substring(4)) );
            }
        }
    }
    
    /**
     * Restarts the game by resetting all of the card stacks and the moves stack.
     * @precondition    the player wants to reset the game
     * @postcondition   resets the game
     */
    public void restart()
    {
        stock = new Stack<Card>();
        waste = new Stack<Card>();
        for ( int i = 0; i < foundations.length; i++ )
            foundations[i] = new Stack<Card>();
        for ( int i = 0; i < piles.length; i++ )
            piles[i] = new Stack<Card>();
        createStock();
        deal();
        notRevealed = 21;
        display.unselect();
        moves = new Stack<String>();
    }
    
    /**
     * Sends as many possible cards as it can to the foundations.
     * Iterates through the 3 waste cards and the top of the piles.
     * @postcondition   any cards from the top 3 of waste and the top
     *                  of each pile that can go to any foundation
     *                  are sent to that corresponding foundation.
     */
    public void sendPossibleCardsToFoundation()
    {
        display.unselect();
        
        wasteClicked3();
        wasteClicked3();
        
        wasteClicked2();
        wasteClicked2();
        
        wasteClicked1();
        wasteClicked1();
        
        for ( int i = 0; i < 7; i++ )
            if ( ! piles[i].isEmpty() && piles[i].peek().isFaceUp() )
            {
                pileClicked(i);
                pileClicked(i);
            }
        display.unselect();
    }
    
    /**
     * Checks if all the cards are revealed or not using a hidden counter
     * which tracks the number of cards revealed so far.
     * @return  true, if all the cards have been revealed; otherwise,
     *          false
     */
    public boolean isAllRevealed()
    {
        return notRevealed == 0;
    }
    
    /**
     * Checks whether the player has won the game or not
     * by checking if the top card of each foundation is a king.
     * @return  true, if the player has won the game; otherwise,
     *          false
     */
    public boolean hasWon()
    {
        for ( int i = 0; i < 4; i++ )
            if ( foundations[0].isEmpty() )
                return false;
        return foundations[0].peek().getRank() == 13 &&
               foundations[1].peek().getRank() == 13 &&
               foundations[2].peek().getRank() == 13 &&
               foundations[3].peek().getRank() == 13;
    }
    
    /**
     * Autocompletes the game by adding kings to all the foundations.
     * @precondition    isAllRevealed() returns true
     * @postcondition   the game "autocompletes" and the player
     *                  goes to the win screen immediately
     */
    public void autoComplete()
    {
        foundations[0].push( new Card(13, "s") );
        foundations[1].push( new Card(13, "d") );
        foundations[2].push( new Card(13, "c") );
        foundations[3].push( new Card(13, "h") );
    }
}