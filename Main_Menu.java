import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class main_menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main_Menu extends World
{
    public Main_Menu()
    {    
        super(1200, 675, 1);

        btnStart startButton = new btnStart();
        addObject(startButton, getWidth() / 2, getHeight() / 2 + 100);
    }
}
