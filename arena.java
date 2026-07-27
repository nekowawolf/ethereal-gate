import greenfoot.*;

public class arena extends World
{
    // ===== GLOBAL PAUSE =====
    public static boolean isGamePaused = true;

    // ===== BACKGROUND ANIMATION =====
    private static final int BACKGROUND_FRAME_COUNT = 78;
    private static final int BACKGROUND_ANIMATION_DELAY = 5;;
    private GreenfootImage[] backgroundFrames;
    private int backgroundFrameIndex = 0;
    private int backgroundFrameTimer = 0;

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
        backgroundFrames = loadBackgroundFrames("arena1_frames", "arena1");
        setBackground(backgroundFrames[0]);

        isGamePaused = true;

        setPaintOrder(
            btnOk.class,                
            IntroDialogueImage.class,
            HealthBar_GoblinBoss.class,
            Player.class,
            Goblin_Boss.class,
            Goblin.class,
            Portal.class,
            ClearTimeDisplay.class
        );

        // ===== START TIMER =====
        ClearTime.beginRun();

        // ===== SPAWN PLAYER & UI =====
        addObject(new Player(
            Player.Type.valueOf(SelectedCharacter.get().name())
        ), 200, 540);
        addObject(new ClearTimeDisplay(), 95, 29);

        // ===== DIALOGUE =====
        addObject(new IntroDialogueImage(), getWidth()/2, getHeight()/2);
        addObject(new btnOk(), 859, 579);

        waveInProgress = true;
    }
    
    public void act()
    {
        updateBackground();

        // ===== PAUSE SYSTEM =====
        if (isGamePaused) return;

        // ===== TIMER UPDATE =====
        ClearTime.update();

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
    
    // ===== RESUME GAME =====
    public void resumeGame()
    {
        isGamePaused = false;

        removeObjects(getObjects(IntroDialogueImage.class));
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

    // ===== SPAWN BOSS =====
    void spawnBoss() {
        addObject(new Goblin_Boss(), 1300, 560);
    }
    
    // ===== SPAWN PORTAL =====
    void spawnPortal() {
        addObject(new Portal(new arena2()), 1000, 560);
        isPortalSpawned = true;
    }
}   