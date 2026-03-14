import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class btnHow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class btnHow extends Actor
{
    /**
     * Act - do whatever the btnHow wants to do. This method is called whenever
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
