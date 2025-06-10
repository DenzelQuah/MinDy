package com.example.mindyfindyourself.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mindyfindyourself.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

public class QuizResultActivity extends Activity {

    private TextView textViewResultMessage;
    private TextView textViewScoreBreakdown;
    private Button buttonRetakeQuiz;
    private Button buttonBackToMain;
    private Button buttonUpdateResult;
    private Button buttonDeleteResult;
    private CalendarView calendarViewQuiz;

    private ArrayList<String> answers;
    private String quizId;
    private String dominantMood;
    private String resultMessage;
    private int happyScore, moodyScore, stressedScore;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        initializeViews();
        db = FirebaseFirestore.getInstance();
        displayResults();
        setupButtons();
        setupCalendar(); // NEW
        updateUI();
    }

    private void initializeViews() {
        textViewResultMessage = findViewById(R.id.textViewResultMessage);
        textViewScoreBreakdown = findViewById(R.id.textViewScoreBreakdown);
        buttonRetakeQuiz = findViewById(R.id.buttonRetakeQuiz);
        buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonUpdateResult = findViewById(R.id.buttonUpdateResult);
        buttonDeleteResult = findViewById(R.id.buttonDeleteResult);
        calendarViewQuiz = findViewById(R.id.calendarViewQuiz); // Ensure your layout includes this
    }

    private void displayResults() {
        Intent intent = getIntent();
        resultMessage = intent.getStringExtra("resultMessage");
        dominantMood = intent.getStringExtra("dominantMood");
        happyScore = intent.getIntExtra("happyScore", 0);
        moodyScore = intent.getIntExtra("moodyScore", 0);
        stressedScore = intent.getIntExtra("stressedScore", 0);
        answers = intent.getStringArrayListExtra("answers");
        quizId = intent.getStringExtra("quizId");
    }

    private void updateUI() {
        textViewResultMessage.setText(resultMessage != null ? resultMessage : "No result available");
        String scoreBreakdown = String.format("Score Breakdown:\nHappy: %d/5\nMoody: %d/5\nStressed: %d/5\n\nDominant Mood: %s",
                happyScore, moodyScore, stressedScore, (dominantMood != null ? dominantMood : "N/A"));
        textViewScoreBreakdown.setText(scoreBreakdown);
    }

    private void setupButtons() {
        buttonRetakeQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        buttonBackToMain.setOnClickListener(v -> finish());

        buttonUpdateResult.setOnClickListener(v -> {
            if (quizId == null || answers == null) {
                Toast.makeText(QuizResultActivity.this, "Cannot update this result.", Toast.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(QuizResultActivity.this)
                    .setTitle("Update Quiz")
                    .setMessage("Do you want to update your quiz answers?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
                        intent.putStringArrayListExtra("answers", answers);
                        intent.putExtra("updateId", quizId);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        buttonDeleteResult.setOnClickListener(v -> {
            if (quizId == null) {
                Toast.makeText(QuizResultActivity.this, "Cannot delete this result.", Toast.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(QuizResultActivity.this)
                    .setTitle("Delete Result")
                    .setMessage("Are you sure you want to delete your quiz result?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        db.collection("quizzes").document(quizId)
                                .delete()
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(QuizResultActivity.this, "Result deleted.", Toast.LENGTH_SHORT).show();
                                    textViewResultMessage.setText("Result deleted.");
                                    textViewScoreBreakdown.setText("No result available.");
                                    buttonUpdateResult.setEnabled(false);
                                    buttonDeleteResult.setEnabled(false);
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(QuizResultActivity.this, "Failed to delete result.", Toast.LENGTH_SHORT).show());
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void setupCalendar() {
        calendarViewQuiz.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year);
            db.collection("quizzes")
                    .whereEqualTo("date", selectedDate)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);

                            // Extract values
                            this.resultMessage = doc.getString("resultMessage");
                            this.dominantMood = doc.getString("dominantMood");
                            this.happyScore = doc.getLong("happyScore") != null ? Objects.requireNonNull(doc.getLong("happyScore")).intValue() : 0;
                            this.moodyScore = doc.getLong("moodyScore") != null ? Objects.requireNonNull(doc.getLong("moodyScore")).intValue() : 0;
                            this.stressedScore = doc.getLong("stressedScore") != null ? Objects.requireNonNull(doc.getLong("stressedScore")).intValue() : 0;
                            this.answers = (ArrayList<String>) doc.get("answers");
                            this.quizId = doc.getId();

                            // Update UI directly
                            updateUI();
                            buttonUpdateResult.setEnabled(true);
                            buttonDeleteResult.setEnabled(true);

                        } else {
                            // Clear UI if no result found
                            textViewResultMessage.setText("No result found for selected date.");
                            textViewScoreBreakdown.setText("");
                            buttonUpdateResult.setEnabled(false);
                            buttonDeleteResult.setEnabled(false);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error fetching result: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

}
