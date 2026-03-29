import greenfoot.*;

/**
 * Confirms the intro dialogue and starts arena 1.
 */
public class btnOk extends Actor
{
    public btnOk()
    {
        setImage(new GreenfootImage("btnOk.png"));
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new arena());
        }
    }
}
