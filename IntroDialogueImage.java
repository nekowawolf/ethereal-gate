import greenfoot.*;

public class IntroDialogueImage extends Actor
{
    public IntroDialogueImage()
    {
        GreenfootImage img = new GreenfootImage(SelectedCharacter.getDialogueImage());
        img.scale(1200, 675);
        setImage(img);
    }
}