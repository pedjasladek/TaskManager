package ra48_2014.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by VELIKI on 4/24/2017.
 */

public class NoviZadatakAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NoviZadatak> taskList;

    private Date date;
    private Calendar tmpCal;
    private Calendar tmpCal1;
    private Calendar tmpCal2;

    /* Constructor */

    public NoviZadatakAdapter(Context context) {
        this.mContext = context;
        taskList = new ArrayList<NoviZadatak>();
    }
    public void addTask(NoviZadatak task) {
        taskList.add(task);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = taskList.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_element, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name_text);
            holder.date = (TextView) view.findViewById(R.id.edit);
            holder.urg = (Button) view.findViewById(R.id.colorBtn);
            holder.checkBox = (CheckBox) view.findViewById(R.id.checkBoxElem);
            holder.urgButton = (Button) view.findViewById(R.id.urgBtn);
            view.setTag(holder);
        }

        date = new Date();
        tmpCal = Calendar.getInstance();
        tmpCal1 = Calendar.getInstance();
        tmpCal2 = Calendar.getInstance();

        NoviZadatak task = (NoviZadatak) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.name.setText(task.getName());
        tmpCal = task.getCalendar();
        date = task.getCalendar().getTime();
        tmpCal1.add(Calendar.DAY_OF_YEAR, 1);
        tmpCal2.add(Calendar.DAY_OF_YEAR, 7);

        if (task.getPriority() == 1) {
            holder.urg.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        } else if (task.getPriority() == 2) {
            holder.urg.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
        } else if (task.getPriority() == 3) {
            holder.urg.setBackgroundColor(mContext.getResources().getColor(R.color.green));
        }

        if (task.getReminder())
            holder.urgButton.setBackgroundColor(mContext.getResources().getColor(R.color.black));
        else
            holder.urgButton.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        if (tmpCal.get(Calendar.DAY_OF_YEAR) < tmpCal2.get(Calendar.DAY_OF_YEAR)) {
            switch (tmpCal.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    holder.date.setText("Ponedeljak u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.TUESDAY:
                    holder.date.setText("Utorak u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.WEDNESDAY:
                    holder.date.setText("Sreda u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.THURSDAY:
                    holder.date.setText("Cetvrtak u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.FRIDAY:
                    holder.date.setText("Petak u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.SATURDAY:
                    holder.date.setText("Subota u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.SUNDAY:
                    holder.date.setText("Nedelja u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
                    break;
            }
        } else if (tmpCal.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            holder.date.setText("Danas u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
        } else if (tmpCal.get(Calendar.DAY_OF_YEAR) == tmpCal1.get(Calendar.DAY_OF_YEAR)) {
            holder.date.setText("Sutra u " + tmpCal.get(Calendar.HOUR_OF_DAY) + "" + " : " + tmpCal.get(Calendar.MINUTE) + "");
        }
        else {
            holder.date.setText(tmpCal.get(Calendar.DAY_OF_MONTH) + "" + "/" + (tmpCal.get(Calendar.MONTH) + 1) + ""
                    + "/" + tmpCal.get(Calendar.YEAR) + "" + " u " + tmpCal.get(Calendar.HOUR_OF_DAY) + ""
                    + ":" + tmpCal.get(Calendar.MINUTE) + "");
        }

        holder.checkBox.setChecked(task.getFinished());

        if (task.getFinished())
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        return view;
    }

    private class ViewHolder {
        public TextView name = null;
        public TextView date = null;
        public CheckBox checkBox = null;
        public Button urg = null;
        public Button urgButton = null;
    }
}
