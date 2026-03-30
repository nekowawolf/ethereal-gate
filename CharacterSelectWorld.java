import greenfoot.*;

public class CharacterSelectWorld extends World
{
    private CardPlayer1 card1;
    private CardPlayer2 card2;

    public CharacterSelectWorld()
    {
        super(1200, 675, 1);

        GreenfootImage bg = new GreenfootImage("CharacterSelectUI.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int gap = 10;

        card1 = new CardPlayer1();
        card2 = new CardPlayer2();

        int cardY = centerY + 50;
        addObject(card1, centerX - (card1.getImage().getWidth() / 2) - gap, cardY);
        addObject(card2, centerX + (card2.getImage().getWidth() / 2) + gap, cardY);

        btnEnterGate enter = new btnEnterGate();
        addObject(enter, centerX, cardY + 200);

        setSelected(SelectedCharacter.get());
    }

    public void setSelected(SelectedCharacter.Type type)
    {
        card1.setSelected(type == SelectedCharacter.Type.PLAYER1);
        card2.setSelected(type == SelectedCharacter.Type.PLAYER2);
    }
}