package com.example.mindyfindyourself.mood;

import android.content.Context;
import com.example.mindyfindyourself.model.MoodEntry;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import java.util.ArrayList;

// CRUD operations and sensor stubs for Mood feature
public class MoodRepository {
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MoodRepository(Context context) {
        this.context = context;
    }

    // CREATE: Add a new mood entry to Firebase
    public void addMood(MoodEntry moodEntry) {
        String id = db.collection("moods").document().getId();
        moodEntry.setId(id);
        db.collection("moods").document(id).set(moodEntry);
    }

    // READ: Get all mood entries from Firebase
    public void getAllMoods(OnSuccessListener<ArrayList<MoodEntry>> onSuccess, OnFailureListener onFailure) {
        db.collection("moods").get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                ArrayList<MoodEntry> list = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    MoodEntry entry = doc.toObject(MoodEntry.class);
                    list.add(entry);
                }
                onSuccess.onSuccess(list);
            })
            .addOnFailureListener(onFailure);
    }

    // UPDATE: Update a mood entry in Firebase
    public void updateMood(MoodEntry moodEntry) {
        db.collection("moods").document(moodEntry.getId()).set(moodEntry);
    }

    // DELETE: Delete a mood entry from Firebase
    public void deleteMood(String moodId) {
        db.collection("moods").document(moodId).delete();
    }

    // SENSOR: Capture photo using camera
    public void captureMoodPhoto() {
        // TODO: Implement camera intent
    }

    // SENSOR: Get current location
    public void getCurrentLocation() {
        // TODO: Implement location fetch
    }
}
