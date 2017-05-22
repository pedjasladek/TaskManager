package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class StatistikaActivity extends AppCompatActivity{

    private RelativeLayout layout;
    private Button btnBack;
    StatistikaView statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika);

        statistic = new StatistikaView(getApplicationContext());

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
