package com.example.workoutplan.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// migration to add finishedCount column

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE training_days ADD COLUMN `finishedCount` INTEGER NOT NULL DEFAULT 0")
    }
}