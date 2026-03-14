import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class How_to_play here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HowToPlay extends Actor
{
    /**
     * Act - do whatever the How_to_play wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
     public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Main_Menu menu = (Main_Menu)getWorld();
            menu.showHowToPlay();
        }
    }
}
