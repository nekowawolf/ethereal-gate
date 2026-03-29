import greenfoot.*;

public class IntroDialogueWorld extends World
{
    public IntroDialogueWorld()
    {
        super(1200, 675, 1);

        GreenfootImage bg = new GreenfootImage("Dialogue_Player1.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);

        addObject(new btnOk(), 859, 579);
    }
}