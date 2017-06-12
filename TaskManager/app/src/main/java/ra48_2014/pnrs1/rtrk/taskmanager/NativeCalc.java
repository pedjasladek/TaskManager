package ra48_2014.pnrs1.rtrk.taskmanager;

/**
 * Created by niketic95 on 12/06/17.
 */

public class NativeCalc {

    public native double calculatePercentage(int total, int completed);
    static {
        System.loadLibrary("calculatestats");
    }
}
