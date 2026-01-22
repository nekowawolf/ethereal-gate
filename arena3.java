import greenfoot.*;

public class arena3 extends World
{
    // ===== WAVE SYSTEM =====
    private boolean isPortalSpawned = false;
    private int currentWave = 1;

    private int mushroomsToSpawn = 2;
    private int mushroomsSpawned = 0;

    private int spawnTimer = 0;
    private int spawnDelay = 60;

    // ===== WAVE STATE =====
    private boolean waveInProgress = false;
    private boolean waveCompleted = false;

    // ===== BOSS STATE =====
    private boolean bossSpawned = false;
    private boolean bossDefeated = false;

    public arena3()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, Mushroom.class, mushroomBoss.class, Portal.class);

        addObject(new Player(), 200, 540);

        waveInProgress = true;
    }
    
    public void act()
    {
        // ===== RUN CURRENT WAVE =====
        if (waveInProgress && !waveCompleted) {
            spawnWave();

            if (mushroomsSpawned >= mushroomsToSpawn
                && getObjects(Mushroom.class).isEmpty()) {

                waveCompleted = true;
                waveInProgress = false;
            }
        }

        // ===== AFTER WAVE CLEARED =====
        if (waveCompleted && !isPortalSpawned) {

            if (currentWave < 4) {
                currentWave++;
                setupNextWave();
            }
            else if (!bossSpawned) {
                spawnBoss();
            }
            else if (bossDefeated) {
                spawnPortal();
            }
        }

        // ===== CHECK BOSS DEFEATED =====
        if (bossSpawned && !bossDefeated) {
            if (getObjects(mushroomBoss.class).isEmpty()) {
                bossDefeated = true;
            }
        }
    }

    // ===== SETUP NEXT WAVE =====
    private void setupNextWave()
    {
        if (currentWave == 2) {
            mushroomsToSpawn = 2;
        }
        else if (currentWave == 3) {
            mushroomsToSpawn = 2;
        }
        else if (currentWave == 4) {
            mushroomsToSpawn = 3;
        }

        mushroomsSpawned = 0;
        spawnTimer = 0;
        waveInProgress = true;
        waveCompleted = false;
    }

    // ===== SPAWN WAVE =====
    private void spawnWave()
    {
        if (mushroomsSpawned < mushroomsToSpawn) {
            spawnTimer++;

            if (spawnTimer >= spawnDelay) {
                spawnTimer = 0;
                spawnMushroom();
                mushroomsSpawned++;
            }
        }
    }

    // ===== SPAWN SINGLE MUSHROOM =====
    private void spawnMushroom()
    {
        int spawnX = Greenfoot.getRandomNumber(2) == 0 ? -100 : 1300;
        int spawnY = 570;

        addObject(new Mushroom(), spawnX, spawnY);
    }

    // ===== SPAWN BOSS =====
    private void spawnBoss()
    {
        addObject(new mushroomBoss(), 1000, 555);
        bossSpawned = true;
    }

    // ===== SPAWN PORTAL =====
    private void spawnPortal()
    {
        addObject(new Portal(new arena4()), 1000, 560);
        isPortalSpawned = true;
    }
}

