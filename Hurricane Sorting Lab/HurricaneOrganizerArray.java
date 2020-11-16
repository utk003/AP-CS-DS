import java.io.*;
import java.util.*;

/**
 * Models hurricane information, works with Hurricane class
 * and the user to manipulate an array of hurricane data.
 * 
 * Data came from http://www.aoml.noaa.gov/hrd/tcfaq/E23.html except for 2018.
 * 2018 data came from https://en.wikipedia.org/wiki/2018_Atlantic_hurricane_season.
 *
 * @author Susan King 
 * @version January 17, 2019
 */
public class HurricaneOrganizerArray
{
    private Hurricane [] hurricanes;
    
    /**
     * 
     */
    public HurricaneOrganizerArray()
    {
    }

    /**
     * Comment this constructor even though you did not write it.
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     */
    public HurricaneOrganizerArray(String filename) throws IOException
    {
        readFile(filename);
    }

    /**
     * Comment this method even though you did not write it.
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     */
    private static int determineFileLength(String filename) throws IOException
    {
        Scanner inFile = new Scanner(new File(filename));
        int counter = 0;

        while(inFile.hasNextLine())
        {
            counter++;
            inFile.nextLine();
        }
        inFile.close();
        return counter;
    }

    /**
     * Comment this method even though you did not write it.
     */
    public void readFile(String filename) throws IOException
    {
        hurricanes = new Hurricane [determineFileLength(filename)];
        int hurYear, hurPressure, hurSpeed;
        String hurName, hurMonth;
        Scanner inFile = new Scanner(new File(filename));

        for(int i = 0; i < hurricanes.length; i++)
        {
            hurYear = inFile.nextInt();
            hurMonth = inFile.next();
            hurPressure = inFile.nextInt();
            hurSpeed = inFile.nextInt();
            String tempName = inFile.nextLine();
            hurName = "";
            for(int k = 0; k < tempName.length(); k++)
            {
                char c = tempName.charAt(k);
                if(('a' <= c && c <= 'z') || ('A' <= c && c <='Z'))
                    hurName += c;
            }
            Hurricane h = new Hurricane(hurYear, hurMonth, hurPressure, hurSpeed, hurName);
            hurricanes [i] = h;
        }
        inFile.close();
    }
    
    /**
     * 
     */
    public int countHurricanes()
    {
        return hurricanes.length;
    }
    
    /**
     * Comment this method.
     */
    public int findMaxWindSpeed( )
    {
        int max = 0;
        for ( Hurricane h : hurricanes )
            Math.max( max, h.getSpeed() );
        return max;
    }

    /**
     * Comment this method.
     */
    public int findMaxPressure( )
    {
        int max = 0;
        for ( Hurricane h : hurricanes )
            Math.max( max, h.getPressure() );
        return max;
    }

    /**
     * Comment this method.
     */
    public int findMinWindSpeed( )
    {
        int min = 0;
        for ( Hurricane h : hurricanes )
            Math.min( min, h.getSpeed() );
        return min;
    }

    /**
     * Comment this method.
     */
    public int findMinPressure( )
    {
        int min = 0;
        for ( Hurricane h : hurricanes )
            Math.min( min, h.getPressure() );
        return min;
    }

    /**
     * Comment this method.
     */
    public double calculateAverageWindSpeed( )
    {
        double sum = 0;
        int c = countHurricanes();
        for ( Hurricane h : hurricanes )
            sum += 1.0 * h.getSpeed() / c;
        return sum;
    }

    /**
     * Comment this method.
     */
    public double calculateAveragePressure( )
    {
        double sum = 0;
        int c = countHurricanes();
        for ( Hurricane h : hurricanes )
            sum += 1.0 * h.getPressure() / c;
        return sum;
    }

    /**
     * Comment this method.
     */
    public double calculateAverageCategory( )
    {
        double sum = 0;
        int c = countHurricanes();
        for ( Hurricane h : hurricanes )
            sum += 1.0 * h.getCategory() / c;
        return sum;
    }

