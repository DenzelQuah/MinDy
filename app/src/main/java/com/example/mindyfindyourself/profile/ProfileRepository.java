package com.example.mindyfindyourself.profile;

import android.content.Context;
import com.example.mindyfindyourself.model.UserProfile;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

// CRUD operations for User Profile feature
public class ProfileRepository {
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProfileRepository(Context context) {
        this.context = context;
    }

    // CREATE: Add a new user profile to Firebase
    public void addUserProfile(UserProfile profile) {
        db.collection("profiles").document(profile.getId()).set(profile);
    }

    // READ: Get user profile from Firebase
    public void getUserProfile(String userId, OnSuccessListener<DocumentSnapshot> onSuccess, OnFailureListener onFailure) {
        db.collection("profiles").document(userId).get()
            .addOnSuccessListener(onSuccess)
            .addOnFailureListener(onFailure);
    }

    // UPDATE: Update a user profile in Firebase
    public void updateUserProfile(UserProfile profile) {
        db.collection("profiles").document(profile.getId()).set(profile);
    }

    // DELETE: Delete a user profile from Firebase
    public void deleteUserProfile(String userId, OnSuccessListener<Void> onSuccess, OnFailureListener onFailure) {
        db.collection("profiles").document(userId).delete()
            .addOnSuccessListener(onSuccess)
            .addOnFailureListener(onFailure);
    }
}
