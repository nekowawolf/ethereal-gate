import greenfoot.*;

public class GameOverWorld extends World
{
    private static final int BACKGROUND_FRAME_COUNT = 78;
    private static final int BACKGROUND_ANIMATION_DELAY = 5;;
    private GreenfootImage[] backgroundFrames;
    private int backgroundFrameIndex = 0;
    private int backgroundFrameTimer = 0;

    public GameOverWorld()
    {    
        super(1200, 675, 1, false);

        backgroundFrames = loadBackgroundFrames("GameOver_frames", "GameOver");
        setBackground(backgroundFrames[0]);

        // Game Over text
        addObject(new GameOver(), getWidth() / 2, 337);

        // Menu button
        addObject(new btnMenu(), getWidth() / 2, getHeight() / 2 + 50);
    }

    public void act()
    {
        updateBackground();
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
