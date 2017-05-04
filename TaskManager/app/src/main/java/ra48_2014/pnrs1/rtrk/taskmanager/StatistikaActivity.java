package ra48_2014.pnrs1.rtrk.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.StackView;


public class StatistikaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatistikaView statistic = new StatistikaView(getApplicationContext());

        setContentView(statistic);
    }




}
