package com.example.mindyfindyourself.journal;

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
import com.example.mindyfindyourself.model.JournalEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;

public class JournalActivity extends Activity {
    private static final int PICK_IMAGE = 1003;
    private EditText editTextJournal;
    private Button buttonAddPhoto, buttonAddLocation, buttonSaveJournal;
    private ListView listViewJournals;
    private Uri photoUri = null;
    private JournalRepository journalRepository;
    private JournalAdapter journalAdapter;
    private ArrayList<JournalEntry> journalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        editTextJournal = findViewById(R.id.editTextJournal);
        buttonAddPhoto = findViewById(R.id.buttonAddPhoto);
        buttonAddLocation = findViewById(R.id.buttonAddLocation);
        buttonSaveJournal = findViewById(R.id.buttonSaveJournal);
        listViewJournals = findViewById(R.id.listViewJournals);
        journalRepository = new JournalRepository(this);

        journalAdapter = new JournalAdapter(this, journalList);
        listViewJournals.setAdapter(journalAdapter);

        buttonAddPhoto.setOnClickListener(v -> pickImage());
        buttonAddLocation.setOnClickListener(v -> getLocation());
        buttonSaveJournal.setOnClickListener(v -> saveJournal());

        loadJournals();
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

    private void loadJournals() {
        journalRepository.getAllJournalEntries(list -> {
            journalList.clear();
            journalList.addAll(list);
            journalAdapter.updateData(journalList);
        }, e -> Toast.makeText(this, "Failed to load journals", Toast.LENGTH_SHORT).show());
    }

    private void saveJournal() {
        String text = editTextJournal.getText().toString();
        String photo = photoUri != null ? photoUri.toString() : null;
        // TODO: Get location string
        JournalEntry entry = new JournalEntry("", text, System.currentTimeMillis(), photo, null);
        journalRepository.addJournalEntry(entry);
        Toast.makeText(this, "Journal saved", Toast.LENGTH_SHORT).show();
        loadJournals();
    }
}
