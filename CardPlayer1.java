import greenfoot.*;

public class CardPlayer1 extends Actor
{
    private GreenfootImage base;
    private double currentScale = 1.0;
    private double targetScale = 1.0;
    private int baseY;
    private int yOffset = 0;

    public CardPlayer1()
    {
        base = new GreenfootImage("CardPlayer1.png");
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
            SelectedCharacter.select(SelectedCharacter.Type.PLAYER1);
            CharacterSelectWorld w = (CharacterSelectWorld) getWorld();
            w.setSelected(SelectedCharacter.Type.PLAYER1);
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