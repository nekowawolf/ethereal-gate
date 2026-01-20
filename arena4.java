import greenfoot.*;

public class arena4 extends World
{
    boolean isPortalSpawned = false;

    public arena4()
    {    
        super(1200, 675, 1, false);

        setPaintOrder(Player.class, Goblin.class, Portal.class);

        addObject(new Player(), 200, 540);
        addObject(new Goblin(), 1300, 570);
    }
    
    public void act() {
        if (getObjects(Goblin.class).isEmpty() && !isPortalSpawned) {
            spawnPortal();
        }
    }
    
    // ===== SPAWN PORTAL =====
    public void spawnPortal() {
        addObject(new Portal(new arena5()), 1000, 560);
        isPortalSpawned = true;
    }
}