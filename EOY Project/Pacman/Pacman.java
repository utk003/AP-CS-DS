package Pacman;

public class Pacman extends BoardPlayer {
    
    private static final double MOVE_DISTANCE = 0.1;
    
    public Pacman(PacmanGame game) {
        super(game);
        loc = new Location(-1, -1);
        dir = Location.Direction.NONE;
    }

    public Location move(Location.Direction direction) 
    {
        BoardPiece[][] board = game.board;
        int intRow;
        int intCol;
        if (direction == Location.Direction.LEFT)
        {
            intRow = (int) (loc.getRow());
            intCol = (int) (loc.getCol() + 1 + MOVE_DISTANCE);
            if (intCol < 30 && board[intRow][intCol] == null)
            {
                loc = new Location(intRow, loc.getCol() + MOVE_DISTANCE);
            }
        }
        else if (direction == Location.Direction.RIGHT)
        {
            intRow = (int) (loc.getRow());
            intCol = (int) Math.floor(loc.getCol() - 1 - MOVE_DISTANCE);
            if (intCol >= 0 && board[intRow][intCol] == null)
            {
                loc = new Location(intRow, loc.getCol() - MOVE_DISTANCE);
            }
        }
        else if (direction == Location.Direction.UP)
        {
            intRow = (int) Math.floor(loc.getRow() - 1 - MOVE_DISTANCE);
            intCol = (int) (loc.getCol());
            if (intRow >= 0 && board[intRow][intCol] == null)
            {
                loc = new Location(loc.getRow() - MOVE_DISTANCE, intCol);
            }
        }
        else if (direction == Location.Direction.DOWN)
        {
            intRow = (int) Math.floor(loc.getRow() + 1 + MOVE_DISTANCE);
            intCol = (int) (loc.getCol());
            if (intRow < 30 && board[intRow][intCol] == null)
            {
                loc = new Location(loc.getRow() + MOVE_DISTANCE, intCol);
            }
        }
        return loc;
    }
}
