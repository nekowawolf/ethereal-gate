import greenfoot.*;

public class btnEnterGate extends Actor
{
    public btnEnterGate()
    {
        setImage(new GreenfootImage("btnEnterGate.png"));
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new arena());
        }
    }
}

