import java.io.*;
/**
 * Tests the scanner class on the provided mystery documents.
 *
 * @author Anu Datar
 * @version 05/17/2018
 */
public class ScannerTester
{
    /**
     *  Main tester method 
     *
     * @param  str array of String objects 
     */
    public static void main(String[] str) throws FileNotFoundException
    {
        FileReader reader = new FileReader(new File("FindAuthorMaterial/MysteryText/mystery1.txt"));
        // StringReader reader = new StringReader("This is a string");
        Scanner scanner = new Scanner(reader);
        
        Document doc = new Document( scanner );
        doc.parseDocument();
        
        System.out.println( doc.toString() );
    }
}
