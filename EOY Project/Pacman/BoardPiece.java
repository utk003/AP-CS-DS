package Pacman;

import java.awt.*;

public abstract class BoardPiece {
    protected BoardPiece.Direction dir;

    public enum Direction {
        PELLET, UP_DOWN, LEFT_RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
    }

    public Image getImage()
    {
        throw new RuntimeException("IMPLEMENT ME");
    }
    
    public Direction getDirection() {
        return dir;
    }
}
