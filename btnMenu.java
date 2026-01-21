import greenfoot.*;

public class btnMenu extends Actor
{
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Main_Menu());
        }
    }
}
