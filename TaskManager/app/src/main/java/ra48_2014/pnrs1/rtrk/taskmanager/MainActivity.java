package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Button btnNoviZad;
    private Button btnStat;
    private ListView listView;
    private NoviZadatakAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }
        }
    }
}
