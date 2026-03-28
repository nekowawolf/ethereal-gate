import greenfoot.*;

public class WinGameWorld extends World
{
    public WinGameWorld()
    {
        super(1200, 675, 1);

        ClearTime.finishRun();

        addObject(new WinGame(), getWidth() / 2, 337);
        addObject(new ClearTimeWinDisplay(), getWidth() / 2, 405);
        addObject(new btnMenu(), getWidth() / 2, 460);
    }
}