import greenfoot.*;
import java.util.List;

public class Goblin_Boss extends Actor {

    // ===== ANIMATION ARRAYS =====
    GreenfootImage[] idle;
    GreenfootImage[] walk;
    GreenfootImage[] attack;
    GreenfootImage[] death;

    // ===== ANIMATION TIMERS =====
    int frame = 0;
    int timer = 0;
    int delay = 5;
    int deathWait = 60;

    // ===== STATE FLAGS =====
    boolean facingRight = false;
    boolean isAttacking = false;
    boolean isTakingHit = false;
    boolean isDead = false;

    // ===== STATS =====
    int health = 20;
    int speed = 1;
    int attackRange = 100;
    int hitCooldown = 0;

    public Goblin_Boss() {
        idle = loadImages("idle", 21);
        walk = loadImages("walk", 10);
        attack = loadImages("attack", 23);
        death = loadImages("die", 11);

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

    // ===== LOAD IMAGES HELPER =====
    GreenfootImage[] loadImages(String folder, int count) {
        GreenfootImage[] imgs = new GreenfootImage[count];
        for (int i = 0; i < count; i++) {
            String fileName = String.format("goblinBoss/%s/%02d.png", folder, i);
            imgs[i] = new GreenfootImage(fileName);
            imgs[i].scale(
                imgs[i].getWidth(),
                imgs[i].getHeight()
            );
        }
        return imgs;
    }

    // ===== MOVEMENT AI =====
    void moveTowardsPlayer() {
        if (isAttacking || isDead) return;

        World world = getWorld();
        if (world != null) {
            List<Player> players = world.getObjects(Player.class);
            if (!players.isEmpty()) {
                Player p = players.get(0);

                int distanceX = getX() - p.getX();
                int distanceY = Math.abs(getY() - p.getY());

                if (Math.abs(distanceX) > attackRange) {
                    if (distanceX < 0) {
                        setLocation(getX() + speed, getY());
                        facingRight = true;
                    } else {
                        setLocation(getX() - speed, getY());
                        facingRight = false;
                    }
                } else if (distanceY < 50) {
                    startAttack();
                }
            }
        }
    }

    // ===== COMBAT SYSTEM =====
    void startAttack() {
        isAttacking = true;
        frame = 0;
        timer = 0;
    }

    void damagePlayer() {
        List<Player> players = getWorld().getObjects(Player.class);
        if (!players.isEmpty()) {
            Player p = players.get(0);
            if (Math.abs(getX() - p.getX()) < attackRange + 20 &&
                Math.abs(getY() - p.getY()) < 60) {
                p.takeDamage();
            }
        }
    }

    void checkPlayerHit() {
        if (hitCooldown > 0) {
            hitCooldown--;
            return;
        }

        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return;
        Player p = players.get(0);

        if (p.isAttacking()) {
            int xDist = getX() - p.getX();
            int yDist = Math.abs(getY() - p.getY());

            boolean playerFacingBoss =
                (p.getX() < getX() && p.isFacingRight()) ||
                (p.getX() > getX() && !p.isFacingRight());

            if (Math.abs(xDist) < 100 && yDist < 80 && playerFacingBoss) {
                takeDamage(1);
            }
        }
    }

    public void takeDamage(int dmg) {
        if (isDead) return;

        health -= dmg;
        hitCooldown = 20;

        if (health <= 0) {
            isDead = true;
            frame = 0;
            timer = 0;
        }
    }

    // ===== ANIMATION LOOP =====
    void animate() {
        timer++;
        if (timer < delay) return;
        timer = 0;

        GreenfootImage img = null;

        if (isAttacking) {
            img = attack[frame];

            if (frame == 14) {
                damagePlayer();
            }

            frame++;
            if (frame >= attack.length) {
                frame = 0;
                isAttacking = false;
            }
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

    // ===== MOVEMENT CHECK =====
    boolean isMoving() {
        return !isAttacking;
    }

    // ===== ADD BOSS HEALTH BAR =====
    protected void addedToWorld(World world) {
        world.addObject(
            new HealthBar_GoblinBoss(this),
            world.getWidth() / 2,
            60
        );
    }
}