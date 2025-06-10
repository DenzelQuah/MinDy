package com.example.mindyfindyourself.reminder;

import android.content.Context;
import com.example.mindyfindyourself.model.Reminder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import java.util.ArrayList;
import java.util.List;

// CRUD operations for Reminder feature
public class ReminderRepository {
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ReminderRepository(Context context) {
        this.context = context;
    }

    // CREATE: Add a new reminder to Firebase
    public void addReminder(Reminder reminder) {
        String id = db.collection("reminders").document().getId();
        reminder.setId(id);
        db.collection("reminders").document(id).set(reminder);
    }

    // READ: Get all reminders from Firebase
    public void getAllReminders(OnSuccessListener<ArrayList<Reminder>> onSuccess, OnFailureListener onFailure) {
        db.collection("reminders").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Reminder> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Reminder entry = doc.toObject(Reminder.class);
                        list.add(entry);
                    }
                    onSuccess.onSuccess(list);
                })
                .addOnFailureListener(onFailure);
    }

    // UPDATE: Update a reminder in Firebase
    public void updateReminder(Reminder reminder) {
        db.collection("reminders").document(reminder.getId()).set(reminder);
    }

    // DELETE: Delete a reminder from Firebase
    public void deleteReminder(String reminderId) {
        db.collection("reminders").document(reminderId).delete();
    }
}
