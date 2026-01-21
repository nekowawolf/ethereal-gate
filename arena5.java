import greenfoot.*;

public class arena5 extends World
{
    // ===== WAVE SYSTEM =====
    private int currentWave = 1;
    private final int MAX_WAVE = 3;

    private int enemiesPerType;

    private int goblinSpawned = 0;
    private int eyeSpawned = 0;
    private int mushroomSpawned = 0;
    private int skeletonSpawned = 0;

    private int spawnTimer = 0;
    private int spawnDelay = 40;

    private boolean waveInProgress = true;
    private boolean waveCompleted = false;

    // ===== BOSS STATE =====
    private boolean bossSpawned = false;
    private boolean bossDefeated = false;

    public arena5()
    {
        super(1200, 675, 1, false);

        setPaintOrder(
            Player.class,
            Goblin.class,
            FlyingEye.class,
            Mushroom.class,
            Skeleton.class,
            tronBoss.class
        );

        addObject(new Player(), 200, 540);

        setupWave();
    }

    public void act()
    {
        if (waveInProgress && !waveCompleted)
        {
            spawnWave();

            if (allEnemiesSpawned() && allEnemiesCleared())
            {
                waveCompleted = true;
                waveInProgress = false;
            }
        }

        if (waveCompleted)
        {
            if (currentWave < MAX_WAVE)
            {
                currentWave++;
                setupWave();
            }
            else if (!bossSpawned)
            {
                spawnBoss();
            }
            else if (bossDefeated)
            {
                winGame();
            }
        }

        // ===== CHECK BOSS DEFEATED =====
        if (bossSpawned && !bossDefeated)
        {
            if (getObjects(tronBoss.class).isEmpty())
            {
                bossDefeated = true;
            }
        }
    }

    // ===== SETUP WAVE =====
    private void setupWave()
    {
        enemiesPerType = currentWave;

        goblinSpawned = 0;
        eyeSpawned = 0;
        mushroomSpawned = 0;
        skeletonSpawned = 0;

        spawnTimer = 0;
        waveInProgress = true;
        waveCompleted = false;
    }

    // ===== SPAWN WAVE =====
    private void spawnWave()
    {
        spawnTimer++;
        if (spawnTimer < spawnDelay) return;
        spawnTimer = 0;

        if (goblinSpawned < enemiesPerType)
        {
            spawnGoblin();
            goblinSpawned++;
        }
        else if (eyeSpawned < enemiesPerType)
        {
            spawnFlyingEye();
            eyeSpawned++;
        }
        else if (mushroomSpawned < enemiesPerType)
        {
            spawnMushroom();
            mushroomSpawned++;
        }
        else if (skeletonSpawned < enemiesPerType)
        {
            spawnSkeleton();
            skeletonSpawned++;
        }
    }

    // ===== CHECKERS =====
    private boolean allEnemiesSpawned()
    {
        return goblinSpawned >= enemiesPerType
            && eyeSpawned >= enemiesPerType
            && mushroomSpawned >= enemiesPerType
            && skeletonSpawned >= enemiesPerType;
    }

    private boolean allEnemiesCleared()
    {
        return getObjects(Goblin.class).isEmpty()
            && getObjects(FlyingEye.class).isEmpty()
            && getObjects(Mushroom.class).isEmpty()
            && getObjects(Skeleton.class).isEmpty();
    }

    // ===== SPAWN POSITION =====
    private int randomX()
    {
        return Greenfoot.getRandomNumber(2) == 0 ? -120 : 1320;
    }

    // ===== SPAWN METHODS =====
    private void spawnGoblin()
    {
        addObject(new Goblin(), randomX(), 570);
    }

    private void spawnFlyingEye()
    {
        addObject(new FlyingEye(), randomX(), 450);
    }

    private void spawnMushroom()
    {
        addObject(new Mushroom(), randomX(), 570);
    }

    private void spawnSkeleton()
    {
        addObject(new Skeleton(), randomX(), 575);
    }

    // ===== SPAWN BOSS =====
    private void spawnBoss()
    {
        addObject(new tronBoss(), 1000, 490);
        bossSpawned = true;
    }

    // ===== WIN GAME =====
    private void winGame()
    {
        Greenfoot.setWorld(new WinGameWorld());
    }
}