    /**
     * Sorts ascending based upon the hurricanes' years,
     * The algorithm is selection sort.
     */
    public void sortYears()
    {
        int ind;
        Hurricane h;
        for ( int i = 1; i < hurricanes.length - 1; i++ )
        {
            ind = i;
            for ( int j = i + 1; j < hurricanes.length; j++ )
                if ( hurricanes[j].compareYearTo( hurricanes[ind] ) < 0 )
                    ind = j;
            if ( ind != i )
            {
                h = hurricanes[ind];
                hurricanes[ind] = hurricanes[i];
                hurricanes[i] = h;
            }
        }
    }

    /**
     * Lexicographically sorts hurricanes based on the hurricanes' name, 
     * using insertion sort.
     */
    public void sortNames()
    {
        int ind;
        Hurricane h;
        boolean stop;
        for ( int i = 1; i < hurricanes.length; i++ )
        {
            h = hurricanes[i];
            stop = false;
            for ( ind = i - 1; ind >= 0 && ! stop; ind-- )
            {
                if ( h.compareNameTo( hurricanes[ind] ) < 0 )
                    hurricanes[ind + 1] = hurricanes[ind];
                else
                {
                    hurricanes[ind + 1] = h;
                    stop = true;
                }
            }
        }
    }

    /**
     * Sorts descending based upon the hurricanes' categories,
     * using selection sort.
     */
    public void sortCategories()
    {
        int mindex;
        Hurricane temp;
        for ( int i = 0; i < hurricanes.length - 1; i++ )
        {
            mindex = i;
            for ( int j = 0; j < hurricanes.length; j++ )
                if ( hurricanes[mindex].compareCategoryTo( hurricanes[j] ) > 0 )
                    mindex = j;
            temp = hurricanes[i];
            hurricanes[i] = hurricanes[mindex];
            hurricanes[mindex] = temp;
        }
    }  

    /**
     * Sorts descending based upon pressures using a non-recursive merge sort.
     */
    public void sortPressures()
    {
        int n = 1;
        while ( n < hurricanes.length )
            n *= 2;
        Hurricane[] arr = new Hurricane[n];
        for ( int i = 0; i < hurricanes.length; i++ )
            arr[i] = hurricanes[i];
        for ( int i = hurricanes.length; i < arr.length; i++ )
            arr[i] = new Hurricane(0,"",-1,0,"");
        int len = 1;
        Hurricane[] temp;
        int i1, i2, i3;
        while ( len * 2 <= n )
        {
            for ( int i = 0; i < n; i += 2 * len )
            {
                temp = new Hurricane[len];
                for ( int j = 0; j < len; j++ )
                    temp[j] = arr[j + i];
                i1 = 0;
                i2 = len;
                i3 = 0;
                while ( i1 < len && i2 < 2 * len )
                {
                    if ( temp[i1].comparePressureTo( arr[i + i2] )  > 0 )
                    {
                        arr[i + i3] = temp[i1];
                        i1++;
                    }
                    else
                    {
                        arr[i + i3] = arr[i + i2];
                        i2++;
                    }
                    i3++;
                }
                while ( i1 < len )
                {
                    arr[i + i3] = temp[i1];
                    i1++;
                    i3++;
                }
            }
            len *= 2;
        }
        for ( int i = 0; i < hurricanes.length; i++ )
            hurricanes[i] = arr[i];
    }
    
    /**
     * Sorts descending a portion of array based upon pressure, 
     * using selection sort.
     * 
     * @param   start   the first index to start the sort
     * @param   end     one past the last index to sort; hence, end position
     *                  is excluded in the sort
     */
    private void sortPressuresHelper (int start, int end)
    {
        // write this code
    }

    /**
     * Sorts ascending based upon wind speeds using a recursive merge sort. 
     */
    public void sortWindSpeeds(int low, int high)
    {
        if ( high - low == 0 )
            return;
        if ( high - low == 1 )
        {
            mergeWindSpeedsSortHelper(low, low, high);
        }
        int mid = (low + high) / 2;
        sortWindSpeeds(low, mid);
        sortWindSpeeds(mid + 1, high);
        mergeWindSpeedsSortHelper(low, mid, high);
    }

