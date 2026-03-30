public final class SelectedCharacter
{
    public enum Type
    {
        PLAYER1,
        PLAYER2
    }

    private static Type selected = Type.PLAYER1;

    private SelectedCharacter()
    {
    }

    public static void select(Type type)
    {
        if (type == null)
        {
            return;
        }
        selected = type;
    }

    public static Type get()
    {
        return selected;
    }

    public static String getDialogueImage()
    {
        return (selected == Type.PLAYER2) ? "Dialogue_Player2.png" : "Dialogue_Player1.png";
    }
}