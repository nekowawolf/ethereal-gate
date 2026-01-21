import greenfoot.*;

public class arena4 extends World
{
    // ===== WAVE SYSTEM =====
    private boolean isPortalSpawned = false;
    private int currentWave = 1;

    private int skeletonsToSpawn = 2; // Wave 1: 2 enemies
    private int skeletonsSpawned = 0;

    private int spawnTimer = 0;
    private int spawnDelay = 60;

    // ===== WAVE STATE =====
    private boolean waveInProgress = false;
    private boolean waveCompleted = false;

    public arena4()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, Skeleton.class, Portal.class);

        addObject(new Player(), 200, 540);

        waveInProgress = true;
    }
    
    public void act()
    {
        // ===== RUN CURRENT WAVE =====
        if (waveInProgress && !waveCompleted) {
            spawnWave();

            // Check if wave is finished
            if (skeletonsSpawned >= skeletonsToSpawn
                && getObjects(Skeleton.class).isEmpty()) {

                waveCompleted = true;
                waveInProgress = false;
            }
        }

        // ===== AFTER WAVE CLEARED =====
        if (waveCompleted && !isPortalSpawned) {

            if (currentWave < 5) {
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
            skeletonsToSpawn = 2;
        }
        else if (currentWave == 3) {
            skeletonsToSpawn = 2;
        }
        else if (currentWave == 4) {
            skeletonsToSpawn = 2;
        }
        else if (currentWave == 5) {
            skeletonsToSpawn = 3;
        }

        skeletonsSpawned = 0;
        spawnTimer = 0;
        waveInProgress = true;
        waveCompleted = false;
    }

    // ===== SPAWN WAVE =====
    private void spawnWave()
    {
        if (skeletonsSpawned < skeletonsToSpawn) {
            spawnTimer++;

            if (spawnTimer >= spawnDelay) {
                spawnTimer = 0;
                spawnSkeleton();
                skeletonsSpawned++;
            }
        }
    }

    // ===== SPAWN SINGLE SKELETON =====
    private void spawnSkeleton()
    {
        int spawnX = Greenfoot.getRandomNumber(2) == 0 ? -120 : 1320;
        int spawnY = 575;

        addObject(new Skeleton(), spawnX, spawnY);
    }

    // ===== SPAWN PORTAL =====
    private void spawnPortal()
    {
        addObject(new Portal(new arena5()), 1000, 560);
        isPortalSpawned = true;
    }
}