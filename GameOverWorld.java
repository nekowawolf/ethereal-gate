import greenfoot.*;

public class GameOverWorld extends World
{
    public GameOverWorld()
    {    
        super(1200, 675, 1);

        // Game Over text
        addObject(new GameOver(), getWidth() / 2, 337);

        // Menu button
        addObject(new btnMenu(), getWidth() / 2, getHeight() / 2 + 50);
    }
}
