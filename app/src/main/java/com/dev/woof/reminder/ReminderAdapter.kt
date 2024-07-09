package com.dev.woof.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.woof.R

class ReminderAdapter(
    private val reminders: List<Reminder>,
    private val onDeleteClick: (Reminder) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val causeTextView: TextView = itemView.findViewById(R.id.causeTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.causeTextView.text = reminder.cause
        holder.timeTextView.text = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date(reminder.time))

        holder.deleteIcon.setOnClickListener {
            onDeleteClick(reminder)
        }
    }

    override fun getItemCount() = reminders.size
}
