package com.example.mindyfindyourself.mood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Mood Tracker Activity: CRUD for mood entries, uses Camera and GPS
class MoodTrackerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_mood_tracker)
        // TODO: Implement UI and CRUD logic for mood entries
        // TODO: Integrate Camera for photo
        // TODO: Integrate GPS for location
        // TODO: Connect to Firebase for data storage
    }
}

// Mood History Activity: List, update, delete mood entries
class MoodHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_mood_history)
        // TODO: Display list of mood entries from Firebase
        // TODO: Allow update/delete of entries
    }
}
