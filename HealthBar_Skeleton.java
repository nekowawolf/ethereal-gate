import greenfoot.*;

public class HealthBar_Skeleton extends Actor
{
    int width = 45;
    int height = 6;
    Skeleton owner;
    int maxHealth = 5; 

    public HealthBar_Skeleton(Skeleton owner) {
        this.owner = owner;
        updateBar();
    }

    public void act() {
        if (owner != null && owner.getWorld() != null) {
            // ===== FOLLOW POSITION =====
            setLocation(owner.getX(), owner.getY() - 51);
            updateBar();
        } else {
            // ===== AUTO REMOVE =====
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
        }
    }

    private void updateBar() {
        GreenfootImage img = new GreenfootImage(width + 2, height + 2);
        
        // ===== BACKGROUND =====
        img.setColor(Color.BLACK);
        img.fillRect(0, 0, width + 2, height + 2);
        
        double healthRatio = (double) owner.health / maxHealth;
        int currentHealthWidth = (int) (healthRatio * width);
        
        // ===== COLOR LOGIC =====
        img.setColor(Color.RED);
        
        if (currentHealthWidth > 0) {
            img.fillRect(1, 1, currentHealthWidth, height);
        }
        
        setImage(img);
    }
}