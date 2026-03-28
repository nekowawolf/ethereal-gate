import greenfoot.*;
import greenfoot.Font;

public class ClearTimeDisplay extends Actor
{
    private static final int FONT_SIZE = 26;
    private String lastText = "";

    public ClearTimeDisplay()
    {
        updateImage();
    }

    public void act()
    {
        if (ClearTime.isRunActive())
        {
            updateImage();
        }
    }

    private void updateImage()
    {
        String text = ClearTime.formatMmSs(ClearTime.getElapsedMs());
        if (text.equals(lastText))
        {
            return;
        }
        lastText = text;

        GreenfootImage img = new GreenfootImage(160, 40);
        
        img.setFont(new Font(FONT_SIZE)); 
        
        img.setColor(new Color(0, 0, 0, 120));
        img.drawString(text, 3, 29);
        
        img.setColor(Color.WHITE);
        img.drawString(text, 2, 28);
        
        setImage(img);
    }
}