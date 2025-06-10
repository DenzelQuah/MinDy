package com.example.mindyfindyourself.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    private Context context;
    private List<Question> questions;
    private int[] selectedAnswers;

    public QuestionAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
        this.selectedAnswers = new int[questions.size()];
        for (int i = 0; i < selectedAnswers.length; i++) selectedAnswers[i] = -1;
    }

    @Override
    public int getCount() { return questions.size(); }

    @Override
    public Object getItem(int position) { return questions.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        TextView text1 = convertView.findViewById(android.R.id.text1);
        RadioGroup radioGroup = new RadioGroup(context);
        Question q = questions.get(position);
        text1.setText(q.getQuestion());
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        for (int i = 0; i < q.getOptions().length; i++) {
            RadioButton rb = new RadioButton(context);
            rb.setText(q.getOptions()[i]);
            int finalI = i;
            rb.setOnClickListener(v -> selectedAnswers[position] = finalI);
            radioGroup.addView(rb);
        }

        ((ViewGroup) convertView).removeAllViews();
        ((ViewGroup) convertView).addView(text1);
        ((ViewGroup) convertView).addView(radioGroup);

        if (selectedAnswers[position] != -1) {
            ((RadioButton) radioGroup.getChildAt(selectedAnswers[position])).setChecked(true);
        }
        return convertView;
    }

    public int[] getSelectedAnswers() { return selectedAnswers; }
}


class Question {
    private String question;
    private String[] options;
    private int[] scores; // Each option's score for emotion
    private String[] emotions; // e.g. {"Happy", "Stress", "Moody"}

    public Question(String question, String[] options) {
        this.question = question;
        this.options = options;
        this.scores = scores;
        this.emotions = emotions;
    }
    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int[] getScores() { return scores; }
    public String[] getEmotions() { return emotions; }
}
