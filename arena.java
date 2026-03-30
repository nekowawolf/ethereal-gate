import greenfoot.*;

public class arena extends World
{
    // ===== GLOBAL PAUSE =====
    public static boolean isGamePaused = true;

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
            SelectedCharacter.get() == SelectedCharacter.Type.PLAYER2 ? Player.Type.PLAYER2 : Player.Type.PLAYER1
        ), 200, 540);
        addObject(new ClearTimeDisplay(), 95, 29);

        // ===== DIALOGUE =====
        addObject(new IntroDialogueImage(), getWidth()/2, getHeight()/2);
        addObject(new btnOk(), 859, 579);

        waveInProgress = true;
    }
    
    public void act()
    {
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