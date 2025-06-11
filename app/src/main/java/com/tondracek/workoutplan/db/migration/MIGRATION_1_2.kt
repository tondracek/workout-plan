package com.tondracek.workoutplan.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE training_days ADD COLUMN `index` INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE training_exercises ADD COLUMN `index` INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE training_sets ADD COLUMN `index` INTEGER NOT NULL DEFAULT 0")
    }
}