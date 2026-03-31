import greenfoot.*;

public class CardPlayer3 extends Actor
{
    private GreenfootImage base;
    private double currentScale = 1.0;
    private double targetScale = 1.0;
    private int baseY;
    private int yOffset = 0;

    public CardPlayer3()
    {
        if (SaveData.hasWon) {
            base = new GreenfootImage("CardPlayer3_Unlocked.png");
        } else {
            base = new GreenfootImage("CardPlayer3_Locked.png");
        }
        setImage(new GreenfootImage(base));
    }

    protected void addedToWorld(World world)
    {
        baseY = getY();
        updateImage();
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            if (SaveData.hasWon) {
                SelectedCharacter.select(SelectedCharacter.Type.PLAYER3);
                CharacterSelectWorld w = (CharacterSelectWorld) getWorld();
                w.setSelected(SelectedCharacter.Type.PLAYER3);
            }
        }

        animate();
    }

    public void setSelected(boolean selected)
    {
        targetScale = selected ? 1.08 : 1.0;
        yOffset = selected ? -10 : 0;
    }

    private void animate()
    {
        double speed = 0.18;
        currentScale += (targetScale - currentScale) * speed;
        updateImage();
        setLocation(getX(), baseY + yOffset);
    }

    private void updateImage()
    {
        int w = Math.max(1, (int) Math.round(base.getWidth() * currentScale));
        int h = Math.max(1, (int) Math.round(base.getHeight() * currentScale));
        GreenfootImage img = new GreenfootImage(base);
        img.scale(w, h);
        setImage(img);
    }
}
