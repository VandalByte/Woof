package com.dev.woof.reminder

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Reminder::class],
    version = 1
)

abstract class ReminderDatabase: RoomDatabase() {
    abstract val dao: ReminderDao
}