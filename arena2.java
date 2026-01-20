import greenfoot.*;

public class arena2 extends World
{
    boolean isPortalSpawned = false;

    public arena2()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, FlyingEye.class, Portal.class);

        addObject(new Player(), 200, 540);
        addObject(new FlyingEye(), 1300, 560);
    }
    
    public void act() {
        if (getObjects(FlyingEye.class).isEmpty() && !isPortalSpawned) {
            spawnPortal();
        }
    }
    
    // ===== SPAWN PORTAL =====
    public void spawnPortal() {
        addObject(new Portal(new arena3()), 1000, 560);
        isPortalSpawned = true;
    }
}