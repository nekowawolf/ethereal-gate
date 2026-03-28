import greenfoot.*;
import greenfoot.Font;

public class ClearTimeWinDisplay extends Actor
{
    public ClearTimeWinDisplay()
    {
        String text = ClearTime.formatMmSs(ClearTime.getLastClearMs());
        
        int width = 120;
        int height = 70;

        GreenfootImage img = new GreenfootImage(width, height);
        
        int fontSize = 20;
        img.setFont(new Font(fontSize));

        int textWidth = text.length() * (fontSize / 2);

        int x = (width - textWidth) / 2;
        int y = height / 2 + (fontSize / 2) - 28;

        img.setColor(new Color(0, 0, 0, 140));
        img.drawString(text, x + 2, y + 2);

        img.setColor(Color.WHITE);
        img.drawString(text, x, y);

        setImage(img);
    }
}