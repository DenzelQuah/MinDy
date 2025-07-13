# MinDy - Mental Wellness Tracker

MinDy is a mobile wellness application designed to help Malaysian university and polytechnic students improve their emotional well-being. It offers a daily mood self-assessment quiz, reflective journaling, and a lifestyle health tracker. MinDy encourages self-awareness, builds resilience, and fosters healthier daily routines in a minimalist, user-friendly interface.

---

## Features

### 1) Mood Self-Assessment Quiz
- A 5-question daily quiz to classify your mood as **Happy**, **Stressed**, or **Moody**.
- Results are stored with timestamps for historical analysis.
- Uses the **proximity sensor** to monitor device usage behavior.
- Data saved in **Firebase Firestore**.
- Share, retake, update, or delete results.

### 2) Emotion Journal
- Reflective journaling space for tracking feelings, events, and triggers.
- Journal entries are tagged with mood score of the day.
- Includes **speech-to-text** via microphone.
- Uses **light sensor** to adjust brightness based on environment.
- CRUD support with Firebase integration.

### 3) Health Tracker
- Tracks **water intake**, **workout duration**, and **sleep hours**.
- Daily records auto-linked with current date.
- Grid view to browse historical data.
- Options to update, delete, and **share health logs**.

---

## Objectives
- Empower students to recognize emotional distress early.
- Encourage emotional journaling and reflection.
- Promote healthier lifestyle habits (hydration, exercise, sleep).
- Provide a lightweight, anonymous, and private wellness tool.

---

## 🧭 How to Use the App

### 1. 📥 Launch the App
- Open the **MinDy** app from your Android device.
- You will land on the **Home Screen** with navigation to all main features.

---

### 2. 🌤️ Take the Mood Self-Assessment Quiz
1. Tap on **"Self-Assessment Quiz"** on the home screen.
2. Answer 5 multiple-choice questions about your current mood.
3. Submit your answers to get your result: **Happy**, **Moody**, or **Stressed**.
4. On the result screen, you can:
   - **Share** your result
   - **Update** your answers
   - **Delete** the session
   - View past results on the **Calendar**

> 💡 *Proximity Sensor Tip: Keep your device at a normal distance for best tracking experience.*

---

### 3. 📝 Use the Emotion Journal
1. Tap on **"Emotion Journal"**.
2. View your existing entries (sorted by most recent).
3. Tap **"+"** to add a new journal entry.
4. Enter a **title** and **write your thoughts**.
   - Optional: Use the **microphone icon** for speech-to-text journaling.
5. Tap **Save**. Your entry will be saved with a timestamp and mood tag.

> 💡 *The screen brightness adjusts automatically based on your surroundings.*

---

### 4. 🏃‍♂️ Track Your Daily Health
1. Tap on **"Health Tracker"**.
2. Enter the following for today:
   - Water intake (cups/liters)
   - Workout hours
   - Sleep hours
3. Tap **"Save & View Records"**.
4. On the next screen (Grid View), you can:
   - **Update**, **Delete**, or **Share** any health log by long-pressing it.

> ⚠️ *Only one health record can be created or updated per day.*

---

### 5. 🔄 Navigation & Extras
- Use the back button or navigation menu to move between features.
- All data is securely stored in **Firebase Firestore**.
- No login is required — data is anonymous and device-based.

---