    /**
     * Merges two consecutive parts of an array, using wind speed as a criteria
     * and a temporary array.  The merge results in an ascending sort between
     * the two given indices.
     * 
     * @precondition the two parts are sorted ascending based upon wind speed
     * 
     * @param low   the starting index of one part of the array.
     *              This index is included in the first half.
     * @param mid   the starting index of the second part of the array.
     *              This index is included in the second half.
     * @param high  the ending index of the second part of the array.  
     *              This index is included in the merge.
     */
    private void mergeWindSpeedsSortHelper(int low, int mid, int high)
    {
        if ( low == mid )
        {
            if ( hurricanes[high].compareSpeedTo( hurricanes[low] ) < 0 )
            {
                Hurricane temp = hurricanes[high];
                hurricanes[high] = hurricanes[low];
                hurricanes[low] = temp;
            }
            return;
        }
        Hurricane[] arr = new Hurricane[mid - low + 1];
        for ( int i = 0; i < arr.length; i++ )
            arr[i] = hurricanes[low + i];
        int i1 = 0, i2 = mid + 1, i3 = 0;
        while ( i1 < arr.length && i2 <= high )
        {
            if ( arr[i1].comparePressureTo( hurricanes[i2] )  > 0 )
            {
                hurricanes[i3] = arr[i1];
                i1++;
            }
            else
            {
                hurricanes[i3] = hurricanes[i2];
                i2++;
            }
            i3++;
        }
        while ( i1 < arr.length )
        {
            hurricanes[i3] = arr[i1];
            i1++;
            i3++;
        }
    }

    /**
     * Sequential search for all the hurricanes in a given year.
     * 
     * @param   year
     * @return  an array of objects in Hurricane that occured in
     *          the parameter year
     */
    public Hurricane [] searchYear(int year)
    {
        int i = 0;
        for ( Hurricane h: hurricanes )
            if ( h.getYear() == year )
                i++;
        Hurricane[] hs = new Hurricane[i];
        i = 0;
        for ( Hurricane h: hurricanes )
            if ( h.getYear() == year )
            {
                hs[i] = h;
                i++;
            }
        return hs;
    }     

    /**
     * Binary search for a hurricane name.
     * 
     * @param  name   hurricane name being search
     * @return a Hurricane array of all objects in hurricanes with specified name. 
     *         Returns null if there are no matches
     */
    public Hurricane[ ] searchHurricaneName(String name)
    {
        sortNames();
        return searchHurricaneNameHelper(name, 0, hurricanes.length - 1);
    }

    /**
     * Recursive binary search for a hurricane name.  This is the helper
     * for searchHurricaneName.
     * 
     * @precondition  the array must be presorted by the hurricane names
     * 
     * @param   name  hurricane name to search for
     * @param   low   the smallest index that needs to be checked
     * @param   high  the highest index that needs to be checked
     * @return  a Hurricane array of all Hurricane objects with a specified name. 
     *          Returns null if there are no matches
     */
    private Hurricane[ ] searchHurricaneNameHelper(String name, int low , int high)
    {
        if ( high < low )
            return new Hurricane[0];
        if ( high == low )
        {
            if ( hurricanes[low].getName().equals( name ) )
                return new Hurricane[] { hurricanes[low] };
            return new Hurricane[0];
        }
        int mid = (low + high)/2;
        // Test for match
        if ( hurricanes[mid].getName().compareTo( name ) == 0 )
        {
            Hurricane[] a1 = searchHurricaneNameHelper( name, low, mid - 1 );
            Hurricane[] a2 = searchHurricaneNameHelper( name, mid + 1, high );
            Hurricane[] arr = new Hurricane[a1.length + a2.length + 1];
            for ( int i = 0; i < a1.length; i++ )
                arr[i] = a1[i];
            for ( int i = 0; i < a2.length; i++ )
                arr[i + a1.length] = a2[i];
            arr[a1.length + a2.length] = hurricanes[mid];
            return arr;
        }
        // Determine if the potential match is in the 
        // "first half" of the considered items in the array
        if ( hurricanes[mid].getName().compareTo( name ) > 0 )
            return searchHurricaneNameHelper( name, mid + 1, high );
        // The potential match must be in the
        // "second half" of the considered items in the array
        return searchHurricaneNameHelper( name, low, mid - 1 );
    }

    /**
     * Supports Binary Search method to get the full range of matches.
     * 
     * @precondition  the array must be presorted by the hurricane names
     * 
     * @param   name hurricane name being search for
     * @param   index  the index where a match was found
     * @return  a Hurricane array with objects from hurricanes with specified name. 
     *          Returns null if there are no matches
     */
    private Hurricane[ ] retrieveMatchedNames (String name, int index)
    {
        // Find the start where the matches start:

        
        // Find the end of the matches:

        
        // Copy the objects whose names match:

        return null;  // correct this line
    }

