package com.example.mindyfindyourself.mood;

import android.content.Context;
import com.example.mindyfindyourself.model.MoodEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class MoodAdapter extends android.widget.BaseAdapter {
    private List<MoodEntry> moodList;
    private Context context;

    public MoodAdapter(Context context, List<MoodEntry> moodList) {
        this.context = context;
        this.moodList = moodList;
    }

    @Override
    public int getCount() {
        return moodList.size();
    }

    @Override
    public Object getItem(int position) {
        return moodList.get(position);
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
        MoodEntry entry = moodList.get(position);
        android.widget.TextView text1 = convertView.findViewById(android.R.id.text1);
        android.widget.TextView text2 = convertView.findViewById(android.R.id.text2);
        text1.setText(entry.getMood());
        text2.setText("Date: " + new java.util.Date(entry.getDate()).toString());
        return convertView;
    }

    public void updateData(List<MoodEntry> newList) {
        this.moodList = newList;
        notifyDataSetChanged();
    }
}
