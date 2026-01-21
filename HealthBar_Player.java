import greenfoot.*;

public class HealthBar_Player extends Actor
{
    int width = 60; 
    int height = 8;
    Player owner;   
    int maxHealth = 10; 

    public HealthBar_Player(Player owner) {
        this.owner = owner;
        updateBar();
    }

    public void act() {
        if (owner != null && owner.getWorld() != null) {
            setLocation(owner.getX(), owner.getY() - 12);
            updateBar();
        } else {
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
        }
    }

    private void updateBar() {
        GreenfootImage img = new GreenfootImage(width + 2, height + 2);
        
        img.setColor(Color.BLACK);
        img.fillRect(0, 0, width + 2, height + 2);
        
        double healthRatio = (double) owner.health / maxHealth;
        int currentHealthWidth = (int) (healthRatio * width);
        
        if (healthRatio > 0.6) img.setColor(Color.GREEN);
        else if (healthRatio > 0.3) img.setColor(Color.YELLOW);
        else img.setColor(Color.RED);
        
        if (currentHealthWidth > 0) {
            img.fillRect(1, 1, currentHealthWidth, height);
        }
        
        setImage(img);
    }
}