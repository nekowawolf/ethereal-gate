public final class ClearTime
{
    private static long runStartMs;
    private static boolean runActive;
    private static long lastClearMs = -1;

    private ClearTime()
    {
    }

    public static void beginRun()
    {
        runStartMs = System.currentTimeMillis();
        runActive = true;
    }

    public static void cancelRun()
    {
        runActive = false;
    }

    public static void finishRun()
    {
        if (!runActive)
        {
            return;
        }
        lastClearMs = System.currentTimeMillis() - runStartMs;
        runActive = false;
    }

    public static boolean isRunActive()
    {
        return runActive;
    }

    public static long getElapsedMs()
    {
        if (!runActive)
        {
            return 0;
        }
        return System.currentTimeMillis() - runStartMs;
    }

    public static long getLastClearMs()
    {
        return lastClearMs;
    }

    public static boolean hasLastClear()
    {
        return lastClearMs >= 0;
    }

    public static String formatMmSs(long ms)
    {
        if (ms < 0)
        {
            ms = 0;
        }
        long totalSec = ms / 1000;
        long m = totalSec / 60;
        long s = totalSec % 60;
        return m + ":" + (s < 10 ? "0" : "") + s;
    }
}