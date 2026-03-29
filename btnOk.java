import greenfoot.*;

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
            arena world = (arena)getWorld();
            world.resumeGame();

            getWorld().removeObject(this);
        }
    }
}