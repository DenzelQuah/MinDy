package com.example.mindyfindyourself.reminder;

import android.content.Context;
import com.example.mindyfindyourself.model.Reminder;
import java.util.List;

public class ReminderAdapter extends android.widget.BaseAdapter {
    private List<Reminder> reminderList;
    private Context context;

    public ReminderAdapter(Context context, List<Reminder> reminderList) {
        this.context = context;
        this.reminderList = reminderList;
    }

    @Override
    public int getCount() {
        return reminderList.size();
    }

    @Override
    public Object getItem(int position) {
        return reminderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent,
                    false);
        }
        Reminder entry = reminderList.get(position);
        android.widget.TextView text1 = convertView.findViewById(android.R.id.text1);
        android.widget.TextView text2 = convertView.findViewById(android.R.id.text2);
        text1.setText(entry.getMessage());
        text2.setText("Time: " + new java.util.Date(entry.getTime()).toString());
        return convertView;
    }

    public void updateData(List<Reminder> newList) {
        this.reminderList = newList;
        notifyDataSetChanged();
    }
}
