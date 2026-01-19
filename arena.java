import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class arena here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class arena extends World
{

    /**
     * Constructor for objects of class arena.
     * 
     */
    public arena()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 675, 1);
        addObject(new Player(), 600, 540);
        addObject(new Goblin(), 800, 570);
    }
}
