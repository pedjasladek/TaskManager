// IRemindAidlInterface.aidl
package ra48_2014.pnrs1.rtrk.taskmanager;

// Declare any non-default types here with import statements

interface IRemindAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void zadatakDodat(String zadatak);
    void zadatakObrisan(String zadatak);
    void zadatakPromenjen(String zadatak);
}
