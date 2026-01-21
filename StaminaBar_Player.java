import greenfoot.*;

public class StaminaBar_Player extends Actor {

    private Player player;
    private int width = 40;
    private int height = 5;

    public StaminaBar_Player(Player player) {
        this.player = player;
        updateBar();
    }

    public void act() {
        updateBar();
        followPlayer();
    }

    void updateBar() {
        int stamina = player.getStamina();
        int max = player.getMaxStamina();

        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(Color.DARK_GRAY);
        img.fill();

        int barWidth = (int)((double) stamina / max * width);
        img.setColor(Color.YELLOW);
        img.fillRect(0, 0, barWidth, height);

        setImage(img);
    }

    void followPlayer() {
        setLocation(player.getX(), player.getY() - 4);
    }
}