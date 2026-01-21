import greenfoot.*;

public class WinGameWorld extends World
{
    public WinGameWorld()
    {
        super(1200, 675, 1);

        addObject(new WinGame(), getWidth() / 2, 337);
        addObject(new btnMenu(), getWidth() / 2, 380);
    }
}