import greenfoot.*;

public class arena4 extends World
{
    boolean isPortalSpawned = false;

    public arena4()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, Skeleton.class, Portal.class);

        addObject(new Player(), 200, 540);
        addObject(new Skeleton(), 1300, 575);
    }
    
    public void act() {
        if (getObjects(Skeleton.class).isEmpty() && !isPortalSpawned) {
            spawnPortal();
        }
    }
    
    // ===== SPAWN PORTAL =====
    public void spawnPortal() {
        addObject(new Portal(new arena5()), 1000, 560);
        isPortalSpawned = true;
    }
}