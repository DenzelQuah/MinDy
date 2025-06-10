package com.example.mindyfindyourself.quiz;

import android.content.Context;
import com.example.mindyfindyourself.model.QuizResult;
import java.util.List;

public class QuizAdapter extends android.widget.BaseAdapter {
    private List<QuizResult> quizList;
    private Context context;

    public QuizAdapter(Context context, List<QuizResult> quizList) {
        this.context = context;
        this.quizList = quizList;
    }

    @Override
    public int getCount() {
        return quizList.size();
    }

    @Override
    public Object getItem(int position) {
        return quizList.get(position);
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
        QuizResult entry = quizList.get(position);
        android.widget.TextView text1 = convertView.findViewById(android.R.id.text1);
        android.widget.TextView text2 = convertView.findViewById(android.R.id.text2);
        text1.setText("Score: " + entry.getScore());
        text2.setText("Date: " + new java.util.Date(entry.getDate()).toString());
        return convertView;
    }

    public void updateData(List<QuizResult> newList) {
        this.quizList = newList;
        notifyDataSetChanged();
    }
}
