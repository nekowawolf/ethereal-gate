import greenfoot.*;
import java.util.List;

public class Goblin extends Actor {

    // ===== ANIMATION =====
    GreenfootImage[] idle;
    GreenfootImage[] run;
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

    int health = 5;
    int damage = 1;

    public Goblin() {
        // ===== LOAD IMAGES =====
        idle = loadImages("goblin_idle", 4);
        run = loadImages("goblin_run", 8);
        attack = loadImages("goblin_attack", 8);
        takeHit = loadImages("goblin_takehit", 4);
        death = loadImages("goblin_death", 4);

        setImage(idle[0]);
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
            imgs[i] = new GreenfootImage("goblin/" + folder + "/" + i + ".png");
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
                
                int distance = getX() - p.getX();
                
                if (Math.abs(distance) > 40) {
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
            int xDist = getX() - p.getX();
            int yDist = Math.abs(getY() - p.getY());
            
            boolean playerFacingThis = (p.getX() < getX() && p.isFacingRight()) || 
                                      (p.getX() > getX() && !p.isFacingRight());
            
            if (Math.abs(xDist) < 50 && yDist < 50 && playerFacingThis) {
                takeHit();
            }
        }
    }

    // ===== DAMAGE PLAYER =====
    void damagePlayer() {
        java.util.List<Player> players = getWorld().getObjects(Player.class);
        if (!players.isEmpty()) {
            Player p = players.get(0);
            if (Math.abs(getX() - p.getX()) < 60 && Math.abs(getY() - p.getY()) < 60) {
                p.takeDamage(damage);
            }
        }
    }

    // ===== ANIMATION =====
    void animate() {
        timer++;
        if (timer < delay) return;
        timer = 0;

        GreenfootImage img = null;

        if (isDead) return; 

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
            if (isMoving()) {
                frame = (frame + 1) % run.length;
                img = run[frame];
            } else {
                frame = (frame + 1) % idle.length;
                img = idle[frame];
            }
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
            deathWait--;
        } else {
            getWorld().removeObject(this);
        }
    }

    // ===== ADD HEALTH BAR =====
    protected void addedToWorld(World world) {
        world.addObject(new HealthBar_Goblin(this), getX(), getY() - 27);
    }

    boolean isMoving() {
        return !isAttacking && !isTakingHit;
    }
}