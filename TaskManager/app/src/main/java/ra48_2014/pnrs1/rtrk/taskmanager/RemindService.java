package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class RemindService extends Service {

    private ArrayList<NoviZadatak> taskList;
    private IBinder binder = new ReminderBinder();
    private DatabaseHelper db;

    private static int reminder = 2;
    private static final String TAG1 = "Usao";
    private static final String TAG2 = "Izasao";

    public RemindService() {

        db = new DatabaseHelper(this);
        final Handler handler = new Handler();
        Runnable thread = new Runnable() {

            @Override
            public void run() {

                taskList.clear();


                NoviZadatak[] tasks = db.Read();
                if(tasks != null) {
                    for (int i = 0; i < tasks.length; i++) {
                        taskList.add(tasks[i]);
                    }
                }

                for(NoviZadatak zadatak : taskList) {

                    Calendar cal = zadatak.getCalendar();
                    Calendar cal_at_moment = Calendar.getInstance();

                    if(cal.getTimeInMillis() - cal_at_moment.getTimeInMillis() <= 0){
                        zadatak.setFinished(true);
                        zadatak.setReminder(false);
                        db.Update(zadatak, zadatak.getName().toString(),
                                zadatak.getDescription().toString(),
                                zadatak.getCalendar(),
                                zadatak.getPriority(),
                                zadatak.getFinished(),
                                zadatak.getReminder());
                    }

                    if (zadatak.getReminder() && !zadatak.getFinished()) {
                        if (cal.getTimeInMillis() - cal_at_moment.getTimeInMillis() < 900000) {
                            Log.v(TAG1, "Usaoooooooooooooooooooooooooooooo");
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(RemindService.this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Task Manager")
                                    .setContentText(zadatak.getName() + " - 15 minuta do isteka zadatka");
                            MainActivity.notificationManager.notify(reminder, builder.build());
                            Log.v(TAG2, "Izisoooooooooooooooooooooooooooooo");
                            zadatak.setReminder(false);

                            db.Update(zadatak, zadatak.getName().toString(),
                                    zadatak.getDescription().toString(),
                                    zadatak.getCalendar(),
                                    zadatak.getPriority(),
                                    zadatak.getFinished(),
                                    zadatak.getReminder());
                        }

                    }
                }
                handler.postDelayed(this, 60000);
            }
        };

        handler.postDelayed(thread, 1000);
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Task Manager");

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        Bundle bundle = intent.getExtras();
        taskList = ((ArrayList<NoviZadatak>) bundle.get("Task"));

        return binder;
    }

    public class ReminderBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }

    public void update(NoviZadatak[] tasks) {
        taskList.clear();
        if (tasks != null) {

            for (int i = 0; i < tasks.length; i++) {
                taskList.add(tasks[i]);
            }
        }
    }
}


