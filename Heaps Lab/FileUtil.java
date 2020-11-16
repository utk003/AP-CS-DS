import java.io.*;
import java.util.*;

/**
 * The FileUtil class can read files and save files with a given file name.
 * @author Given in Project
 * @version Given in Project
 */
public class FileUtil
{
    /**
     * Creates a string iterator that holds the contents of a file with name fileName.
     * @param fileName  the name of the file to read
     * @precondition    fileName is a valid file name
     * @return          a string iterator with the contents of the file
     */
    public static Iterator<String> loadFile(String fileName)
    {
        try
        {
            Scanner in = new Scanner(new File(fileName));
            List<String> list = new ArrayList<String>();
            while (in.hasNextLine())
                list.add(in.nextLine());
            in.close();
            return list.iterator();
        }
        catch(FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Creates a file with name fileName using a string iterator
     * that holds the contents of the file.
     * @param fileName  the name of the file to read
     * @param data      the string iterator that holds the contents of a file
     * @precondition    fileName is a valid file name
     * @precondition    the string iterator is not null
     */
    public static void saveFile(String fileName, Iterator<String> data)
    {
        try
        {
            PrintWriter out = new PrintWriter(
                new FileWriter(fileName), true);
            while (data.hasNext())
                out.println(data.next());
            out.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}