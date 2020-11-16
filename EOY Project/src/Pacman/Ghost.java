package Pacman;

import java.awt.*;

public abstract class Ghost extends BoardPlayer {
    protected double speed;
    protected State state;

    // Fill in later
    protected double[] defaultSpeeds;

    // Sprites Array
    // U1, U2, R1, R2, D1, D2, L1, L2, ScareB1, ScareB2, ScareW1, ScareW2, EyesU, EyesR, EyesD, EyesL

    protected String color;

    protected enum State {
        Alive, Scared, Dead
    }

    public Ghost(PacmanGame game) {
        super(game);
        loc = new Location(-1, -1);
        state = State.Alive;
    }

    public void updateState(State s) {
        if (state == s)
            return;
        if (s == State.Alive)
            speed = defaultSpeeds[0];
        else if (s == State.Scared)
            speed = defaultSpeeds[1];
        else
            speed = defaultSpeeds[2];
        state = s;
    }
    /*
    public Image getImage() {
        if ( state == State.Scared )
            return sprites[]
    }
    */
}
