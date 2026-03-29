public final class ClearTime
{
    private static int frameCount = 0;
    private static boolean runActive = false;

    private static int lastClearFrame = -1;

    private ClearTime() {}

    public static void beginRun()
    {
        frameCount = 0;
        runActive = true;
    }

    public static void cancelRun()
    {
        runActive = false;
    }

    public static void finishRun()
    {
        if (!runActive) return;

        lastClearFrame = frameCount;
        runActive = false;
    }

    public static void update()
    {
        if (runActive)
        {
            frameCount++;
        }
    }

    public static boolean isRunActive()
    {
        return runActive;
    }

    public static long getElapsedMs()
    {
        return (long)(frameCount * (1000.0 / 60.0));
    }

    public static long getLastClearMs()
    {
        return (long)(lastClearFrame * (1000.0 / 60.0));
    }

    public static boolean hasLastClear()
    {
        return lastClearFrame >= 0;
    }

    public static String formatMmSs(long ms)
    {
        long totalSec = ms / 1000;
        long m = totalSec / 60;
        long s = totalSec % 60;
        return m + ":" + (s < 10 ? "0" : "") + s;
    }
}