import java.util.*;
import java.io.*;
/**
 * Uses the Document class to parse documents
 * and uses weights and calculated ratios to
 * accurately determine the author of each piece.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class DocumentStatistics
{
    private Document doc;
    private List<Sentence> sentences;
    private Scanner sc;
    private double[][] novelInfoChart;
    private double[] weights;
    private String[] authorDirs;
    private String[] authors;
    private double[][] authorStatsChart;
    private String[] authorGuesses;
    private int[] indices;

    /**
     * Constructor for objects of class DocumentStatistics
     * Uses private methods to calculate specific ratios
     * and then identifies the author of the work.
     */
    public DocumentStatistics() throws IOException
    {
        novelInfoChart = new double[5][5];
        weights = new double[] {11.0, 33.0, 50.0, 0.4, 4.0};

        authorDirs = new String[] {"agatha.christie","alexandre.dumas","brothers.grim",
            "charles.dickens","douglas.adams","emily.bronte","fyodor.dostoevsky",
            "james.joyce","jane.austen","lewis.caroll","mark.twain",
            "sir.arthur.conan.doyle","william.shakespeare"};

        authors = new String[] {"Agatha Christie","Alexandre Dumas","Brothers Grim",
            "Charles Dickens","Douglas Adams","Emily Bronte","Fyodor Dostoevsky",
            "James Joyce","Jane Austen","Lewis Caroll","Mark Twain",
            "Sir Arthur Conan Doyle","William Shakespeare"};

        collectAuthorInformation();
        
        analyzeAllDocs();
        /*
        for ( int i = 0; i < 5; i++ )
        System.out.println(i + " " + novelInfoChart[i]);
        for ( int i = 0; i < 13; i++ )
        System.out.println(authors[i] + " " + authorStatsChart[i]);
         */
    }
    
    /**
     * Uses the calculated ratios and the author stats
     * in order to guess who wrote each document.
     */
    private void computeGuesses()
    {
        authorGuesses = new String[ novelInfoChart.length ];
        for ( int i = 0; i < novelInfoChart.length; i++ )
        {
            double minDiff = 0;
            for ( int k = 0; k < 5; k++ )
                minDiff += Math.abs( novelInfoChart[i][k] - authorStatsChart[0][k] );
            int index = 0;
            for ( int j = 1; j < authorDirs.length; j++ )
            {
                double diff = 0;
                for ( int k = 0; k < 5; k++ )
                    diff += Math.abs( novelInfoChart[i][k] - authorStatsChart[j][k] );
                if ( minDiff > diff )
                {
                    minDiff = diff;
                    index = j;
                }
            }
            authorGuesses[i] = authors[index];
        }
        for ( int i = 0; i < authorGuesses.length; i++ )
            System.out.println( indices[i] + "\t" + authorGuesses[i] );
        System.out.println();
    }
    
    /**
     * Analyzes all the documents that are given (indexed 1 to 5)
     * and calculates the important ratios, etc.
     */
    public void analyzeAllDocs() throws IOException
    {
        analyzeAllDocs( new int[] {1,2,3,4,5} );
    }
    
    /**
     * Analyzes all the documents whose indices are given in the parameter
     * and calculates the important ratios, etc.
     */
    public void analyzeAllDocs( int[] indices ) throws IOException
    {
        this.indices = indices;
        novelInfoChart = new double[ indices.length ][5];
        for ( int i = 0; i < novelInfoChart.length; i++ )
            novelInfoChart[i] = analyzeDoc( indices[i] );
        computeGuesses();
    }
    
    /**
     * Collects the statistics for all of the authors.
     */
    private void collectAuthorInformation() throws IOException
    {
        authorStatsChart = new double[ authorDirs.length ][5];
        String fileDirectory = "FindAuthorMaterial/SignatureFiles/";
        BufferedReader br;
        for ( int i = 0; i < authorStatsChart.length; i++ )
        {
            File f = new File(fileDirectory + authorDirs[i] + ".stats");
            br = new BufferedReader(new FileReader(f));
            br.readLine();
            for ( int j = 0; j < 5; j++ )
                authorStatsChart[i][j] = weights[j] * Double.parseDouble( br.readLine() );
        }
    }
    
    /**
     * Analyzes the specific document with index i
     * and returns a double array containing 5 important ratios:
     * 1) Average Word Length
     * 2) Type-Token Ratio
     *  (number of different words / total number of words)
     * 3) Hapax Legomena Ratio
     *  (number of words which appear only once / total number of words)
     * 4) Average number of words per sentence
     * 5) Average number of phrases per sentence
     * 
     * @param i The index of the document to parse and analyze
     * @return  the double array of length 5 containing the 5 ratios listed above.
     */
    private double[] analyzeDoc( int i ) throws IOException
    {
        File f = new File("FindAuthorMaterial/MysteryText/mystery" + i + ".txt");
        sc = new Scanner( new FileReader(f) );
        doc = new Document( sc );
        doc.parseDocument();
        sentences = doc.copyDocument();
        double[] info = new double[5];
        long totalCharsUsed = 0;
        int totalNumWords = 0;
        int numDiffWords = 0;
        int numRepUniqueWords = 0;
        int totalNumPhrases = 0;
        int numSentences = sentences.size();
        Set<Token> diffWords = new TreeSet<Token>();
        Set<Token> uniqueWords = new TreeSet<Token>();
        boolean added;
        for ( Sentence s: sentences )
        {
            List<Phrase> phrases = s.duplicateSentence();
            totalNumPhrases += phrases.size();
            for ( Phrase p: phrases )
            {
                List<Token> words = p.duplicatePhrase();
                totalNumWords += words.size();
                for ( Token t: words )
                {
                    totalCharsUsed += t.getValue().length();
                    added = diffWords.add(t);
                    if ( ! added )
                    {
                        added = uniqueWords.add(t);
                        if ( added )
                            numRepUniqueWords++;
                    }
                    else
                        numDiffWords++;
                }
            }
        }
        info[0] = weights[0] * totalCharsUsed / totalNumWords;
        info[1] = weights[1] * numDiffWords / totalNumWords;
        info[2] = weights[2] * (numDiffWords - numRepUniqueWords) / totalNumWords;
        info[3] = weights[3] * totalNumWords / numSentences;
        info[4] = weights[4] * totalNumPhrases / numSentences;
        return info;
    }
}
