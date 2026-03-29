import greenfoot.*; 
import greenfoot.Font;

public class DamageNumber extends Actor
{
    private static final int RISE_PER_FRAME = 2;
    private static final int LIFETIME = 50;

    private int life = LIFETIME;

    public DamageNumber(int damageAmount)
    {
        String text = String.valueOf(damageAmount);
        int fontSize = 22;

        Font font = new Font(fontSize);

        int w = Math.max(48, text.length() * 16 + 10);
        int h = 34;

        GreenfootImage img = new GreenfootImage(w, h);
        img.setFont(font);

        img.setColor(Color.BLACK);
        img.drawString(text, 4, 27);

        if (damageAmount > 20)
        {
            img.setColor(Color.ORANGE); 
        }
        else
        {
            img.setColor(Color.RED);
        }

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

        int randomDamage = Greenfoot.getRandomNumber(21) + 10;

        int offsetX = Greenfoot.getRandomNumber(21) - 10;

        world.addObject(
            new DamageNumber(randomDamage),
            host.getX() + offsetX,
            host.getY() - 38
        );
    }
}