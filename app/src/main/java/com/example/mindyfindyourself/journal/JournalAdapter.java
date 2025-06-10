package com.example.mindyfindyourself.journal;

import android.content.Context;
import com.example.mindyfindyourself.model.JournalEntry;
import java.util.List;

public class JournalAdapter extends android.widget.BaseAdapter {
    private List<JournalEntry> journalList;
    private Context context;

    public JournalAdapter(Context context, List<JournalEntry> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @Override
    public int getCount() {
        return journalList.size();
    }

    @Override
    public Object getItem(int position) {
        return journalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        JournalEntry entry = journalList.get(position);
        android.widget.TextView text1 = convertView.findViewById(android.R.id.text1);
        android.widget.TextView text2 = convertView.findViewById(android.R.id.text2);
        text1.setText(entry.getText());
        text2.setText("Date: " + new java.util.Date(entry.getDate()).toString());
        return convertView;
    }

    public void updateData(List<JournalEntry> newList) {
        this.journalList = newList;
        notifyDataSetChanged();
    }
}
