import java.util.*;

/**
 * A WordGame object, which enables the user
 * to play a large array of word games:
 * Echo, Jotto, Search, Hangman, Jumble, and Subwords.
 * 
 * Echo repeats your word and tells you if it's valid or not.
 * Jotto makes you guess a word of a given length and
 * tells you how many letters you have in common with the target word.
 * Search makes you search for a word and tells you whether the guess
 * was before or after the target word lexographically.
 * Hangman plays hangman.
 * Jumble jumbles a word and makes you guess the original word.
 * Subwords makes you guess all the subwords of a given word.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class WordGame
{
    // instance variables - replace the example below with your own
    private WordGameDisplay d;
    private ArrayList<String> dictionary;

    /**
     * Constructor for objects of class WordGame
     */
    public WordGame()
    {
        d = new WordGameDisplay();
        dictionary = new ArrayList<String>();
        Iterator<String> it = d.loadWords("words.txt");
        while ( it.hasNext() )
            dictionary.add( it.next() );
        try
        {
            play();
        }
        catch ( InterruptedException e )
        {
            System.out.println( e.getMessage() );
        }
    }
    
    /**
     * A private method that continually plays games the player
     * chooses and keeps playing games until the player quits.
     * @throws InterruptedException under no circumstances
     */
    private void play() throws InterruptedException
    {
        d.setTitle("Shall we play a game?");
        d.setText("echo\njotto\nsearch\nhangman\njumble\nsubwords");
        String game = d.getGuess().toLowerCase();
        while ( ! game.equals("quit") )
        {
            if ( game.equals("echo") )
            {
                echo();
                Thread.sleep(1000);
            }
            else if ( game.equals("jotto") )
            {
                jotto();
                Thread.sleep(2500);
            }
            else if ( game.equals("search") )
            {
                search();
                Thread.sleep(4000);
            }
            else if ( game.equals("hangman") )
            {
                hangman();
                Thread.sleep(4000);
            }
            else if ( game.equals("jumble") )
            {
                jumble();
                Thread.sleep(4000);
            }
            else if ( game.equals("subwords") )
            {
                subwords();
                Thread.sleep(4000);
            }
            d.setTitle("Shall we play a game?");
            d.setText("echo\njotto\nsearch\nhangman\njumble\nsubwords");
            game = d.getGuess().toLowerCase();
        }
    }
    
    /**
     * Plays the game Echo.
     */
    public void echo()
    {
        d.setTitle("\"The Echo Game\"");
        d.setText("Enter a word");
        String isWord = "";
        int wordNum = 0;
        String guess = d.getGuess().toLowerCase();
        while ( ! guess.equals("quit") )
        {
            wordNum = dictionaryIndex( guess );
            if ( wordNum == -1 )
                isWord = "not a word";
            else
                isWord = "word #" + String.valueOf( wordNum );
            d.setText("\"" + guess + "\" is " + isWord + ".\nEnter another word.");
            guess = d.getGuess().toLowerCase();
        }
    }
    
    /**
     * Seaches for word in dictionary using binary search.
     * Returns the index of the word if it is in the dictionary,
     * and returns -1 otherwise.
     * @param word  the word to search for
     * @return      the index of the word if its in the dictionary; otherwise,
     *              -1
     */
    public int dictionaryIndex(String word)
    {
        int lower = 0;
        int higher = dictionary.size();
        int mid = (lower + higher) / 2;
        String check = dictionary.get(mid);
        while ( lower <= higher && ! check.equals( word ) )
        {
            if ( check.compareTo(word) > 0 )
                higher = mid - 1;
            else if ( check.compareTo(word) < 0 )
                lower = mid + 1;
            mid = (lower + higher) / 2;
            if ( mid >= dictionary.size() )
                return -1;
            check = dictionary.get(mid);
        }
        if ( lower > higher )
            return -1;
        return mid;
    }
    
    /**
     * Returns a random word from the dictionary.
     * @return  the random word
     */
    public String getRandomWord()
    {
        return dictionary.get( (int) (dictionary.size() * Math.random()) );
    }
    
    /**
     * Gets a random word of length len.
     * @param len   the target word length
     * @return      the random word of length len
     */
    public String getRandomWord(int len)
    {
        String word = getRandomWord();
        while ( word.length() != len )
            word = getRandomWord();
        return word;
    }
    
    /**
     * Checks and returns how many common letters s1 and s2 have.
     * @param s1    string 1
     * @param s2    string 2
     * @return      the number of common letters in s1 and s2
     */
    public int commonLetters( String s1, String s2 )
    {
        String letterMatch = letterMatch( s1, s2 );
        if ( letterMatch.equals(" ") )
            return 0;
        int s1Match = s1.indexOf(letterMatch), s2Match = s2.indexOf(letterMatch);
        s1 = s1.substring(0, s1Match) + s1.substring(s1Match + 1);
        s2 = s2.substring(0, s2Match) + s2.substring(s2Match + 1);
        return 1 + commonLetters( s1, s2 );
    }
    
    /**
     * Finds the the common letter (if any) in strings s1 and s2
     * @param s1    string 1
     * @param s2    string 2
     * @return      the common letter of s1 and s2 if one exists; otherwise,
     *              " " (space)
     */
    private String letterMatch( String s1, String s2 )
    {
        for ( int i = 0; i < s1.length(); i++ )
            for ( int j = 0; j < s2.length(); j++ )
                if ( s1.substring(i,i+1).equals( s2.substring(j,j+1) ) )
                    return s1.substring(i,i+1);
        return " ";
    }
    
    /**
     * Handles and manages the Jotto game. Exits with status
     * 0, 1, or 2 to signify quitting, passing, and winning, respectively.
     * @param n the number of letters in the Jotto word
     * @return  the exit status of the game (0, 1, or 2)
     * @throws InterruptedException under no circumstances
     */
    private int jotto(int n) throws InterruptedException
    {
        String word = getRandomWord(n);
        System.out.println(word);
        String guess;
        String messageOut = "Guess my " + String.valueOf(n) + "-letter word.";
        d.setText( messageOut );
        String feedback = "";
        int letterMatches;
        int count = 1, plays = 0;
        while (! feedback.equals( "That\'s it!" ) )
        {
            guess = d.getGuess().toLowerCase();
            if ( guess.equals( "pass" ) )
            {
                messageOut += "\nIt was \"" + word + "\". Play again!";
                d.setText( messageOut );
                Thread.sleep(2500);
                return 1;
            }
            if ( guess.equals("quit") )
            {
                messageOut += "\nIt was \"" + word + "\". Play again!";
                d.setText( messageOut );
                Thread.sleep(2500);
                return 0;
            }
            letterMatches = commonLetters( word, guess );
            if ( dictionaryIndex( guess ) == -1 )
                feedback = "not a word";
            else if ( guess.length() != n )
                feedback = "must be " + String.valueOf(n) + " letters long";
            else
            {
                if ( letterMatches != n )
                    feedback = String.valueOf( letterMatches );
                else
                    feedback = "That\'s it!";
                plays++;
            }
            messageOut += "\n" + String.valueOf(count) + ".\t" + guess + "\t" + feedback;
            d.setText( messageOut );
            count++;
        }
        messageOut += "\nIt took " + String.valueOf(plays)
                   + " guesses to find \"" + word + "\".";
        d.setText( messageOut );
        Thread.sleep(2500);
        return 2;
    }
    
    /**
     * Plays Jotto
     * @throws InterruptedException under no circumstances
     */
    public void jotto() throws InterruptedException
    {
        d.setTitle("Let\'s Play Jotto");
        int wordLength = 3;
        int gameVal = jotto( wordLength );
        while ( gameVal != 0 )
        {
            if ( gameVal == 2 )
                wordLength++;
            gameVal = jotto( wordLength );
        }
    }
    
    /**
     * Plays Search
     */
    public void search()
    {
        String word = getRandomWord();
        d.setTitle("Search For The Word");
        String messageOut = "";
        d.setText(messageOut);
        String guess;
        int count = 0;
        String feedback = "";
        while ( ! feedback.equals( "That\'s it!" ) )
        {
            guess = d.getGuess().toLowerCase();
            count++;
            if ( guess.equals("quit") )
                break;
            if ( dictionaryIndex( guess ) == -1 )
                feedback = "not a word";
            else if ( word.compareTo( guess ) > 0 )
                feedback = "later";
            else if ( word.compareTo( guess ) < 0 )
                feedback = "earlier";
            else
                feedback = "That\'s it!";
            messageOut += String.valueOf(count) + ".\t" + guess + "\t" + feedback + "\n";
            d.setText(messageOut);
        }
        messageOut += "It took " + String.valueOf(count) + " guesses to "
                    + "guess the word \"" + word + "\".";
        d.setText(messageOut);
    }
    
    /**
     * Plays Hangman
     */
    public void hangman() throws InterruptedException
    {
        String word = getRandomWord();
        int distinctLetters = commonLetters(word, "abcdefghijklmnopqrstuvwxyz");
        int maxWrong = Math.min( 7, 26 - distinctLetters );
        while ( word.length() < 5 )
            word = getRandomWord();
        d.setTitle("Let\'s Play HangMan");
        String guessedWord = "", guessedLetters = "";
        for ( int i = 0; i < word.length(); i++ )
            guessedWord += "_ ";
        String messageOut = guessedWord + "\n\nGuessed Letters:\n" + guessedLetters;
        d.setText(messageOut);
        String guess;
        while ( guessedWord.indexOf("_") != -1 && guessedLetters.length() < 2 * maxWrong )
        {
            guess = d.getGuess().toLowerCase();
            if ( guess.equals("quit") )
                return;
            if ( guess.length() != 1 || guess.charAt(0) < 'a' || guess.charAt(0) > 'z' )
            {
                d.setText("\"" + guess + "\" is not a valid letter.\nPlease enter a valid letter");
                Thread.sleep(2000);
            }
            else
            {
                if ( word.indexOf(guess) == -1 )
                {
                    if ( guessedLetters.indexOf(guess) == -1 )
                        guessedLetters += guess + " ";
                }
                else
                {
                    int i = word.indexOf(guess);
                    while ( i != -1 )
                    {
                        guessedWord = guessedWord.substring( 0, 2 * i ) + guess
                                    + guessedWord.substring( 2 * i + 1 );
                        i = word.indexOf(guess, i+1);
                    }
                }
                messageOut = guessedWord + "\n\nGuessed Letters:\n" + guessedLetters;
            }
            d.setText(messageOut);
        }
        if ( guessedWord.indexOf("_") != -1 )
            messageOut += "\n\nYou lost. The word was " + word + ".";
        else
            messageOut += "\n\nYou won! The word was " + word + ".";
        d.setText(messageOut);
    }
    
    /**
     * Plays Jumble
     */
    public void jumble() throws InterruptedException
    {
        d.setTitle("Let\'s Play Jumble");
        int len = 3, i;
        boolean won = true;
        String wordOrig = "", wordScram, word, guess;
        while ( won )
        {
            word = getRandomWord(len);
            wordOrig = word;
            wordScram = "";
            while ( word.length() > 0 )
            {
                i = (int) (word.length() * Math.random());
                wordScram += word.substring(i,i+1);
                word = word.substring(0,i) + word.substring(i+1);
            }
            d.setText("The word has been scrambled to: \"" + wordScram + "\"");
            guess = d.getGuess();
            while ( commonLetters(guess, wordOrig) != len || guess.length() != len )
            {
                d.setText("\"" + guess + "\" does not contain the right letters."
                        + "\nPlease put a valid answer.\n\n"
                        + "The word was scrambled to: \"" + wordScram + "\"");
                guess = d.getGuess();
            }
            if ( ! guess.equals(wordOrig) )
                won = false;
            else
            {
                len++;
                d.setText("Congratulations! The word was \"" + wordOrig + "\".");
                Thread.sleep(2500);
            }
        }
        d.setText("You lost on round " + String.valueOf(len - 2)
                + ".\nThe word was \"" + wordOrig + "\".");
    }
    
    /**
     * Plays Subwords
     */
    public void subwords() throws InterruptedException
    {
        d.setTitle("Let\'s Play SubWords");
        int len;
        String word = "";
        ArrayList<String> subwords = new ArrayList<String>();
        while ( subwords.size() == 0 )
        {
            len = 4 + (int) (2 * Math.random());
            word = getRandomWord(len);
            fillSubWordsList( subwords, word );
        }
        playSubWords( word, subwords );
    }
    
    /**
     * Fills the given arraylist with all subwords of the given word
     * @param subwords  the arraylist to fill
     * @param word      the word to split into subwords
     */
    private void fillSubWordsList( ArrayList<String> subwords, String word )
    {
        String subword;
        for ( int i = 0; i < word.length(); i++ )
            for ( int j = 0; j < word.length(); j++ )
                for ( int k = 0; k < word.length(); k++ )
                    if ( i != j && j != k && k != i )
                    {
                        subword = word.substring(i,i+1)
                                + word.substring(j,j+1)
                                + word.substring(k,k+1);
                        if ( dictionaryIndex(subword) != -1 )
                            add(subwords, subword);
                        for ( int l = 0; l < word.length(); l++ )
                            if ( l != i && l != j && l != k )
                            {
                                subword = word.substring(i,i+1)
                                        + word.substring(j,j+1)
                                        + word.substring(k,k+1)
                                        + word.substring(l,l+1);
                                if ( dictionaryIndex(subword) != -1 )
                                    add(subwords, subword);
                                if ( word.length() == 5 ) // Hack to avoid 5th for loop
                                {
                                    int m = 10 - i - j - k - l; // all 5 distinct -> sum = 10
                                    subword = word.substring(i,i+1)
                                            + word.substring(j,j+1)
                                            + word.substring(k,k+1)
                                            + word.substring(l,l+1)
                                            + word.substring(m,m+1);
                                    if ( dictionaryIndex(subword) != -1 )
                                        add(subwords, subword);
                                }
                            }
                    }
    }
    
    /**
     * Adds the given string to the arraylist using sequential search
     * in order to ensure the arraylist is in ascending order afterwards.
     * @param arr       the arraylist
     * @param s         the word to add
     * @precondition    the arraylist is in ascending order
     * @postcondition   the arraylist is in ascending order
     */
    private void add(ArrayList<String> arr, String s)
    {
        int i = 0;
        while ( i < arr.size() && arr.get(i).compareTo(s) < 0 )
            i++;
        if ( i >= arr.size() )
            arr.add(s);
        if ( arr.get(i).compareTo(s) == 0 )
            return;
        arr.add(i, s);
    }
    
    /**
     * Handles and manages the Subwords game
     * @param word      the word to play with
     * @param subwords  all the subwords of worD
     * @throws InterruptedException under no circumstances
     */
    private void playSubWords( String word, ArrayList<String> subwords ) throws InterruptedException
    {
        int count = subwords.size();
        ArrayList<String> subwordsFound = new ArrayList<String>();
        String messageOut = "Find all the subwords of \"" + word + "\".\n"
                          + "Number of words left to find: " + String.valueOf(count) + "\n\n"
                          + "Words found:\n" + stringifyArrayList(subwordsFound);
        d.setText(messageOut);
        String guess;
        while ( count != 0 )
        {
            guess = d.getGuess().toLowerCase();
            if ( guess.equals("quit") )
            {
                ArrayList<String> missed = new ArrayList<String>();
                for ( String s: subwords )
                    if ( ! in(s, subwordsFound) )
                        missed.add(s);
                messageOut = "You had " + String.valueOf(count) + " subwords left to find.\n\n"
                           + "You missed the subwords:\n" + stringifyArrayList(missed) + "\n"
                           + "All of the subwords are:\n" + stringifyArrayList(subwords);
                d.setText(messageOut);
                Thread.sleep( 500 * Math.max(0, count - 4) );
                return;
            }
            if ( in( guess, subwords ) )
            {
                if ( in( guess, subwordsFound ) )
                    d.setText("You already found \"" + guess + "\".\nFind another subword.");
                else
                {
                    d.setText("\"" + guess + "\" is a valid subword.\nFind another subword.");
                    subwordsFound.add(guess);
                    count--;
                    messageOut = "Find all the subwords of \"" + word + "\".\n"
                               + "Number of words left to find: " + String.valueOf(count) + "\n\n"
                               + "Words found:\n" + stringifyArrayList(subwordsFound);
                    if ( count == 0 )
                        break;
                }
            }
            else
                d.setText("\"" + guess + "\" is not a valid subword.\nFind a valid subword.");
            Thread.sleep(2000);
            d.setText(messageOut);
        }
        messageOut = "You found all " + String.valueOf(subwords.size()) + " subwords of \"" + word
                   + "\".\n\n" + "The subwords are:\n" + stringifyArrayList(subwords);
        d.setText(messageOut);
    }
    
    /**
     * Returns the string form of the arraylist
     * @param arr   the arraylist
     * @return      the string form of the arraylist (somewhat like toString())
     */
    private String stringifyArrayList( ArrayList<String> arr )
    {
        String result = "";
        for ( String s: arr )
            result += s + "\n";
        return result;
    }
    
    /**
     * Checks if word is in the given arraylist using sequential search
     * @param word  the word to search for
     * @param arrS  the arraylist to search in
     * @return      true if word is in the arraylist; otherwise,
     *              false
     */
    private boolean in( String word, ArrayList<String> arrS )
    {
        for ( String s: arrS )
            if ( word.equals(s) )
                return true;
        return false;
    }
}
