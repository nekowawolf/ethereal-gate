import greenfoot.*;

public class Portal extends Actor {

    // ===== ANIMATION =====
    GreenfootImage[] images;
    int frame = 0;
    int timer = 0;
    int delay = 5;

    // ===== TARGET WORLD =====
    World targetWorld;

    // ===== CONSTRUCTOR =====
    public Portal(World target) {
        targetWorld = target;

        images = new GreenfootImage[8];
        for (int i = 0; i < 8; i++) {
            images[i] = new GreenfootImage("portal/" + i + ".png");
        }
        setImage(images[0]);
    }

    public void act() {
        animate();
        checkPlayer();
    }

    // ===== ANIMATION =====
    void animate() {
        timer++;
        if (timer < delay) return;
        timer = 0;

        frame = (frame + 1) % images.length;
        setImage(images[frame]);
    }

    // ===== CHECK PLAYER =====
    void checkPlayer() {
        Player p = (Player) getOneIntersectingObject(Player.class);
        if (p == null) return;

        int dx = Math.abs(getX() - p.getX());
        int dy = Math.abs(getY() - p.getY());

        int centerX = 10;
        int centerY = 20;

        if (dx <= centerX && dy <= centerY) {
            Greenfoot.setWorld(targetWorld);
        }
    }
}