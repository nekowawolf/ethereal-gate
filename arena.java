import greenfoot.*;

public class arena extends World
{
    // ===== WAVE SYSTEM =====
    boolean isPortalSpawned = false;
    boolean bossSpawned = false;

    int currentWave = 1;
    int goblinsToSpawn = 3;
    int goblinsSpawned = 0;
    int spawnTimer = 0;
    int spawnDelay = 60;
    
    // ===== WAVE STATE =====
    boolean waveInProgress = false;
    boolean waveComplete = false;

    public arena()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(HealthBar_GoblinBoss.class, Player.class, Goblin_Boss.class, Goblin.class, Portal.class);

        addObject(new Player(), 200, 540);
        
        waveInProgress = true;
    }
    
    public void act() {
        if (waveInProgress && !waveComplete) {
            spawnWave();
            
            if (goblinsSpawned >= goblinsToSpawn && getObjects(Goblin.class).isEmpty()) {
                waveComplete = true;
                waveInProgress = false;
            }
        }
        
        if (waveComplete && !isPortalSpawned) {
            if (currentWave < 2) {
                currentWave++;
                goblinsToSpawn = 2;
                goblinsSpawned = 0;
                spawnTimer = 0;
                waveInProgress = true;
                waveComplete = false;
            } else {
                // ===== GOBLIN BOSS LOGIC =====
                
                if (!bossSpawned) {
                    spawnBoss();
                    bossSpawned = true;
                }
                else if (getObjects(Goblin_Boss.class).isEmpty()) {
                    spawnPortal();
                }
            }
        }
    }
    
    // ===== SPAWN WAVE =====
    void spawnWave() {
        if (goblinsSpawned < goblinsToSpawn) {
            spawnTimer++;
            if (spawnTimer >= spawnDelay) {
                spawnTimer = 0;
                spawnGoblin();
                goblinsSpawned++;
            }
        }
    }
    
    // ===== SPAWN SINGLE GOBLIN =====
    void spawnGoblin() {
        int spawnSide = Greenfoot.getRandomNumber(2);
        int spawnX, spawnY = 570;
        
        if (spawnSide == 0) {
            spawnX = -200;
        } else {
            spawnX = 1300;
        }
        
        addObject(new Goblin(), spawnX, spawnY);
    }

    // ===== 3.PAWN BOSS =====
    void spawnBoss() {
        addObject(new Goblin_Boss(), 1300, 560);
    }
    
    // ===== SPAWN PORTAL =====
    void spawnPortal() {
        addObject(new Portal(new arena2()), 1000, 560);
        isPortalSpawned = true;
    }
}