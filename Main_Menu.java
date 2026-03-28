import greenfoot.*;

public class Main_Menu extends World
{
    private GreenfootSound bgm;

    public Main_Menu()
    {    
        super(1200, 675, 1);

        bgm = new GreenfootSound("bgm.mp3");
        bgm.setVolume(40);

        btnStart startButton = new btnStart();
        addObject(startButton, getWidth() / 2, getHeight() / 2 + 100);

        btnHow howButton = new btnHow();
        addObject(howButton, getWidth() / 2, getHeight() / 2 + 180);
    }

    public void showHowToPlay()
    {
        HowToPlay popup = new HowToPlay();
        addObject(popup, getWidth()/2, getHeight()/2);

        btnClose close = new btnClose();
        addObject(close, getWidth()/2 + 400, getHeight()/2 - 170);
    }

    public void backToMenu()
    {
        removeObjects(getObjects(HowToPlay.class));
        removeObjects(getObjects(btnClose.class));
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