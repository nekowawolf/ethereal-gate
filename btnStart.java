import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class btnStart here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class btnStart extends Actor
{
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new arena());
        }
    }
}
