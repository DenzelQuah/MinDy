package com.example.mindyfindyourself.journal;

import android.content.Context;
import com.example.mindyfindyourself.model.JournalEntry;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import java.util.ArrayList;
import java.util.List;

// CRUD operations for Journal feature
public class JournalRepository {
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public JournalRepository(Context context) {
        this.context = context;
    }

    // CREATE: Add a new journal entry to Firebase
    public void addJournalEntry(JournalEntry entry) {
        String id = db.collection("journals").document().getId();
        entry.setId(id);
        db.collection("journals").document(id).set(entry);
    }

    // READ: Get all journal entries from Firebase
    public void getAllJournalEntries(OnSuccessListener<ArrayList<JournalEntry>> onSuccess, OnFailureListener onFailure) {
        db.collection("journals").get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                ArrayList<JournalEntry> list = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    JournalEntry entry = doc.toObject(JournalEntry.class);
                    list.add(entry);
                }
                onSuccess.onSuccess(list);
            })
            .addOnFailureListener(onFailure);
    }

    // UPDATE: Update a journal entry in Firebase
    public void updateJournalEntry(JournalEntry entry) {
        db.collection("journals").document(entry.getId()).set(entry);
    }

    // DELETE: Delete a journal entry from Firebase
    public void deleteJournalEntry(String entryId) {
        db.collection("journals").document(entryId).delete();
    }
}
