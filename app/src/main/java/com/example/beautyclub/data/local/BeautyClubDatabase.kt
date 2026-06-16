package com.example.beautyclub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.beautyclub.data.local.dao.MemberDao
import com.example.beautyclub.data.local.dao.TransactionDao
import com.example.beautyclub.data.local.entity.MemberEntity
import com.example.beautyclub.data.local.entity.TransactionEntity

@Database(
    entities = [MemberEntity::class, TransactionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BeautyClubDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao
    abstract fun transactionDao(): TransactionDao

    companion object {

        @Volatile
        private var INSTANCE: BeautyClubDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE transactions ADD COLUMN type TEXT NOT NULL DEFAULT 'Purchases'"
                )
            }
        }

        fun getDatabase(context: Context): BeautyClubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeautyClubDatabase::class.java,
                    "beauty_club_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}