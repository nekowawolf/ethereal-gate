import greenfoot.*;

public class Player extends Actor {

    public enum Type
    {
        PLAYER1,
        PLAYER2
    }

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
    int attackRange = 70;

    int health = 10;
    boolean isHit = false;
    boolean isDead = false;
    int hitTimer = 0;

    // ===== STAMINA SYSTEM =====
    int maxStamina = 100;
    int stamina = 100;
    int staminaCost = 25;  

    int staminaRegenDelay = 150;
    int staminaTimer = 0;

    // ===== GROUND =====
    int groundY = 540;

    public Player()
    {
        this(Type.PLAYER1);
    }

    public Player(Type type)
    {
        if (type == null)
        {
            type = Type.PLAYER1;
        }

        if (type == Type.PLAYER2)
        {
            double p2Scale = 2.5; 
            this.attackRange = 120;

            walk = loadImages("player2", "player2_run", 16, true, p2Scale);
            idle = loadImages("player2", "player2_idle", 10, true, p2Scale);
            jump = loadImages("player2", "player2_Jump", 5, false, p2Scale);
            fall = jump;

            // Frame 0-4 (Total 5 frame)
            attack1 = loadImages("player2", "player2_Attack", 3, false, p2Scale);
            attack2 = attack1;

            hit = loadImages("player2", "player2_Hit", 4, false, p2Scale);
            death = loadImages("player2", "player2_Death", 6, false, p2Scale);
        }
        else
        {
            double p1Scale = 2.0;
            this.attackRange = 70; 

            walk = loadImages("player", "player1_run", 10, true, p1Scale);
            idle = loadImages("player", "player1_idle", 10, true, p1Scale);
            jump = loadImages("player", "player1_Jump", 3, true, p1Scale);
            fall = loadImages("player", "player1_JumpFallInbetween", 2, true, p1Scale);
            attack1 = loadImages("player", "player1_Attack1", 4, true, p1Scale);
            attack2 = loadImages("player", "player1_Attack2", 6, true, p1Scale);

            hit = loadImages("player", "player_Hit", 1, true, p1Scale);
            death = loadImages("player", "player_Death", 10, true, p1Scale);
        }

        setImage(idle[0]);
    }

    public void act() {
        if (arena.isGamePaused) return;
        
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

        regenerateStamina();

        // Enemy damage is handled by each enemy's checkPlayerHit()
        // so attack range settings apply consistently to all monsters.
        animate();
        limitToScreen();
    }

    public void takeDamage(int dmg) {
        if (isDead || isHit) return;
        health -= dmg;
        if (health <= 0) {
            isDead = true;
            frame = 0;
        } else {
            isHit = true;
            hitTimer = 20;
            GreenfootImage img = new GreenfootImage(hit[0]); 
            if (!facingRight) img.mirrorHorizontally();
            setImage(img);
        }
    }

    private void die() {
        ClearTime.cancelRun();
        Greenfoot.setWorld(new GameOverWorld());
    }

    void animateHit() {
        if (hitTimer > 0) hitTimer--;
        else {
            isHit = false;
            frame = 0;
        }
    }

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
            Greenfoot.delay(60);
            die();
        }
    }

    GreenfootImage[] loadImages(String baseDir, String folder, int count, boolean twoDigits, double scale) {
        GreenfootImage[] imgs = new GreenfootImage[count];
        for (int i = 0; i < count; i++) {
            String index;
            if (count == 1) index = twoDigits ? "00" : "0";
            else index = twoDigits ? String.format("%02d", i) : String.valueOf(i);

            imgs[i] = new GreenfootImage(baseDir + "/" + folder + "/" + index + ".png");
            int newW = (int)(imgs[i].getWidth() * scale);
            int newH = (int)(imgs[i].getHeight() * scale);
            imgs[i].scale(newW, newH);
        }
        return imgs;
    }

    void handleAttack() {
        if (Greenfoot.mousePressed(null) && !isAttacking && onGround && stamina >= staminaCost) {
            isAttacking = true;
            frame = 0;
            timer = 0;
            useStamina();
            if (attackIndex == 0) {
                currentAttack = attack1;
                attackIndex = 1;
            } else {
                currentAttack = attack2;
                attackIndex = 0;
            }
        }
    }

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

    void handleJump() {
        if (Greenfoot.isKeyDown("space") && onGround) {
            vSpeed = jumpStrength;
            onGround = false;
            frame = 0;
        }
    }

    void applyGravity() {
        vSpeed += gravity;
        setLocation(getX(), getY() + vSpeed);
        if (getY() >= groundY) {
            setLocation(getX(), groundY);
            vSpeed = 0;
            onGround = true;
        }
    }

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

    void hitGoblin() {
        if (!isAttacking) return;
        java.util.List<Goblin> goblins = getWorld().getObjects(Goblin.class);
        for (Goblin g : goblins) {
            int xDist = getX() - g.getX();
            int yDist = Math.abs(getY() - g.getY());
            boolean facingGoblin = (facingRight && xDist < 0) || (!facingRight && xDist > 0);
            
            if (Math.abs(xDist) < attackRange && yDist < 50 && facingGoblin) {
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
        if (getX() < minX) setLocation(minX, getY());
        if (getX() > maxX) setLocation(maxX, getY());
    }

    void useStamina() {
        stamina -= staminaCost;
        if (stamina < 0) stamina = 0;
        staminaTimer = 0;
    }

    void regenerateStamina() {
        if (stamina >= maxStamina) return;
        staminaTimer++;
        if (staminaTimer >= staminaRegenDelay) {
            stamina++;
            if (stamina > maxStamina) stamina = maxStamina;
        }
    }

    protected void addedToWorld(World world) {
        world.addObject(new HealthBar_Player(this), getX(), getY() - 12);
        world.addObject(new StaminaBar_Player(this), getX(), getY() - 4);
    }

    public boolean isAttacking() { return isAttacking; }
    public boolean isFacingRight() { return facingRight; }
    public int getStamina() { return stamina; }
    public int getMaxStamina() { return maxStamina; }
    public int getAttackRange() { return attackRange; }
}