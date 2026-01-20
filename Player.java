import greenfoot.*;

public class Player extends Actor {

    // ===== ANIMATION =====
    GreenfootImage[] walk;
    GreenfootImage[] idle;
    GreenfootImage[] jump;
    GreenfootImage[] fall;

    GreenfootImage[] attack1;
    GreenfootImage[] attack2;
    GreenfootImage[] currentAttack;

    GreenfootImage[] hit;
    GreenfootImage[] death;

    int frame = 0;
    int timer = 0;
    int delay = 6;

    // ===== PHYSICS =====
    int vSpeed = 0;
    int gravity = 1;
    int jumpStrength = -15;

    boolean facingRight = true;
    boolean onGround = true;

    // ===== STATE =====
    boolean isAttacking = false;
    int attackIndex = 0;

    int health = 10;
    boolean isHit = false;
    boolean isDead = false;
    int hitTimer = 0;

    // ===== GROUND =====
    int groundY = 540;

    public Player() {
        // ===== LOAD IMAGES =====
        walk = loadImages("player1_run", 10);
        idle = loadImages("player1_idle", 10);
        jump = loadImages("player1_Jump", 3);
        fall = loadImages("player1_JumpFallInbetween", 2);
        attack1 = loadImages("player1_Attack1", 4);
        attack2 = loadImages("player1_Attack2", 6);
        
        hit = loadImages("player_Hit", 1);
        death = loadImages("player_Death", 10);

        setImage(idle[0]);
    }

    public void act() {
        if (isDead) {
            animateDeath();
            return;
        }

        if (isHit) {
            animateHit();
            applyGravity();
            return;
        }

        handleAttack();

        if (!isAttacking) {
            handleMovement();
            handleJump();
        }

        if (!onGround) {
            applyGravity();
        }

        hitGoblin();
        animate();
        limitToScreen();
    }

    // ===== TAKE DAMAGE =====
    public void takeDamage() {
        if (isDead || isHit) return;

        health--;

        if (health <= 0) {
            isDead = true;
            frame = 0;
        } else {
            isHit = true;
            hitTimer = 20;

            GreenfootImage img = new GreenfootImage(hit[0]); 
            
            if (!facingRight) {
                img.mirrorHorizontally();
            }
            
            setImage(img);
        }
    }

    // ===== HIT ANIMATION =====
    void animateHit() {
        if (hitTimer > 0) {
            hitTimer--;
        } else {
            isHit = false;
            frame = 0;
        }
    }

    // ===== DEATH ANIMATION =====
    void animateDeath() {
        timer++;
        if (timer < delay) return;
        timer = 0;

        if (frame < death.length) {
            GreenfootImage img = new GreenfootImage(death[frame]);
            if (!facingRight) img.mirrorHorizontally();
            setImage(img);
            frame++;
        } else {
            Greenfoot.stop();
        }
    }

    // ===== LOAD IMAGES =====
    GreenfootImage[] loadImages(String folder, int count) {
        GreenfootImage[] imgs = new GreenfootImage[count];
        for (int i = 0; i < count; i++) {
            String index = (count > 1) ? String.format("%02d", i) : "00";
            if (count == 1) index = "00";
            
            imgs[i] = new GreenfootImage("player/" + folder + "/" + index + ".png");
            imgs[i].scale(imgs[i].getWidth() * 2, imgs[i].getHeight() * 2);
        }
        return imgs;
    }

    // ===== ATTACK INPUT =====
    void handleAttack() {
        if (Greenfoot.mousePressed(null) && !isAttacking && onGround) {
            isAttacking = true;
            frame = 0;
            timer = 0;
            if (attackIndex == 0) {
                currentAttack = attack1;
                attackIndex = 1;
            } else {
                currentAttack = attack2;
                attackIndex = 0;
            }
        }
    }

    // ===== MOVEMENT =====
    void handleMovement() {
        if (Greenfoot.isKeyDown("d")) {
            setLocation(getX() + 4, getY());
            facingRight = true;
        }
        if (Greenfoot.isKeyDown("a")) {
            setLocation(getX() - 4, getY());
            facingRight = false;
        }
    }

    // ===== JUMP =====
    void handleJump() {
        if (Greenfoot.isKeyDown("space") && onGround) {
            vSpeed = jumpStrength;
            onGround = false;
            frame = 0;
        }
    }

    // ===== GRAVITY =====
    void applyGravity() {
        vSpeed += gravity;
        setLocation(getX(), getY() + vSpeed);
        if (getY() >= groundY) {
            setLocation(getX(), groundY);
            vSpeed = 0;
            onGround = true;
        }
    }

    // ===== ANIMATION =====
    void animate() {
        timer++;
        if (timer < delay) return;
        timer = 0;

        GreenfootImage img;

        if (isAttacking) {
            img = new GreenfootImage(currentAttack[frame]);
            frame++;
            if (frame >= currentAttack.length) {
                frame = 0;
                isAttacking = false;
            }
        } else if (!onGround) {
            if (vSpeed < 0) {
                frame = (frame + 1) % jump.length;
                img = new GreenfootImage(jump[frame]);
            } else {
                frame = (frame + 1) % fall.length;
                img = new GreenfootImage(fall[frame]);
            }
        } else if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("d")) {
            frame = (frame + 1) % walk.length;
            img = new GreenfootImage(walk[frame]);
        } else {
            frame = (frame + 1) % idle.length;
            img = new GreenfootImage(idle[frame]);
        }

        if (!facingRight) img.mirrorHorizontally();
        setImage(img);
    }

    // ===== COLLISION =====
    void hitGoblin() {
        if (!isAttacking) return;
        java.util.List<Goblin> goblins = getWorld().getObjects(Goblin.class);
        for (Goblin g : goblins) {
            if (Math.abs(getX() - g.getX()) < 50 && Math.abs(getY() - g.getY()) < 50) {
                g.takeHit();
            }
        }
    }

   void limitToScreen() {
        World w = getWorld();
        if (w == null) return;

        int halfWidth = getImage().getWidth() / 2;

        int visualOffset = 120;

        int minX = halfWidth - visualOffset;
        int maxX = w.getWidth() - halfWidth + visualOffset;

        if (getX() < minX) {
            setLocation(minX, getY());
        }
        if (getX() > maxX) {
            setLocation(maxX, getY());
        }
    }

    // ===== GETTER =====
    public boolean isAttacking() {
        return isAttacking;
    }
}