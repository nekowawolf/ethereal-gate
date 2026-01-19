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
    int attackIndex = 0; // 0 = attack1, 1 = attack2

    // ===== GROUND =====
    int groundY = 540;

    public Player() {

        // ===== WALK (10) =====
        walk = loadImages("player1_run", 10);

        // ===== IDLE (10) =====
        idle = loadImages("player1_idle", 10);

        // ===== JUMP (3) =====
        jump = loadImages("player1_Jump", 3);

        // ===== FALL (2) =====
        fall = loadImages("player1_JumpFallInbetween", 2);

        // ===== ATTACK 1 (00-03) =====
        attack1 = loadImages("player1_Attack1", 4);

        // ===== ATTACK 2 (00-05) =====
        attack2 = loadImages("player1_Attack2", 6);

        setImage(idle[0]);
    }

    public void act() {

        handleAttack();

        if (!isAttacking) {
            handleMovement();
            handleJump();
        }

        if (!onGround) {
            applyGravity();
        }

        animate();
    }

    // ===== LOAD IMAGE HELPER =====
    GreenfootImage[] loadImages(String folder, int count) {
        GreenfootImage[] imgs = new GreenfootImage[count];
        for (int i = 0; i < count; i++) {
            String index = String.format("%02d", i);
            imgs[i] = new GreenfootImage(folder + "/" + index + ".png");
            imgs[i].scale(
                imgs[i].getWidth() * 2,
                imgs[i].getHeight() * 2
            );
        }
        return imgs;
    }

    // ===== ATTACK INPUT =====
    void handleAttack() {
        if (Greenfoot.mousePressed(null) && !isAttacking && onGround) {

            isAttacking = true;
            frame = 0;
            timer = 0;

            // pilih attack bergantian
            if (attackIndex == 0) {
                currentAttack = attack1;
                attackIndex = 1;
            } else {
                currentAttack = attack2;
                attackIndex = 0;
            }
        }
    }

    // ===== MOVE =====
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

        // ===== ATTACK =====
        if (isAttacking) {
            img = new GreenfootImage(currentAttack[frame]);
            frame++;

            if (frame >= currentAttack.length) {
                frame = 0;
                isAttacking = false;
            }
        }

        // ===== AIR =====
        else if (!onGround) {
            if (vSpeed < 0) {
                frame = (frame + 1) % jump.length;
                img = new GreenfootImage(jump[frame]);
            } else {
                frame = (frame + 1) % fall.length;
                img = new GreenfootImage(fall[frame]);
            }
        }

        // ===== WALK =====
        else if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("d")) {
            frame = (frame + 1) % walk.length;
            img = new GreenfootImage(walk[frame]);
        }

        // ===== IDLE =====
        else {
            frame = (frame + 1) % idle.length;
            img = new GreenfootImage(idle[frame]);
        }

        if (!facingRight) {
            img.mirrorHorizontally();
        }

        setImage(img);
    }
}