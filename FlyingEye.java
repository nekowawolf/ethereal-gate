import greenfoot.*;
import java.util.List;

public class FlyingEye extends Actor
{
    // ===== ANIMATION =====
    GreenfootImage[] flight;
    GreenfootImage[] attack;
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

    int health = 10;

    public FlyingEye() {
        // ===== LOAD IMAGES =====
        flight = loadImages("Eye_Flight", 8);
        attack = loadImages("Eye_Attack", 8);
        takeHit = loadImages("Eye_TakeHit", 4);
        death = loadImages("Eye_Death", 4);

        setImage(flight[0]);
    }

    public void act() {
        if (isDead) {
            animateDeath();
            return;
        }

        checkPlayerHit();
        moveTowardsPlayer();
        animate();
    }

    // ===== LOAD IMAGES =====
    GreenfootImage[] loadImages(String folder, int count) {
        GreenfootImage[] imgs = new GreenfootImage[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = new GreenfootImage("flyingEye/" + folder + "/" + i + ".png");
            imgs[i].scale(imgs[i].getWidth() * 2, imgs[i].getHeight() * 2);
        }
        return imgs;
    }

    // ===== PLAYER DETECTION =====
    void moveTowardsPlayer() {
        if (isAttacking || isTakingHit) return;

        World world = getWorld();
        if (world != null) {
            List<Player> players = world.getObjects(Player.class);
            if (!players.isEmpty()) {
                Player p = players.get(0);
                
                int diffX = getX() - p.getX();
                int diffY = getY() - p.getY();
                
                if (Math.abs(diffX) > 45) {
                    if (diffX < 0) {
                        setLocation(getX() + 3, getY());
                        facingRight = true;
                    } else {
                        setLocation(getX() - 3, getY());
                        facingRight = false;
                    }
                } 
                
                if (Math.abs(diffY) > 5) {
                    if (diffY < 0) setLocation(getX(), getY() + 1);
                    else setLocation(getX(), getY() - 1);
                }

                if (Math.abs(diffX) < 50 && Math.abs(diffY) < 50) {
                    isAttacking = true;
                    frame = 0;
                    timer = 0;
                }
            }
        }
    }

    // ===== TAKE DAMAGE =====
    public void takeHit() {
        if (isDead || isTakingHit) return;

        health--;
        isTakingHit = true;
        frame = 0;
        timer = 0;

        if (health <= 0) {
            isDead = true;
            frame = 0;
            timer = 0;
        }
    }

    // ===== CHECK PLAYER ATTACK =====
    void checkPlayerHit() {
        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return;
        Player p = players.get(0);

        if (p.isAttacking()) {
            if (Math.abs(getX() - p.getX()) < 60 && Math.abs(getY() - p.getY()) < 60) {
                takeHit();
            }
        }
    }

    // ===== DAMAGE PLAYER =====
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
            if (frame >= takeHit.length) {
                frame = 0;
                isTakingHit = false;
            }
        } else if (isAttacking) {
            img = attack[frame];
            
            if (frame == 4) { 
                damagePlayer(); 
            }
            
            frame++;
            if (frame >= attack.length) {
                frame = 0;
                isAttacking = false;
            }
        } else {
            frame = (frame + 1) % flight.length;
            img = flight[frame];
        }

        if (img != null) {
            GreenfootImage displayImg = new GreenfootImage(img);
            if (!facingRight) {
                displayImg.mirrorHorizontally();
            }
            setImage(displayImg);
        }
    }

    // ===== DEATH ANIMATION =====
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
            setLocation(getX(), getY() + 2);
            deathWait--;
        } else {
            getWorld().removeObject(this);
        }
    }
}