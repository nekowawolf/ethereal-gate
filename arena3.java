import greenfoot.*;

public class arena3 extends World
{
    // ===== BACKGROUND ANIMATION =====
    private static final int BACKGROUND_FRAME_COUNT = 78;
    private static final int BACKGROUND_ANIMATION_DELAY = 5;;
    private GreenfootImage[] backgroundFrames;
    private int backgroundFrameIndex = 0;
    private int backgroundFrameTimer = 0;

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

        backgroundFrames = loadBackgroundFrames("arena3_frames", "arena3");
        setBackground(backgroundFrames[0]);

        // ===== RESET PAUSE =====
        arena.isGamePaused = false;

        setPaintOrder(
            Player.class, 
            Mushroom.class, 
            mushroomBoss.class, 
            Portal.class, 
            ClearTimeDisplay.class
        );

        addObject(new Player(
            Player.Type.valueOf(SelectedCharacter.get().name())
        ), 200, 540);
        addObject(new ClearTimeDisplay(), 95, 29);

        waveInProgress = true;
    }
    
    public void act()
    {
        updateBackground();

        // ===== PAUSE SYSTEM =====
        if (arena.isGamePaused) return;

        // ===== TIMER UPDATE =====
        ClearTime.update();

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
        if (currentWave == 2) mushroomsToSpawn = 2;
        else if (currentWave == 3) mushroomsToSpawn = 2;
        else if (currentWave == 4) mushroomsToSpawn = 3;

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
        addObject(new mushroomBoss(), 1300, 555);
        bossSpawned = true;
    }

    // ===== SPAWN PORTAL =====
    private void spawnPortal()
    {
        addObject(new Portal(new arena4()), 1000, 560);
        isPortalSpawned = true;
    }

    private void updateBackground()
    {
        if (backgroundFrames == null || backgroundFrames.length == 0) {
            return;
        }

        backgroundFrameTimer++;
        if (backgroundFrameTimer < BACKGROUND_ANIMATION_DELAY) {
            return;
        }
        backgroundFrameTimer = 0;

        setBackground(backgroundFrames[backgroundFrameIndex]);
        backgroundFrameIndex = (backgroundFrameIndex + 1) % BACKGROUND_FRAME_COUNT;
    }

    private GreenfootImage[] loadBackgroundFrames(String folderName, String filePrefix)
    {
        GreenfootImage[] frames = new GreenfootImage[BACKGROUND_FRAME_COUNT];

        for (int i = 0; i < BACKGROUND_FRAME_COUNT; i++) {
            String filename = String.format("%s_%03d.jpg", filePrefix, i + 1);
            GreenfootImage img = new GreenfootImage(folderName + "/" + filename);
            img.scale(1200, 675);
            frames[i] = img;
        }

        return frames;
    }
}