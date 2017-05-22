package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class RemindService extends Service {

    private ArrayList<NoviZadatak> taskList;
    private RemindClass remindObject;

    private static int reminder = 2;
    private static final String TAG1 = "Usao";
    private static final String TAG2 = "Izasao";

    public RemindService() {

        final Handler handler = new Handler();
        Runnable thread = new Runnable() {

            @Override
            public void run() {

                taskList = NoviZadatakAdapter.getList();

                for(NoviZadatak zadatak : taskList) {

                    if(zadatak.getReminder()) {

                        Calendar cal = zadatak.getCalendar();
                        Calendar cal_at_moment = Calendar.getInstance();

                        if(cal.getTimeInMillis() - cal_at_moment.getTimeInMillis() < 900000){
                            Log.v(TAG1,"Usaoooooooooooooooooooooooooooooo");
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(RemindService.this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Task Manager")
                                    .setContentText(zadatak.getName() + " - 15 minuta do isteka zadatka");
                            MainActivity.notificationManager.notify(reminder, builder.build());
                            Log.v(TAG2,"Izisoooooooooooooooooooooooooooooo");
                            zadatak.setReminder(false);
                        }
                    }
                }
                handler.postDelayed(this, 60000);
            }
        };

        handler.postDelayed(thread, 1000);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Task Manager");
        remindObject = new RemindClass(builder);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return remindObject;
    }

}
