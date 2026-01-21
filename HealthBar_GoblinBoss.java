import greenfoot.*;

public class HealthBar_GoblinBoss extends Actor {
    
    // ===== REFERENCE =====
    Goblin_Boss boss;

    // ===== SETTINGS =====
    int maxHealth;
    int barWidth = 400; 
    int barHeight = 25;  
    
    public HealthBar_GoblinBoss(Goblin_Boss boss) {
        this.boss = boss;
        this.maxHealth = boss.health; 
        updateHealthBar();
    }

    public void act() {
        if (boss.getWorld() != null && !boss.isDead) {
            updateHealthBar();
        } 
        else {
            getWorld().removeObject(this);
        }
    }

    void updateHealthBar() {
        GreenfootImage img = new GreenfootImage(barWidth + 4, barHeight + 35);
        
        img.setColor(Color.WHITE);
        img.setFont(new Font("Arial", true, false, 24));
        
        String name = "Orc";
        int textWidth = new GreenfootImage(name, 24, Color.WHITE, new Color(0,0,0,0)).getWidth();
        img.drawString(name, (barWidth - textWidth) / 2, 22);

        img.setColor(Color.GRAY);
        img.fillRect(0, 30, barWidth + 4, barHeight + 4);
        
        img.setColor(Color.BLACK);
        img.fillRect(2, 32, barWidth, barHeight);

        int currentHp = Math.max(0, boss.health); 
        
        double percent = (double) currentHp / (double) maxHealth;
        int fillWidth = (int) (percent * barWidth);
        
        img.setColor(Color.RED);
        img.fillRect(2, 32, fillWidth, barHeight);

        setImage(img);
    }
}