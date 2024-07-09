package com.dev.woof

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.dev.woof.reminder.Reminder
import com.dev.woof.reminder.ReminderAdapter
import com.dev.woof.reminder.ReminderDatabase
import com.dev.woof.reminder.ReminderReceiver
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReminderAdapter
    private val reminders = mutableListOf<Reminder>()

    // Room Database
    private lateinit var reminderDatabase: ReminderDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Room Database
        reminderDatabase = Room.databaseBuilder(
            requireContext(),
            ReminderDatabase::class.java, "reminder-db"
        ).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ReminderAdapter(reminders) { reminder ->
            deleteReminder(reminder)
        }

        recyclerView.adapter = adapter

        // Observe reminders from Room database
        observeReminders()

        val fabAddReminder: FloatingActionButton = view.findViewById(R.id.fabAddReminder)
        fabAddReminder.setOnClickListener {
            showAddReminderDialog()
        }

        return view
    }

    private fun deleteReminder(reminder: Reminder) {
        lifecycleScope.launch {
            reminderDatabase.dao.deleteReminder(reminder)
        }
    }

    private fun showAddReminderDialog() {
        // Inflate your custom layout for the dialog
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_reminder, null)

        // Find views within the custom layout
        val causeEditText = dialogView.findViewById<EditText>(R.id.causeEditText)
        val hourEditText = dialogView.findViewById<EditText>(R.id.hourEditText)
        val minuteEditText = dialogView.findViewById<EditText>(R.id.minuteEditText)
        val amPmEditText = dialogView.findViewById<EditText>(R.id.amPmEditText)

        // Create a MaterialAlertDialogBuilder instance
        val builder = MaterialAlertDialogBuilder(requireContext())

        // Set up AM/PM dropdown menu
        val amPmOptions = arrayOf("AM", "PM")
        amPmEditText.setOnClickListener {
            showAmPmDialog(amPmOptions, amPmEditText)
        }

        // Customizing the dialog appearance and behavior
        builder.setView(dialogView)  // Set custom view
            .setTitle("Add Reminder")  // Set dialog title
            .setPositiveButton("Add") { _, _ ->
                // Handle positive button click (Add button)
                val cause = causeEditText.text.toString()
                var hour = hourEditText.text.toString().toIntOrNull() ?: 0
                val minute = minuteEditText.text.toString().toIntOrNull() ?: 0
                val amPm = amPmEditText.text.toString()

                // TODO: Validate input if needed

                // Convert hour and minute to 24-hour format
                val calendar = Calendar.getInstance().apply {
                    if (amPm.equals("PM", ignoreCase = true) && hour < 12) {
                        hour += 12
                    } else if (amPm.equals("AM", ignoreCase = true) && hour == 12) {
                        hour = 0
                    }
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                }

                // Add reminder to database
                addReminderToDatabase(cause, calendar.timeInMillis)
            }
            .setNegativeButton("Cancel", null)  // Set cancel button

        // Show the dialog
        builder.show()
    }

    private fun showAmPmDialog(options: Array<String>, amPmEditText: EditText) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select AM/PM")
            .setItems(options) { _, which ->
                amPmEditText.setText(options[which])
            }
            .show()
    }


    private fun observeReminders() {
        lifecycleScope.launch {
            reminderDatabase.dao.getReminders().collect { remindersList ->
                reminders.clear()
                reminders.addAll(remindersList)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun addReminderToDatabase(cause: String, time: Long) {
        val reminder = Reminder(cause, time)

        lifecycleScope.launch {
            // Insert into Room Database
            reminderDatabase.dao.upsertReminder(reminder)

            // No need to manually update RecyclerView here because it's handled by observeReminders()
            // Schedule notification
            scheduleNotification(reminder)
        }
    }

    private fun scheduleNotification(reminder: Reminder) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("cause", reminder.cause)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, reminder.id, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.time, pendingIntent)
    }


}



