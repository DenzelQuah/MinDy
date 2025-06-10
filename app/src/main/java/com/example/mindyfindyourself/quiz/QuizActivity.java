package com.example.mindyfindyourself.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mindyfindyourself.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QuizActivity extends Activity {

    private LinearLayout questionsContainer;
    private Button buttonSubmitQuiz;
    private ArrayList<Question> questions = new ArrayList<>();
    private int[] selectedAnswers;
    private ArrayList<RadioGroup> radioGroups = new ArrayList<>();
    private MaterialToolbar toolbar;
    private FirebaseFirestore db;
    private String updateId; // For editing an existing quiz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        toolbar = findViewById(R.id.toolbarQuiz);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        initializeViews();
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // Retrieve updateId if the quiz is being edited
        updateId = getIntent().getStringExtra("updateId");

        // Step 1: Setup questions and initialize selectedAnswers
        setupQuestions();

        // Step 2: Pre-fill answers if editing
        ArrayList<String> passedAnswers = getIntent().getStringArrayListExtra("answers");
        if (passedAnswers != null && passedAnswers.size() == questions.size()) {
            for (int i = 0; i < questions.size(); i++) {
                String option = passedAnswers.get(i);
                String[] options = questions.get(i).getOptions();
                for (int j = 0; j < options.length; j++) {
                    if (options[j].equals(option)) {
                        selectedAnswers[i] = j;
                        break;
                    }
                }
            }
        }

        // Step 3: Build the quiz UI with pre-filled answers if available
        createQuestionViews();

        // Step 4: Setup the submission button
        setupSubmitButton();
    }

    private void initializeViews() {
        questionsContainer = findViewById(R.id.questionsContainer);
        buttonSubmitQuiz = findViewById(R.id.buttonSubmitQuiz);
    }

    private void setupQuestions() {
        questions.clear();

        questions.add(new Question("How have you been feeling emotionally over the past few hours?",
                new String[]{"Energetic and cheerful", "Irritable or upset", "Overwhelmed or tense"}));

        questions.add(new Question("How would you describe your current level of motivation?",
                new String[]{"Highly motivated to get things done", "Struggling to focus or stay on track", "Uninterested and tired of everything"}));

        questions.add(new Question("How are you reacting to small problems today?",
                new String[]{"Taking them lightly or with humor", "Feeling annoyed or frustrated easily", "Getting anxious or stressed quickly"}));

        questions.add(new Question("How well are you connecting with people around you today?",
                new String[]{"Enjoying conversations and feeling sociable", "Feeling distant or easily annoyed", "Avoiding others or feeling emotionally drained"}));

        questions.add(new Question("What best describes your physical state right now?",
                new String[]{"Relaxed and refreshed", "Tired but managing", "Tense or exhausted"}));

        selectedAnswers = new int[questions.size()];
        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1; // -1 indicates no answer selected yet
        }
    }

    private void createQuestionViews() {
        questionsContainer.removeAllViews();
        radioGroups.clear();

        for (int i = 0; i < questions.size(); i++) {
            addQuestionView(i);
        }
    }

    private void addQuestionView(int index) {
        Question question = questions.get(index);

        // Setup question text view
        TextView questionText = new TextView(this);
        questionText.setText((index + 1) + ". " + question.getQuestion());
        questionText.setTextSize(16f);
        questionText.setPadding(32, 32, 32, 16);
        questionText.setTextColor(getResources().getColor(android.R.color.black));
        questionsContainer.addView(questionText);

        // Setup radio group to hold answer options
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setPadding(48, 0, 32, 32);

        for (int i = 0; i < question.getOptions().length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(question.getOptions()[i]);
            radioButton.setTextSize(14f);
            radioButton.setPadding(16, 8, 16, 8);
            radioButton.setId(View.generateViewId());

            // Pre-check option if editing an existing quiz
            if (selectedAnswers[index] == i) {
                radioButton.setChecked(true);
            }

            radioGroup.addView(radioButton);
        }

        final int questionIndex = index;
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    selectedAnswers[questionIndex] = i;
                    break;
                }
            }
        });

        questionsContainer.addView(radioGroup);
        radioGroups.add(radioGroup);
    }

    private void setupSubmitButton() {
        buttonSubmitQuiz.setOnClickListener(v -> submitQuiz());
    }

    private void submitQuiz() {
        // Ensure every question has an answer selected
        for (int answer : selectedAnswers) {
            if (answer == -1) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        int happy = 0, moody = 0, stressed = 0;
        ArrayList<String> answers = new ArrayList<>();

        // Calculate scores and compile the list of answers
        for (int i = 0; i < questions.size(); i++) {
            int selectedIndex = selectedAnswers[i];
            answers.add(questions.get(i).getOptions()[selectedIndex]);

            switch (selectedIndex) {
                case 0: happy++; break;
                case 1: moody++; break;
                case 2: stressed++; break;
            }
        }

        String resultMessage = determineResultMessage(happy, moody, stressed);
        String dominantMood = getDominantMood(happy, moody, stressed);

        saveQuizToFirestore(answers, happy, moody, stressed, dominantMood, resultMessage);
    }

    private void saveQuizToFirestore(ArrayList<String> answers, int happy, int moody, int stressed,
                                     String dominantMood, String resultMessage) {
        Map<String, Object> quizData = new HashMap<>();
        quizData.put("answers", answers);
        quizData.put("happyScore", happy);
        quizData.put("happyScore", happy);
        quizData.put("moodyScore", moody);
        quizData.put("stressedScore", stressed);
        quizData.put("dominantMood", dominantMood);
        quizData.put("resultMessage", resultMessage);
        // Record the current date in the "dd-MM-yyyy" format.
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = sdf.format(new Date());
        quizData.put("date", dateString);
        quizData.put("timestamp", System.currentTimeMillis());

        if (updateId != null && !updateId.isEmpty()) {
            // Update the existing quiz document
            db.collection("quizzes").document(updateId)
                    .set(quizData)
                    .addOnSuccessListener(unused -> launchResultActivity(dominantMood, resultMessage, happy, moody, stressed, answers, updateId))
                    .addOnFailureListener(e -> Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show());
        } else {
            // Create a new quiz document and capture its ID
            db.collection("quizzes")
                    .add(quizData)
                    .addOnSuccessListener(documentReference -> {
                        String newQuizId = documentReference.getId();
                        launchResultActivity(dominantMood, resultMessage, happy, moody, stressed, answers, newQuizId);
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Submission failed", Toast.LENGTH_SHORT).show());
        }
    }
    private void launchResultActivity(String dominantMood, String resultMessage,
                                      int happy, int moody, int stressed, ArrayList<String> answers, String quizId) {
        Intent intent = new Intent(this, QuizResultActivity.class);
        intent.putExtra("dominantMood", dominantMood);
        intent.putExtra("resultMessage", resultMessage);
        intent.putExtra("happyScore", happy);
        intent.putExtra("moodyScore", moody);
        intent.putExtra("stressedScore", stressed);
        intent.putStringArrayListExtra("answers", answers);
        intent.putExtra("quizId", quizId);
        startActivity(intent);
        finish();
    }

    private String determineResultMessage(int happy, int moody, int stressed) {
        if (happy > moody && happy > stressed)
            return "You seem to be feeling Happy!";
        else if (moody > happy && moody > stressed)
            return "You're in a Moody state today.";
        else if (stressed > happy && stressed > moody)
            return "You appear to be feeling Stressed.";
        else if (happy == stressed && happy > moody)
            return "You're feeling mixed—try to take a break and relax.";
        else if (happy == moody && happy > stressed)
            return "You're trying to stay positive but may feel emotionally up and down.";
        else if (moody == stressed && moody > happy)
            return "You might be tense and emotionally sensitive today.";
        else
            return "Your mood is quite balanced today.";
    }


    private String getDominantMood(int happy, int moody, int stressed) {
        if (happy > moody && happy > stressed) return "Happy";
        else if (moody > happy && moody > stressed) return "Moody";
        else if (stressed > happy && stressed > moody) return "Stressed";
        else return "Mixed";
    }
}
