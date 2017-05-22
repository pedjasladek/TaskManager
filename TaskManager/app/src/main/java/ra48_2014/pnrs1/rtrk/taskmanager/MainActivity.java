package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private Button btnNoviZad;
    private Button btnStat;
    private ListView listView;
    private NoviZadatakAdapter adapter;
    public static NotificationManager notificationManager;  //NotificationManager - Class to notify the user of events that happen. This is how you tell the user that something has happened in the background.
    private IRemindAidlInterface remindInterface = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(getBaseContext(), RemindService.class);
        bindService(i, MainActivity.this, BIND_AUTO_CREATE);

        adapter = new NoviZadatakAdapter(getBaseContext());

        listView = (ListView) findViewById(R.id.listView);
        btnNoviZad = (Button) findViewById(R.id.new_task_btn);
        btnStat = (Button) findViewById(R.id.stats_btn);

        btnNoviZad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedOnAddActivity();
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedOnStatsActivity();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), NoviZadatakActivity.class);
                NoviZadatak task =  (NoviZadatak) listView.getItemAtPosition(position);
                i.putExtra("edit", task);
                startActivity(i);
                return true;
            }
        });

        listView.setAdapter(adapter);
    }

    public void proceedOnAddActivity() {
        Intent i = new Intent(getBaseContext(), NoviZadatakActivity.class);
        startActivityForResult(i, 1);

    }

    public void proceedOnStatsActivity() {
        Intent intent = new Intent(MainActivity.this, StatistikaActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                NoviZadatak task = (NoviZadatak) data.getSerializableExtra(getResources().getString(R.string.result));
                adapter.addTask(task);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Task Manager")
                        .setContentText(task.getName() + " - dodat");

                remindInterface = new RemindClass(builder);
                try {
                    remindInterface.zadatakDodat(task.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        remindInterface = IRemindAidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
