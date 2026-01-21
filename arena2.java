import greenfoot.*;
import java.util.List;

public class arena2 extends World
{
    // ===== WAVE SYSTEM =====
    private boolean isPortalSpawned = false;
    private boolean bossSpawned = false;

    private int currentWave = 1;
    private int totalWaves = 3;

    private int enemiesToSpawnThisWave = 2; // default for wave 1
    private int enemiesSpawnedThisWave = 0;

    private int spawnTimer = 0;
    private int spawnDelay = 60;

    private boolean waveInProgress = false;
    private boolean waveComplete = false;

    public arena2()
    {
        super(1200, 675, 1, false);

        setPaintOrder(HealthBar_FlyingEyeBoss.class, Player.class, FlyingEye_Boss.class, FlyingEye.class, Portal.class);

        addObject(new Player(), 200, 540);

        waveInProgress = true;
        enemiesToSpawnThisWave = 2; // wave 1
    }

    public void act() {
        // ===== RUN CURRENT WAVE =====
        if (waveInProgress && !waveComplete) {
            spawnWave();

            if (enemiesSpawnedThisWave >= enemiesToSpawnThisWave && getObjects(FlyingEye.class).isEmpty()) {
                waveComplete = true;
                waveInProgress = false;
            }
        }

        // ===== AFTER WAVE COMPLETED =====
        if (waveComplete && !isPortalSpawned) {
            if (currentWave < totalWaves) { // wave 1 and 2
                currentWave++;
                setupNextWave();
            } else { // wave 3 finished
                if (!bossSpawned) {
                    spawnBoss();
                    bossSpawned = true;
                } else if (getObjects(FlyingEye_Boss.class).isEmpty()) {
                    spawnPortal();
                }
            }
        }
    }

    // ===== SETUP NEXT WAVE =====
    private void setupNextWave() {
        enemiesSpawnedThisWave = 0;
        spawnTimer = 0;
        waveInProgress = true;
        waveComplete = false;

        // Set enemies count per wave
        switch (currentWave) {
            case 2: enemiesToSpawnThisWave = 2; break;
            case 3: enemiesToSpawnThisWave = 3; break;
        }
    }

    // ===== SPAWN WAVE =====
    private void spawnWave() {
        if (enemiesSpawnedThisWave < enemiesToSpawnThisWave) {
            spawnTimer++;

            if (spawnTimer >= spawnDelay) {
                spawnTimer = 0;
                spawnFlyingEye();
                enemiesSpawnedThisWave++;
            }
        }
    }

    // ===== SPAWN SINGLE FLYING EYE =====
    private void spawnFlyingEye() {
        int spawnX = (Greenfoot.getRandomNumber(2) == 0) ? -100 : 1300;
        int spawnY = 450;

        addObject(new FlyingEye(), spawnX, spawnY);
    }

    // ===== SPAWN BOSS =====
    private void spawnBoss() {
        addObject(new FlyingEye_Boss(), 1100, 560);
    }

    // ===== SPAWN PORTAL =====
    private void spawnPortal() {
        addObject(new Portal(new arena3()), 1000, 560);
        isPortalSpawned = true;
    }
}