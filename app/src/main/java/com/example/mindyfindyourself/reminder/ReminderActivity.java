package com.example.mindyfindyourself.reminder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.mindyfindyourself.R;
import com.example.mindyfindyourself.model.Reminder;
import java.util.ArrayList;

public class ReminderActivity extends Activity {
    private EditText editTextReminder;
    private Button buttonSetTime, buttonSaveReminder;
    private ListView listViewReminders;
    private ReminderRepository reminderRepository;
    private ReminderAdapter reminderAdapter;
    private ArrayList<Reminder> reminderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        editTextReminder = findViewById(R.id.editTextReminder);
        buttonSetTime = findViewById(R.id.buttonSetTime);
        buttonSaveReminder = findViewById(R.id.buttonSaveReminder);
        listViewReminders = findViewById(R.id.listViewReminders);
        reminderRepository = new ReminderRepository(this);

        reminderAdapter = new ReminderAdapter(this, reminderList);
        listViewReminders.setAdapter(reminderAdapter);

        buttonSetTime.setOnClickListener(v -> setTime());
        buttonSaveReminder.setOnClickListener(v -> saveReminder());

        loadReminders();
    }

    private void setTime() {
        // TODO: Implement time picker
        Toast.makeText(this, "Time picker not implemented", Toast.LENGTH_SHORT).show();
    }

    private void loadReminders() {
        reminderRepository.getAllReminders(list -> {
            reminderList.clear();
            reminderList.addAll(list);
            reminderAdapter.updateData(reminderList);
        }, e -> Toast.makeText(this, "Failed to load reminders", Toast.LENGTH_SHORT).show());
    }

    private void saveReminder() {
        String message = editTextReminder.getText().toString();
        // TODO: Get time from time picker
        Reminder reminder = new Reminder("", message, System.currentTimeMillis());
        reminderRepository.addReminder(reminder);
        Toast.makeText(this, "Reminder saved", Toast.LENGTH_SHORT).show();
        loadReminders();
    }
}
