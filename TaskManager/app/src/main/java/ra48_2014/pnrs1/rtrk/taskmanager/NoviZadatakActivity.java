package ra48_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NoviZadatakActivity extends AppCompatActivity{

    private Intent intent;

    private Button add_btn;
    private Button cancel_btn;
    private Button red_btn;
    private Button yellow_btn;
    private Button green_btn;
    private Button time_date_btn;

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar store_time_date;
    private Calendar calendar;
    private Calendar temp;

    private EditText name_txt;
    private EditText description_txt;

    private TextView time_txt;
    private TextView date_txt;

    private CheckBox check_box;

    private boolean flag = false;
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean edit = false;
    private boolean finished = false;
    private boolean reminder;

    private int priority;

    private NoviZadatak task;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_zadatak);

        db = new DatabaseHelper(this);

        Log.d("Novi zadatak usao", "Novi zadatak activity napravljen");

        store_time_date = Calendar.getInstance();
        temp = Calendar.getInstance();

        add_btn = (Button) findViewById(R.id.add_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        red_btn = (Button) findViewById(R.id.red_btn);
        yellow_btn = (Button) findViewById(R.id.yellow_btn);
        green_btn = (Button) findViewById(R.id.green_btn);
        name_txt = (EditText) findViewById(R.id.new_task_txt);
        description_txt = (EditText) findViewById(R.id.description_txt);
        time_date_btn = (Button) findViewById(R.id.time_date_btn);
        time_txt = (TextView) findViewById(R.id.time_txt);
        date_txt = (TextView) findViewById(R.id.date_txt);
        check_box = (CheckBox) findViewById(R.id.check_box);

        Log.d("Naduvaj se kite", "Napravio getter-e");

        intent = getIntent();
        if (intent.hasExtra("edit")) {

            edit = true;
            task = (NoviZadatak) intent.getSerializableExtra("edit");
            add_btn.setText(R.string.sacuvaj);
            cancel_btn.setText(R.string.obrisi);
            name_txt.setText(task.getName());
            calendar = task.getCalendar();
            description_txt.setText(task.getDescription());
            store_time_date = task.getCalendar();
            date_txt.setText(store_time_date.get(Calendar.DAY_OF_MONTH) + ""
                    + "/" + store_time_date.get(Calendar.MONTH) + ""
                    + "/" + store_time_date.get(Calendar.YEAR) + "");
            time_txt.setText(store_time_date.get(Calendar.HOUR_OF_DAY) + ""
                    + ":" + store_time_date.get(Calendar.MINUTE) + "");
            if (task.getReminder())
                check_box.setChecked(true);

            switch (task.getPriority()) {
                case 1:
                    green_btn.setEnabled(false);
                    yellow_btn.setEnabled(false);
                    priority = 1;
                    break;
                case 2:
                    red_btn.setEnabled(false);
                    green_btn.setEnabled(false);
                    priority = 2;
                    break;
                case 3:
                    red_btn.setEnabled(false);
                    yellow_btn.setEnabled(false);
                    priority = 3;
                    break;
            }
        } else {
            edit = false;
        }

         /* Called when the user presses "Dodaj" button or "Sacuvaj" */

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit) {
                    if (flag && flag1 && flag2 && !name_txt.getText().toString().isEmpty() && !description_txt.getText().toString().isEmpty()) {
                        finished = (store_time_date.getTimeInMillis() < temp.getTimeInMillis()) ? true : false;
                        task = new NoviZadatak(name_txt.getText().toString(), description_txt.getText().toString(), check_box.isChecked(), finished, priority, store_time_date);

                        Intent i = new Intent(NoviZadatakActivity.this, MainActivity.class);

                        i.putExtra(getResources().getString(R.string.result), task);
                        setResult(Activity.RESULT_OK, i);
                        finish();

                    } else {
                        toastEmpty();
                    }
                } else {

                        finished = (store_time_date.getTimeInMillis() < temp.getTimeInMillis()) ? true : false;
                        reminder = (check_box.isChecked()) ? true : false;

                        db.Update(task, name_txt.getText().toString(), description_txt.getText().toString(), calendar, priority, finished, reminder);

                        Intent i = new Intent(NoviZadatakActivity.this, MainActivity.class);
                        setResult(Activity.RESULT_OK, i);

                        finish();
                }
            }
        });

        /* Called when the user presses "Otkazi" button or "Obrisi" */

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit) {
                    toastCancel();
                    finish();
                }else{

                    db.Delete(task.getName());

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    setResult(Activity.RESULT_OK, i);

                    finish();
                }
            }
        });


        /* Called when the user presses "Vreme i datum" button */

        time_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTimeDate();
            }
        });

        /* Called when the user presses red button */

        red_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableYellowAndGreen();
                toastRed();
                priority = 1;
            }
        });

        /* Called when the user presses yellow button */

        yellow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableRedAndGreen();
                toastYellow();
                priority = 2;
            }
        });

        /* Called when the user presses green button */

        green_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableRedAndYellow();
                toastGreen();
                priority = 3;
            }
        });

        /* Called when the user is done setting a new time and the dialog has closed. */

        onTimeSetListener = new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time_txt.setText(Integer.toString(hourOfDay) + "h : " + Integer.toString(minute) + "m");
                toastTime(hourOfDay, minute);
                flag1 = true;
                store_time_date.set(Calendar.MINUTE, minute);
                store_time_date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            }
        };

        /* Called when the user is done setting a new date and the dialog has closed. */

        onDateSetListener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_txt.setText(Integer.toString(dayOfMonth) + " - " + Integer.toString(month) + " - " + Integer.toString(year));
                toastDate(year, month, dayOfMonth);
                flag2 = true;
                store_time_date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                store_time_date.set(Calendar.MONTH, month);
                store_time_date.set(Calendar.YEAR, year);

            }
        };

        /* Called when user presses on check box */

        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastCheckBox();
            }
        });
    }

    /* Gets current date, time and opens date and time dialogs*/

    public void dialogTimeDate() {

        Calendar c = Calendar.getInstance();
        new TimePickerDialog(NoviZadatakActivity.this, onTimeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        new DatePickerDialog(NoviZadatakActivity.this, onDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    /* Jumps to new to MainActivity */

    public void proceedOnMainActivity() {
        Intent intent = new Intent(NoviZadatakActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /* Disables yellow and green button */

    public void disableYellowAndGreen() {
        yellow_btn.setEnabled(false);
        green_btn.setEnabled(false);
        flag = true;
    }

    /* Disables red and green button */

    public void disableRedAndGreen() {
        red_btn.setEnabled(false);
        green_btn.setEnabled(false);
        flag = true;
    }

    /* Disables red and yellow button */

    public void disableRedAndYellow() {
        red_btn.setEnabled(false);
        yellow_btn.setEnabled(false);
        flag = true;
    }

    /* Toast messages */

    public void toastAdd() {
        Toast.makeText(this, "Dodali ste zadatak", Toast.LENGTH_LONG).show();
    }

    public void toastCancel() {
        Toast.makeText(this, "Zadatak je otkazan", Toast.LENGTH_LONG).show();
    }

    public void toastEmpty() {
        Toast.makeText(this, "Niste popunili sva polja", Toast.LENGTH_LONG).show();
    }

    public void toastRed() {
        Toast.makeText(this, "Zuti i zeleni tasteri su iskljuceni", Toast.LENGTH_LONG).show();
    }

    public void toastYellow() {
        Toast.makeText(this, "Crveni i zeleni tasteri su iskljuceni", Toast.LENGTH_LONG).show();
    }

    public void toastGreen() {
        Toast.makeText(this, "Crveni i zuti tasteri su iskljuceni", Toast.LENGTH_LONG).show();
    }

    public void toastTime(int hourOfDay, int minute) {
        Toast.makeText(this, "Vreme: " + Integer.toString(hourOfDay) + "h : " + Integer.toString(minute) + "m", Toast.LENGTH_LONG).show();
    }

    public void toastDate(int year, int month, int dayOfMonth) {
        Toast.makeText(this, "Datum: " + Integer.toString(year) + " - " + Integer.toString(month) + " - " + Integer.toString(dayOfMonth), Toast.LENGTH_LONG).show();
    }

     /* Checking if check box is selected or not */

    public void toastCheckBox() {
        if(check_box.isChecked()){
            Toast.makeText(this, "Podsecanje 15 minute pre isteka ukljuceno", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Podsecanje 15 minute pre isteka iskljuceno", Toast.LENGTH_LONG).show();
        }
    }

}
