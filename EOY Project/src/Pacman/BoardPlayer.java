package Pacman;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BoardPlayer {
    protected Location.Direction dir;
    protected Location loc;
    protected Location.Direction[] dirs =
            {Location.Direction.UP, Location.Direction.RIGHT, Location.Direction.DOWN, Location.Direction.LEFT};
    protected PacmanGame game;

    protected int spriteCounter;

    protected Image[] sprites;

    public BoardPlayer(PacmanGame game) {
        this.game = game;
        spriteCounter = 0;
    }

    public Image getImage() {return new BufferedImage(1,1,1);}

    public Location getLocation() {
        return loc;
    }
    public Location.Direction getDirectionFacing() {
        return dir;
    }
}
