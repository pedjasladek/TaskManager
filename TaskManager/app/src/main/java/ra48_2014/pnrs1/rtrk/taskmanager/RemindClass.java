package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Notification;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;

/**
 * Created by VELIKI on 5/22/2017.
 */

public class RemindClass extends IRemindAidlInterface.Stub{


    private static int dodat = 1;
    private NotificationCompat.Builder addNotification;

    public RemindClass(NotificationCompat.Builder addBuilder){
        this.addNotification = addBuilder;
    };

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
    }

    @Override
    public void zadatakDodat(String zadatak) throws RemoteException {
        addNotification.setContentText(zadatak + " dodat");
        MainActivity.notificationManager.notify(dodat, addNotification.build());
    }

    @Override
    public void zadatakObrisan(String zadatak) throws RemoteException {

    }

    @Override
    public void zadatakPromenjen(String zadatak) throws RemoteException {

    }

}
