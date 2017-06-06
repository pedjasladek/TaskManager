package ra48_2014.pnrs1.rtrk.taskmanager;

/**
 * Created by VELIKI on 6/4/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/* This class takes care of opening the database if it exists, creating it if it does not, and upgrading it as necessary */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database version, name and name of table in database

    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasksbase.db";
    public static final String TABLE_NAME = "Tasks";

    // Column strings for database

    public static final String NAME = "TaskName";
    public static final String DESCRIPTION = "TaskDescription";
    public static final String REMINDER = "TaskReminder";
    public static final String PRIORITY = "TaskPriority";
    public static final String DONE = "TaskDone";
    public static final String DAY = "TaskDay";
    public static final String MONTH = "TaskMonth";
    public static final String YEAR = "TaskYear";
    public static final String HOUR = "TaskHour";
    public static final String MINUTE = "TaskMinute";

    // Data base constructor

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);       //  context for creating database, database name, factory, database version is the number of database
    }

    // Called when the database is created for the first time.

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "      // Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data
                + TABLE_NAME + " ("
                + NAME +" TEXT, "
                + DESCRIPTION +" TEXT, "
                + REMINDER +" INT, "
                + DAY +" INT, "
                + MONTH +" INT, "
                + YEAR +" INT, "
                + HOUR +" INT, "
                + MINUTE +" INT, "
                + PRIORITY +" INT, "
                + DONE +" INT);");
    }

    // Called when the database needs to be upgraded. When changing version of the database.

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Method used for inserting new element in database.

    public void Insert(NoviZadatak mTask)
    {

        Calendar storageCalendar = mTask.getCalendar();

        int year = storageCalendar.get(Calendar.YEAR);
        int month = storageCalendar.get(Calendar.MONTH);
        int day = storageCalendar.get(Calendar.DAY_OF_MONTH);
        int hours = storageCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = storageCalendar.get(Calendar.MINUTE);

        // getWritableDatabase() - Create and/or open a database that will be used for reading and writing.
        // The first time this is called, the database will be opened and onCreate(SQLiteDatabase),
        // onUpgrade(SQLiteDatabase, int, int) and/or onOpen(SQLiteDatabase) will be called.

        SQLiteDatabase db = getWritableDatabase();

        // ContentValues - Temporary buffer used to insert or update values into database tables.

        ContentValues values = new ContentValues();

        // Preparing values to put in row.

        values.put(NAME, mTask.getName());
        values.put(PRIORITY, mTask.getPriority());
        values.put(DAY, day);
        values.put(MONTH, month);
        values.put(YEAR, year);
        values.put(HOUR, hours);
        values.put(MINUTE, minutes);
        values.put(REMINDER, mTask.getReminder() ? 1 : 0);
        values.put(DESCRIPTION, mTask.getDescription());
        values.put(DONE, mTask.getFinished() ? 1 : 0);

        // Method for inserting a row into the database.

        db.insert(TABLE_NAME, null, values);

        // Closing the database.

        close();

    }

    // Method for reading row in table.

    public NoviZadatak[] Read() {

        SQLiteDatabase db = getReadableDatabase();

        // Query is the given table.

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if (cursor.getCount() <= 0){
            return null;
        }

        NoviZadatak[] tasks = new NoviZadatak[cursor.getCount()];

        int i = 0;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            tasks[i++] = CreateTask(cursor);
        }

        close();
        return tasks;
    }

    // Method for updating database. First delete task so that updated task can be put.

    public void Update(NoviZadatak task, String name, String description, Calendar cal, int priority, boolean finished, boolean reminder) {

        Delete(task.getName());
        NoviZadatak mTask = new NoviZadatak(name, description, reminder, finished, priority, cal);
        Insert(mTask);
    }

    // Method for deleting row

    public void Delete(String name) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME, NAME + "=?", new String[] {name}); //   Method for deleting rows in the database. (From what table to delete from, row to delete)

        close();

    }

    // Method used for creating new task (used for reading tasks from array)

    public NoviZadatak CreateTask(Cursor cursor){

        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
        int priority = cursor.getInt(cursor.getColumnIndex(PRIORITY));
        int finished = cursor.getInt(cursor.getColumnIndex(DONE));
        int reminder = cursor.getInt(cursor.getColumnIndex(REMINDER));
        int year = cursor.getInt(cursor.getColumnIndex(YEAR));
        int month = cursor.getInt(cursor.getColumnIndex(MONTH));
        int hourOfDay = cursor.getInt(cursor.getColumnIndex(HOUR));
        int dayOfMonth = cursor.getInt(cursor.getColumnIndex(DAY));
        int minute = cursor.getInt(cursor.getColumnIndex(MINUTE));

        Calendar storageCalendar = Calendar.getInstance();
        storageCalendar.set(Calendar.MINUTE, minute);
        storageCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        storageCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        storageCalendar.set(Calendar.MONTH, month);
        storageCalendar.set(Calendar.YEAR, year);

        NoviZadatak task = new NoviZadatak(name, description, reminder == 1 ? true : false, finished == 1 ? true : false ,priority, storageCalendar);

        return task;

    }
}