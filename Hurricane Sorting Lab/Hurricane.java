import java.io.*;

/**
 * Models hurricane information, including categories.  
 * Works with HurricaneOrganizer, provides object and comparison skeletons.
 * 
 * @author Susan King
 * @version January 17, 2019
 */
public class Hurricane
{
    private int year;
    private String month;
    private int pressure;
    private int speed;
    private String name;
    private int category;

    /**
     * Initializes a Hurricane object with no information.
     */
    public Hurricane()
    {
    }

    /**
     * Initializes a Hurricane object with historical information.
     * 
     * @param year      year the hurricane took place
     * @param month     month in String format
     * @param pressure  hurricane's pressure
     * @param speed     hurricane's speed in knots
     * @param name      hurricane's name
     */
    public Hurricane(int year, String month, 
    int pressure, int speed, String name)
    {
        this.year = year;
        this.month = month;
        this.pressure = pressure;
        this.speed = speed;
        this.name = name;
        category = determineCategory( speed );
    }

    /**
     * Based upon Saffir/Simpson Hurricane Scale, figures out
     * the category using wind speed in knots.
     * 
     * Use https://en.wikipedia.org/wiki/Saffir%E2%80%93Simpson_scale.
     * 
     * @param knots     wind speed in knots
     * @return Saffir/Simpson Hurricane Scale category
     */
    public int determineCategory(int knots)
    {
        if ( knots <= 63 )
            return 0;
        if ( knots <= 82 )
            return 1;
        if ( knots <= 95 )
            return 2;
        if ( knots <= 112 )
            return 3;
        if ( knots <= 136 )
            return 4;
        return 5;
    }

    //Getters

    /**
     * Comment this method.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Comment this method.
     */
    public String getMonth()
    {
        return month;
    }

    /**
     * Comment this method.
     */
    public int getPressure()
    {
        return pressure;
    }

    /**
     * Comment this method.
     */
    public int getSpeed()
    {
        return speed;
    }

    /**
     * Comment this method.
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Comment this method.
     */
    public int getCategory()
    {
        return category;
    }

    /**
     * Comment this method even though you did not write it.
     */
    public void print()
    {
        System.out.println( toString() );
    }

    /**
     * Alter code a bit then comment this method even though you did not write it.
     */
    public String toString()
    {
        return String.format("%-4d %-5s %-15s %-5d %5d %5d ", 
               year, month, name, category, speed, pressure);
    }

    /**
     * Comment this method.
     */
    public int compareYearTo(Hurricane h)
    {
        return year - h.getYear();
    }

    /**
     * Comment this method.
     */
    public int compareNameTo(Hurricane h)
    {
        return name.compareTo( h.getName() );
    }

    /**
     * Comment this method.
     */
    public int comparePressureTo(Hurricane h)
    {
        return pressure - h.getPressure();
    }

    /**
     * Comment this method.
     */
    public int compareSpeedTo(Hurricane h)
    {
        return speed - h.getSpeed();
    }

    /**
     * Comment this method.
     */
    public int compareCategoryTo(Hurricane h)
    {
        return category - h.getCategory();
    }
}
