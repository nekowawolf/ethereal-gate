import greenfoot.*;

public class arena5 extends World
{
    public arena5()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, Goblin.class, Portal.class);

        addObject(new Player(), 200, 540);
        addObject(new Goblin(), 1300, 570);
    }
}