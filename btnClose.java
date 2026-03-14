import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class btnClose here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class btnClose extends Actor
{
    /**
     * Act - do whatever the btnClose wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Main_Menu menu = (Main_Menu)getWorld();
            menu.backToMenu();
        }
    }
}
