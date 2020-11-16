package Pacman;

import java.util.ArrayList;

/**
 * Pacman.Speedy is the pink ghost
 * Pacman.Speedy moves randomly and has the fastest move speed
 */
public class Speedy extends Ghost {

    public Speedy(PacmanGame game) {
        super(game);
        dir = Location.Direction.DOWN;
        color = "pink";
        defaultSpeeds = new double[]{0.1, 0.05, 0.15};
    }

    public void move() {
        Location[] locs = game.getAdjacent(loc);
        ArrayList<Integer> a1 = new ArrayList<Integer>(4);
        for ( int i = 1; i <= 4; i++ )
            a1.add(i);
        ArrayList<Integer> a2 = new ArrayList<Integer>(4);
        for ( int i = 4; i > 0; i-- )
            a2.add( a1.remove((int) (Math.random() * i)) );

        while ( locs[a2.get(0)] == null )
            a2.remove(0);

        dir = dirs[a2.get(0)];
        loc = locs[a2.get(0)];
    }
}
