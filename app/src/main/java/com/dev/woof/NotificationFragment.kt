package com.dev.woof

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class NotificationFragment: Fragment(R.layout.fragment_notification) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReminderAdapter
    private val reminders = mutableListOf<Reminder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ReminderAdapter(reminders)
        recyclerView.adapter = adapter

        val fabAddReminder: FloatingActionButton = view.findViewById(R.id.fabAddReminder)
        fabAddReminder.setOnClickListener {
            showAddReminderDialog()
        }

        return view
    }

    private fun showAddReminderDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_reminder, null)
        val causeEditText = dialogView.findViewById<EditText>(R.id.causeEditText)
        val timeEditText = dialogView.findViewById<EditText>(R.id.timeEditText)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Reminder")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val cause = causeEditText.text.toString()
                val time = timeEditText.text.toString()
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, time.split(":")[0].toInt())
                    set(Calendar.MINUTE, time.split(":")[1].toInt())
                    set(Calendar.SECOND, 0)
                }
                addReminder(cause, calendar.timeInMillis)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addReminder(cause: String, time: Long) {
        val reminder = Reminder(cause, time)
        reminders.add(reminder)
        adapter.notifyItemInserted(reminders.size - 1)

        scheduleNotification(reminder)
    }

    private fun scheduleNotification(reminder: Reminder) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("cause", reminder.cause)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, reminder.time.toInt(), intent, PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.time, pendingIntent)
    }
}
