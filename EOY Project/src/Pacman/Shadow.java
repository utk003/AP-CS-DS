package Pacman;

/**
 * Pacman.Shadow is the Red ghost
 * Pacman.Shadow just follows the player
 */
public class Shadow extends Ghost {

    public Shadow(PacmanGame game) {
        super(game);
        dir = Location.Direction.UP;
        color = "red";
        defaultSpeeds = new double[]{0.075, 0.05, 0.15};
    }

    public void move() {
        Location[] locs = game.getAdjacent(loc);
        double minDist = 100;
        int minDistIndex = -1;
        for ( int i = 0; i < locs.length; i++ ) {
            if ( locs[i] != null ) {
                double dist = loc.getDistanceTo(locs[i]);
                if (dist < minDist) {
                    minDist = dist;
                    minDistIndex = i;
                }
            }
        }
        loc = locs[minDistIndex];
        dir = dirs[minDistIndex];
    }
}
