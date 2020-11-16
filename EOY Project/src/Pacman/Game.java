package Pacman;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;
/**
 * Abstract class Pacman.Game - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Game
{
    private boolean isPaused;
    private boolean hasEnded;
    
    public int level;
    public int score;
    
    Date startTime;
    Date endTime;
    
    JComponent display;
    
    public void pause()
    {
        isPaused = !isPaused;
    }
    
    public boolean isPaused()
    {
        return isPaused;
    }
    
    public boolean hasEnded()
    {
        return hasEnded;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public int getTimeElapsed()
    {
        if (endTime == null)
        {
            Date now = new Date();
            return (int) (now.getTime() - startTime.getTime());
        }
        else
        {
            return (int) (endTime.getTime() - startTime.getTime());
        }
    }
    
    public abstract void play();
}

