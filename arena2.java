import greenfoot.*;

public class arena2 extends World
{
    // ===== WAVE SYSTEM =====
    private boolean isPortalSpawned = false;
    private int currentWave = 1;

    // Flying Eye spawn settings
    private int flyingEyesToSpawn = 2; // Wave 1: 2 enemies
    private int flyingEyesSpawned = 0;

    private int spawnTimer = 0;
    private int spawnDelay = 60;

    // ===== WAVE STATE =====
    private boolean waveInProgress = false;
    private boolean waveCompleted = false;

    public arena2()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, FlyingEye.class, Portal.class);

        addObject(new Player(), 200, 540);

        waveInProgress = true;
    }
    
    public void act()
    {
        // ===== RUN CURRENT WAVE =====
        if (waveInProgress && !waveCompleted) {
            spawnWave();

            if (flyingEyesSpawned >= flyingEyesToSpawn
                && getObjects(FlyingEye.class).isEmpty()) {

                waveCompleted = true;
                waveInProgress = false;
            }
        }

        // ===== AFTER WAVE CLEARED =====
        if (waveCompleted && !isPortalSpawned) {

            if (currentWave < 3) {
                currentWave++;
                setupNextWave();
            } 
            else {
                spawnPortal();
            }
        }
    }

    // ===== SETUP NEXT WAVE =====
    private void setupNextWave()
    {
        if (currentWave == 2) {
            flyingEyesToSpawn = 2; // Wave 2: 2 enemies
        }
        else if (currentWave == 3) {
            flyingEyesToSpawn = 3; // Wave 3: 3 enemies
        }

        flyingEyesSpawned = 0;
        spawnTimer = 0;
        waveInProgress = true;
        waveCompleted = false;
    }

    // ===== SPAWN WAVE =====
    private void spawnWave()
    {
        if (flyingEyesSpawned < flyingEyesToSpawn) {
            spawnTimer++;

            if (spawnTimer >= spawnDelay) {
                spawnTimer = 0;
                spawnFlyingEye();
                flyingEyesSpawned++;
            }
        }
    }

    // ===== SPAWN SINGLE FLYING EYE =====
    private void spawnFlyingEye()
    {
        int spawnX = Greenfoot.getRandomNumber(2) == 0 ? -100 : 1300;
        int spawnY = 450;

        addObject(new FlyingEye(), spawnX, spawnY);
    }

    // ===== SPAWN PORTAL =====
    private void spawnPortal()
    {
        addObject(new Portal(new arena3()), 1000, 560);
        isPortalSpawned = true;
    }
}