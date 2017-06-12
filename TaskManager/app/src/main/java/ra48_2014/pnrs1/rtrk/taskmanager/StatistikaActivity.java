package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class StatistikaActivity extends AppCompatActivity{

    private RelativeLayout layout;
    private Button btnBack;
    private DatabaseHelper db;
    StatistikaView statistic;
    NativeCalc calc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika);

        int redSum = 0;
        int redFinishedSum = 0;
        int yellowSum = 0;
        int yellowFinishedSum = 0;
        int greenSum = 0;
        int greenFinishedSum = 0;
        calc = new  NativeCalc();
        db = new DatabaseHelper(this);
        NoviZadatak[] tasks = db.Read();

        if(tasks != null) {

            for (int i = 0; i < tasks.length; i++){

                switch(tasks[i].getPriority()) {
                    case 1 :
                        redSum++;
                        if(tasks[i].getFinished()){
                            redFinishedSum++;
                        }
                        break;
                    case 2 :
                        yellowSum++;
                        if(tasks[i].getFinished()){
                            yellowFinishedSum++;
                        }
                        break;
                    case 3 :
                        greenSum++;
                        if(tasks[i].getFinished()){
                            greenFinishedSum++;
                        }
                        break;
                }
            }
        }

        double redPercentage;
        double greenPercentage;
        double yellowPercentage;

        redPercentage = calc.calculatePercentage(redFinishedSum,redSum);
        greenPercentage = calc.calculatePercentage(greenFinishedSum,greenSum);
        yellowPercentage = calc.calculatePercentage(yellowFinishedSum,yellowSum);

        statistic = new StatistikaView(getBaseContext(), (int)redPercentage, (int)yellowPercentage, (int)greenPercentage);


        layout = (RelativeLayout) findViewById(R.id.statistika_layout);
        btnBack = (Button)findViewById(R.id.btnBack);

        layout.addView(statistic);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistic.animation.cancel(true);
                proceedOnMainActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        statistic.animation.cancel(true);
        proceedOnMainActivity();
    }

    public void proceedOnMainActivity() {
        Intent intent = new Intent(StatistikaActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
