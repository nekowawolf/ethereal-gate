import greenfoot.*;
import greenfoot.Font;

public class ClearTimeMenuDisplay extends Actor
{
    public ClearTimeMenuDisplay()
    {
        String text = ClearTime.hasLastClear()
            ? ClearTime.formatMmSs(ClearTime.getLastClearMs())
            : "0:00";

        int width = 120;
        int height = 40;

        GreenfootImage img = new GreenfootImage(width, height);
        
        int fontSize = 18;
        img.setFont(new Font(fontSize));

        int textWidth = text.length() * (fontSize / 2);

        int x = (width - textWidth) / 2;
        int y = height / 2 + (fontSize / 2) - 8;

        img.setColor(new Color(0, 0, 0, 120));
        img.drawString(text, x + 1, y + 1);

        img.setColor(Color.WHITE);
        img.drawString(text, x, y);

        setImage(img);
    }
}