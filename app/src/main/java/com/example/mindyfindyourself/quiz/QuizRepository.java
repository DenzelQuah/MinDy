package com.example.mindyfindyourself.quiz;

import android.content.Context;
import com.example.mindyfindyourself.model.QuizResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import java.util.ArrayList;
import java.util.List;

// CRUD operations for Quiz feature
public class QuizRepository {
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public QuizRepository(Context context) {
        this.context = context;
    }

    // CREATE: Add a new quiz result to Firebase
    public void addQuizResult(QuizResult quizResult) {
        String id = db.collection("quizzes").document().getId();
        quizResult.setId(id);
        db.collection("quizzes").document(id).set(quizResult);
    }

    // READ: Get all quiz results from Firebase
    public void getAllQuizResults(OnSuccessListener<ArrayList<QuizResult>> onSuccess, OnFailureListener onFailure) {
        db.collection("quizzes").get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                ArrayList<QuizResult> list = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    QuizResult entry = doc.toObject(QuizResult.class);
                    list.add(entry);
                }
                onSuccess.onSuccess(list);
            })
            .addOnFailureListener(onFailure);
    }

    // UPDATE: Update a quiz result in Firebase
    public void updateQuizResult(QuizResult quizResult) {
        db.collection("quizzes").document(quizResult.getId()).set(quizResult);
    }

    // DELETE: Delete a quiz result from Firebase
    public void deleteQuizResult(String quizId) {
        db.collection("quizzes").document(quizId).delete();
    }
}