    /**
     * Comment this method even though you did not write it.
     */
    public void printHeader()
    {
        System.out.println("\n\n");
        System.out.printf("%-4s %-5s %-15s %-5s %-5s %-5s \n", 
            "Year", "Mon.", "Name", "Cat.", "Knots", "Pressure");
    }

    /**
     * Comment this method even though you did not write it.
     */
    public void printHurricanes()
    {
        printHurricanes(hurricanes);
    }

    /**
     * Add comments here even though you did not write the method.
     */
    public void printHurricanes(Hurricane [] hurs)
    {
        if(hurs.length == 0)
        {
            System.out.println("\nVoid of hurricane data.");
            return;
        }
        printHeader();
        for(Hurricane h: hurs)
        {
            System.out.println(h);
        }
    }

    /**
     * Add comments here even though you did not write the method.
     */
    public void printMenu()
    {
        System.out.println("\n\nEnter option: ");
        System.out.println("\t 1 - Print all hurricane data \n" +
            "\t 2 - Print maximum and minimum data \n" +
            "\t 3 - Print averages \n" +
            "\t 4 - Sort hurricanes by year \n" +
            "\t 5 - Sort hurricanes by name \n" +
            "\t 6 - Sort hurricanes by category, descending \n" +
            "\t 7 - Sort hurricanes by pressure, descending \n" +
            "\t 8 - Sort hurricanes by speed \n" + 
            "\t 9 - Search for hurricanes for a given year \n" +
            "\t10 - Search for a given hurricane by name \n" +
            "\t11 - Quit \n");
    }

    /**
     * Add comments here even though you did not write the method.
     */
    public void printMaxAndMin( )
    {
        System.out.println("Maximum wind speed is " + 
            findMaxWindSpeed( ) +
            " knots and minimum wind speed is " + 
            findMinWindSpeed( ) + " knots.");
        System.out.println("Maximum pressure is " + 
            findMaxPressure( ) +
            " and minimum pressure is " + 
            findMinPressure( ) + ".");
    }

    /**
     * Add comments here even though you did not write the method.
     */
    public void printAverages( )
    {
        System.out.printf("Average wind speed is %5.2f knots. \n" , 
            calculateAverageWindSpeed( ));
        System.out.printf("Average pressure is %5.2f. \n" , 
            calculateAveragePressure( ));
        System.out.printf("Average category is %5.2f. \n" , 
            calculateAverageCategory( ));
    }

    /**
     * Add comments here even though you did not write the method.
     */
    public boolean interactWithUser( )
    {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        printMenu();
        int choice = in.nextInt();
        // clear the input buffer
        in.nextLine();

        if(choice == 1)
        {
            printHurricanes( ); 
        }
        else if (choice == 2)
        {
            printMaxAndMin( );
        }
        else if (choice == 3)
        {
            printAverages( );
        }
        else if(choice == 4)
        {
            sortYears();
            printHurricanes( );
        }
        else if(choice == 5)
        {
            sortNames();
            printHurricanes( );
        }
        else if(choice == 6)
        {
            sortCategories();
            printHurricanes( );
        }
        else if(choice == 7)
        {
            sortPressures();
            printHurricanes( );
        }
        else if(choice == 8)
        {
            sortWindSpeeds(0, hurricanes.length - 1);
            printHurricanes( );
        }
        else if(choice == 9)
        {
            System.out.print("\n\tWhich year do you want to search for?\n\t");
            int year = in.nextInt();
            printHurricanes(searchYear(year));
        }
        else if(choice == 10)
        {
            System.out.print("\n\tWhich name do you want to search for?\n\t");
            String name = in.next();
            printHurricanes(searchHurricaneName(name));
        }
        else if (choice == 11)
        {
            done = true;
        }  
        return done;
    }

    /**
     * Comment the method even though you did not write it.
     * 
     * @param args  user's information from the command line
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     */
    public static void main (String [] args) throws IOException
    {
        HurricaneOrganizerArray cane = new HurricaneOrganizerArray("hurricanedata.txt");
        boolean areWeDoneYet = false;
        while ( ! areWeDoneYet)
        {
            areWeDoneYet = cane.interactWithUser( );    
        }
    }
}
