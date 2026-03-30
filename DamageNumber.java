import greenfoot.*; 
import greenfoot.Font;

public class DamageNumber extends Actor
{
    private static final int RISE_PER_FRAME = 2;
    private static final int LIFETIME = 50;

    private int life = LIFETIME;

    private final Color textColor;

    public DamageNumber(int displayAmount, Color color)
    {
        this.textColor = (color == null) ? Color.RED : color;

        String text = String.valueOf(displayAmount);
        int fontSize = 22;

        Font font = new Font(fontSize);

        int w = Math.max(48, text.length() * 16 + 10);
        int h = 34;

        GreenfootImage img = new GreenfootImage(w, h);
        img.setFont(font);

        img.setColor(Color.BLACK);
        img.drawString(text, 4, 27);

        img.setColor(textColor);

        img.drawString(text, 2, 25);

        setImage(img);
    }

    public void act()
    {
        World w = getWorld();
        if (w == null)
        {
            return;
        }

        setLocation(getX(), getY() - RISE_PER_FRAME);

        life--;

        if (life <= 0)
        {
            w.removeObject(this);
        }
    }

    public static void spawn(Actor host, int damage)
    {
        World world = host.getWorld();
        if (world == null)
        {
            return;
        }

        // Display-only random number (does NOT affect HP logic).
        int displayAmount = Greenfoot.getRandomNumber(21) + 10; // 10 - 30
        Color color = (Greenfoot.getRandomNumber(2) == 0) ? Color.RED : Color.YELLOW;

        world.addObject(new DamageNumber(displayAmount, color), host.getX(), host.getY() - 38);
    }
}