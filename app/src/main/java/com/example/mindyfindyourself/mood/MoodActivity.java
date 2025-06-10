package com.example.mindyfindyourself.mood;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.mindyfindyourself.R;
import com.example.mindyfindyourself.model.MoodEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;

public class MoodActivity extends Activity {
    private static final int PICK_IMAGE = 1002;
    private EditText editTextMood;
    private Button buttonCapturePhoto, buttonGetLocation, buttonSaveMood;
    private ListView listViewMoods;
    private Uri photoUri = null;
    private MoodRepository moodRepository;
    private MoodAdapter moodAdapter;
    private ArrayList<MoodEntry> moodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        editTextMood = findViewById(R.id.editTextMood);
        buttonCapturePhoto = findViewById(R.id.buttonCapturePhoto);
        buttonGetLocation = findViewById(R.id.buttonGetLocation);
        buttonSaveMood = findViewById(R.id.buttonSaveMood);
        listViewMoods = findViewById(R.id.listViewMoods);
        moodRepository = new MoodRepository(this);

        moodAdapter = new MoodAdapter(this, moodList);
        listViewMoods.setAdapter(moodAdapter);
        loadMoods();

        buttonCapturePhoto.setOnClickListener(v -> pickImage());
        buttonGetLocation.setOnClickListener(v -> getLocation());
        buttonSaveMood.setOnClickListener(v -> saveMood());
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
            photoUri = data.getData();
            Toast.makeText(this, "Photo selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLocation() {
        // TODO: Implement location fetch logic
        Toast.makeText(this, "Location feature not implemented", Toast.LENGTH_SHORT).show();
    }

    private void saveMood() {
        String mood = editTextMood.getText().toString();
        String photo = photoUri != null ? photoUri.toString() : null;
        // TODO: Get location string
        MoodEntry entry = new MoodEntry("", mood, System.currentTimeMillis(), photo, null);
        moodRepository.addMood(entry);
        Toast.makeText(this, "Mood saved", Toast.LENGTH_SHORT).show();
        loadMoods();
    }

    private void loadMoods() {
        moodRepository.getAllMoods(list -> {
            moodList.clear();
            moodList.addAll(list);
            moodAdapter.updateData(moodList);
        }, e -> Toast.makeText(this, "Failed to load moods", Toast.LENGTH_SHORT).show());
    }
}
