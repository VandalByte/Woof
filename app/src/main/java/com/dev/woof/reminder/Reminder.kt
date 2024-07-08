package com.dev.woof.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    val cause: String,
    val time: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
