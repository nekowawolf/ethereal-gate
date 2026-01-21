import greenfoot.*;

public class Main_Menu extends World
{
    private GreenfootSound bgm;

    public Main_Menu()
    {    
        super(1200, 675, 1);

        bgm = new GreenfootSound("bgm.mp3");
        bgm.setVolume(50);

        btnStart startButton = new btnStart();
        addObject(startButton, getWidth() / 2, getHeight() / 2 + 100);
    }

    public void started()
    {
        bgm.playLoop();
    }

    public void stopped()
    {
        bgm.stop();
    }
}
