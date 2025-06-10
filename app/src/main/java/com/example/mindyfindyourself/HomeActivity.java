package com.example.mindyfindyourself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.mindyfindyourself.journal.JournalActivity;
import com.example.mindyfindyourself.mood.MoodActivity;
import com.example.mindyfindyourself.profile.ProfileActivity;
import com.example.mindyfindyourself.quiz.QuizActivity;
import com.example.mindyfindyourself.quiz.QuizResultActivity;
import com.example.mindyfindyourself.reminder.ReminderActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Link buttons from the XML
        Button buttonMood = findViewById(R.id.buttonMood);
        Button buttonJournal = findViewById(R.id.buttonJournal);
        Button buttonQuiz = findViewById(R.id.buttonQuiz);
        Button buttonReminder = findViewById(R.id.buttonReminder);
        Button buttonProfile = findViewById(R.id.buttonProfile);
        // New button for navigating to mood quiz result
        Button buttonMoodQuizResult = findViewById(R.id.buttonMoodQuizResult);

        // Existing navigation for other sections
        buttonMood.setOnClickListener(v -> startActivity(new Intent(this, MoodActivity.class)));
        buttonJournal.setOnClickListener(v -> startActivity(new Intent(this, JournalActivity.class)));
        buttonQuiz.setOnClickListener(v -> startActivity(new Intent(this, QuizActivity.class)));
        buttonReminder.setOnClickListener(v -> startActivity(new Intent(this, ReminderActivity.class)));
        buttonProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        // New navigation for Mood Quiz Result
        buttonMoodQuizResult.setOnClickListener(v -> {
            // Initialize Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Query the "quizzes" collection for the latest quiz result
            db.collection("quizzes")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .limit(1)
                    .get()
                    .addOnSuccessListener((QuerySnapshot queryDocumentSnapshots) -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Get the first (latest) document
                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);

                            // Retrieve information from Firestore
                            String resultMessage = doc.getString("resultMessage");
                            String dominantMood = doc.getString("dominantMood");
                            Long happyScore = doc.getLong("happyScore");
                            Long moodyScore = doc.getLong("moodyScore");
                            Long stressedScore = doc.getLong("stressedScore");
                            ArrayList<String> answers = (ArrayList<String>) doc.get("answers");
                            String quizId = doc.getId();

                            // Launch QuizResultActivity with the retrieved data
                            Intent intent = new Intent(HomeActivity.this, QuizResultActivity.class);
                            intent.putExtra("resultMessage", resultMessage);
                            intent.putExtra("dominantMood", dominantMood);
                            intent.putExtra("happyScore", happyScore != null ? happyScore.intValue() : 0);
                            intent.putExtra("moodyScore", moodyScore != null ? moodyScore.intValue() : 0);
                            intent.putExtra("stressedScore", stressedScore != null ? stressedScore.intValue() : 0);
                            intent.putStringArrayListExtra("answers", answers);
                            intent.putExtra("quizId", quizId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeActivity.this, "No quiz result found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(HomeActivity.this, "Error fetching quiz result", Toast.LENGTH_SHORT).show());
        });
    }
}
