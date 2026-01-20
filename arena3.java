import greenfoot.*;

public class arena3 extends World
{
    boolean isPortalSpawned = false;

    public arena3()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, Mushroom.class, Portal.class);

        addObject(new Player(), 200, 540);
        addObject(new Mushroom(), 1300, 570);
    }
    
    public void act() {
        if (getObjects(Mushroom.class).isEmpty() && !isPortalSpawned) {
            spawnPortal();
        }
    }
    
    // ===== SPAWN PORTAL =====
    public void spawnPortal() {
        addObject(new Portal(new arena4()), 1000, 560);
        isPortalSpawned = true;
    }
}