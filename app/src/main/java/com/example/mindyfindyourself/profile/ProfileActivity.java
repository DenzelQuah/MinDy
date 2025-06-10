package com.example.mindyfindyourself.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.mindyfindyourself.R;
import com.example.mindyfindyourself.model.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileActivity extends Activity {
    private static final int PICK_IMAGE = 1001;
    private EditText editTextName;
    private ImageView imageViewAvatar;
    private Button buttonSave, buttonDelete;
    private Uri avatarUri = null;
    private ProfileRepository profileRepository;
    private String userId = "user1"; // For demo, use a static userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextName = findViewById(R.id.editTextName);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);
        profileRepository = new ProfileRepository(this);

        imageViewAvatar.setOnClickListener(v -> pickImage());
        buttonSave.setOnClickListener(v -> saveProfileWithRepo());
        buttonDelete.setOnClickListener(v -> deleteProfileWithRepo());

        loadProfile();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            avatarUri = data.getData();
            imageViewAvatar.setImageURI(avatarUri);
        }
    }

    private void saveProfileWithRepo() {
        String name = editTextName.getText().toString();
        String avatar = avatarUri != null ? avatarUri.toString() : null;
        UserProfile profile = new UserProfile(userId, name, avatar);
        profileRepository.addUserProfile(profile);
        Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
    }

    private void loadProfile() {
        profileRepository.getUserProfile(userId, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    UserProfile profile = documentSnapshot.toObject(UserProfile.class);
                    if (profile != null) {
                        editTextName.setText(profile.getName());
                        if (profile.getAvatarUri() != null) {
                            avatarUri = Uri.parse(profile.getAvatarUri());
                            imageViewAvatar.setImageURI(avatarUri);
                        }
                    }
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteProfileWithRepo() {
        profileRepository.deleteUserProfile(userId, aVoid -> {
            editTextName.setText("");
            imageViewAvatar.setImageResource(R.mipmap.ic_launcher_round);
            avatarUri = null;
            Toast.makeText(this, "Profile deleted", Toast.LENGTH_SHORT).show();
        }, e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
