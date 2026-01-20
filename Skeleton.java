import greenfoot.*;
import java.util.List;

public class Skeleton extends Actor {

    // ===== ANIMATION =====
    GreenfootImage[] idle;
    GreenfootImage[] walk;
    GreenfootImage[] attack;
    GreenfootImage[] shield;
    GreenfootImage[] takeHit;
    GreenfootImage[] death;

    int frame = 0;
    int timer = 0;
    int delay = 6;
    int deathWait = 30;

    // ===== STATE =====
    boolean facingRight = true;
    boolean isAttacking = false;
    boolean isTakingHit = false;
    boolean isDead = false;
    boolean isShielding = false;

    int health = 6;
    int shieldTimer = 0;

    public Skeleton() {
        // ===== LOAD IMAGES =====
        idle = loadImages("Skeleton_Idle", 4);
        walk = loadImages("Skeleton_Walk", 4);
        attack = loadImages("Skeleton_Attack", 8);
        shield = loadImages("Skeleton_Shield", 4);
        takeHit = loadImages("Skeleton_TakeHit", 4);
        death = loadImages("Skeleton_Death", 4);

        setImage(idle[0]);
    }

    public void act() {
        if (isDead) {
            animateDeath();
            return;
        }

        checkPlayerHit();
        handleShieldLogic();
        moveTowardsPlayer();
        animate();
    }

    // ===== LOAD IMAGES =====
    GreenfootImage[] loadImages(String folder, int count) {
        GreenfootImage[] imgs = new GreenfootImage[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = new GreenfootImage("Skeleton/" + folder + "/" + i + ".png");
            double scale = 1.7;
            imgs[i].scale(
                (int)(imgs[i].getWidth() * scale),
                (int)(imgs[i].getHeight() * scale)
            );
        }
        return imgs;
    }

    // ===== SHIELD LOGIC =====
    void handleShieldLogic() {
        if (isShielding) {
            shieldTimer--;
            if (shieldTimer <= 0) {
                isShielding = false;
            }
        } else if (!isAttacking && !isTakingHit) {
            List<Player> players = getWorld().getObjects(Player.class);
            if (!players.isEmpty()) {
                Player p = players.get(0);
                int distance = Math.abs(getX() - p.getX());

                if (distance < 150) {
                    if (Greenfoot.getRandomNumber(30) < 1) { 
                        isShielding = true;
                        shieldTimer = 60;
                        frame = 0;
                    }
                }
            }
        }
    }

    // ===== MOVEMENT =====
    void moveTowardsPlayer() {
        if (isAttacking || isTakingHit || isShielding) return;

        List<Player> players = getWorld().getObjects(Player.class);
        if (!players.isEmpty()) {
            Player p = players.get(0);
            int distance = getX() - p.getX();
            
            if (Math.abs(distance) > 45) {
                if (distance < 0) {
                    setLocation(getX() + 2, getY());
                    facingRight = true;
                } else {
                    setLocation(getX() - 2, getY());
                    facingRight = false;
                }
            } else {
                isAttacking = true;
                frame = 0;
            }
        }
    }

    // ===== TAKE DAMAGE =====
    public void takeHit() {
        if (isDead || isTakingHit) return;

        if (isShielding) {
            return; 
        }

        health--;
        isTakingHit = true;
        frame = 0;

        if (health <= 0) {
            isDead = true;
            frame = 0;
        }
    }

    // ===== COLLISION CHECK =====
    void checkPlayerHit() {
        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return;
        Player p = players.get(0);

        if (p.isAttacking()) {
            if (Math.abs(getX() - p.getX()) < 50 && Math.abs(getY() - p.getY()) < 50) {
                takeHit();
            }
        }
    }

    void damagePlayer() {
        List<Player> players = getWorld().getObjects(Player.class);
        if (!players.isEmpty()) {
            Player p = players.get(0);
            if (Math.abs(getX() - p.getX()) < 60 && Math.abs(getY() - p.getY()) < 60) {
                p.takeDamage();
            }
        }
    }

    // ===== ANIMATION =====
    void animate() {
        timer++;
        if (timer < delay) return;
        timer = 0;

        GreenfootImage img = null;

        if (isTakingHit) {
            img = takeHit[frame];
            frame++;
            if (frame >= takeHit.length) { frame = 0; isTakingHit = false; }
        } else if (isShielding) {
            img = shield[frame % shield.length];
            frame++;
        } else if (isAttacking) {
            img = attack[frame];
            if (frame == 5) damagePlayer();
            frame++;
            if (frame >= attack.length) { frame = 0; isAttacking = false; }
        } else {
            if (isMoving()) {
                frame = (frame + 1) % walk.length;
                img = walk[frame];
            } else {
                frame = (frame + 1) % idle.length;
                img = idle[frame];
            }
        }

        if (img != null) {
            GreenfootImage displayImg = new GreenfootImage(img);
            if (!facingRight) displayImg.mirrorHorizontally();
            setImage(displayImg);
        }
    }

    void animateDeath() {
        timer++;
        if (timer < delay) return;
        timer = 0;

        if (frame < death.length) {
            GreenfootImage displayImg = new GreenfootImage(death[frame]);
            if (!facingRight) displayImg.mirrorHorizontally();
            setImage(displayImg);
            frame++;
        } else if (deathWait > 0) {
            deathWait--;
        } else {
            getWorld().removeObject(this);
        }
    }

    boolean isMoving() {
        return !isAttacking && !isTakingHit && !isShielding;
    }
}