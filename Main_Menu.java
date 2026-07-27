import greenfoot.*;

public class Main_Menu extends World
{
    private static final int BACKGROUND_FRAME_COUNT = 78;
    private static final int BACKGROUND_ANIMATION_DELAY = 5;
    private GreenfootImage[] backgroundFrames;
    private int backgroundFrameIndex = 0;
    private int backgroundFrameTimer = 0;

    private GreenfootSound bgm;

    public Main_Menu()
    {    
        super(1200, 675, 1, false);

        backgroundFrames = loadBackgroundFrames("bg-menu_frames", "bg-menu");
        setBackground(backgroundFrames[0]);

        OverlayImage overlay = new OverlayImage("bg-menu-text.png");
        addObject(overlay, getWidth() / 2, getHeight() / 2);

        bgm = new GreenfootSound("bgm.mp3");
        bgm.setVolume(40);

        btnStart startButton = new btnStart();
        addObject(startButton, getWidth() / 2, getHeight() / 2 + 100);

        btnHow howButton = new btnHow();
        addObject(howButton, getWidth() / 2, getHeight() / 2 + 180);

        addObject(new ClearTimeMenuDisplay(), getWidth() / 2, getHeight() - 40);
    }

    public void showHowToPlay()
    {
        HowToPlay popup = new HowToPlay();
        addObject(popup, getWidth()/2, getHeight()/2);

        btnClose close = new btnClose();
        addObject(close, getWidth()/2 + 400, getHeight()/2 - 170);
    }

    public void act()
    {
        updateBackground();
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

    private void updateBackground()
    {
        if (backgroundFrames == null || backgroundFrames.length == 0) {
            return;
        }

        backgroundFrameTimer++;
        if (backgroundFrameTimer < BACKGROUND_ANIMATION_DELAY) {
            return;
        }
        backgroundFrameTimer = 0;

        setBackground(backgroundFrames[backgroundFrameIndex]);
        backgroundFrameIndex = (backgroundFrameIndex + 1) % BACKGROUND_FRAME_COUNT;
    }

    private GreenfootImage[] loadBackgroundFrames(String folderName, String filePrefix)
    {
        GreenfootImage[] frames = new GreenfootImage[BACKGROUND_FRAME_COUNT];

        for (int i = 0; i < BACKGROUND_FRAME_COUNT; i++) {
            String filename = String.format("%s_%03d.jpg", filePrefix, i + 1);
            GreenfootImage img = new GreenfootImage(folderName + "/" + filename);
            img.scale(1200, 675);
            frames[i] = img;
        }

        return frames;
    }
}