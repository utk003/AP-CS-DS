package Pacman;

public class Location implements Comparable<Location>
{
    private double row; // row location in grid
    private double col; // column location in grid

    public enum Direction {
        LEFT, RIGHT, UP, DOWN, NONE
    }

    public Location(double r, double c)
    {
        row = r;
        col = c;
    }

    public double getRow() {
        return row;
    }

    public double getCol() {
        return col;
    }

    public double getDistanceTo(Location loc) {
        double xDist = row - loc.row, yDist = col - loc.col;
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return Math.abs(row - otherLoc.row) < 0.1 && Math.abs(col - otherLoc.col) < 0.1;
    }

    public int hashCode()
    {
        return (int) (row * 3737 + col);
    }

    public int compareTo(Location loc)
    {
        return hashCode() - loc.hashCode();
    }

    @Override
    public String toString()
    {
        return "(" + row + ", " + col + ")";
    }
}