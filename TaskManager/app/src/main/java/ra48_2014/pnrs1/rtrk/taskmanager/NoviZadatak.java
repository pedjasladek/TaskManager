package ra48_2014.pnrs1.rtrk.taskmanager;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by VELIKI on 4/24/2017.
 */

public class NoviZadatak implements Serializable {

    private String name;
    private String description;
    private Calendar calendar;
    private boolean reminder;
    private boolean finished;
    private int priority;

    public NoviZadatak() {
        name = "";
        description = "";
        priority = -1;
        calendar = Calendar.getInstance();
        reminder = false;
        finished = false;
    }

    /* Constructor */

    public NoviZadatak(String name, String description, boolean reminder, boolean finished, int priority, Calendar calendar) {
        this.name = name;
        this.description = description;
        this.reminder = reminder;
        this.finished = finished;
        this.priority = priority;
        this.calendar = calendar;
    }

    /* Getters, using them for getting data of already made element */

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) { this.priority = priority; }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) { this.calendar = calendar; }

    public boolean getReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) { this.reminder = reminder; }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) { this.finished = finished; }

}
