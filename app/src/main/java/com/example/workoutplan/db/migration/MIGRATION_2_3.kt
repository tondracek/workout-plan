package com.example.workoutplan.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// migration to rename index to orderIndex

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE training_days RENAME COLUMN `index` TO `orderIndex`")
        db.execSQL("ALTER TABLE training_exercises RENAME COLUMN `index` TO `orderIndex`")
        db.execSQL("ALTER TABLE training_sets RENAME COLUMN `index` TO `orderIndex`")
    }
}