package com.example.beautyclub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.beautyclub.data.local.dao.MemberDao
import com.example.beautyclub.data.local.dao.TransactionDao
import com.example.beautyclub.data.local.entity.MemberEntity
import com.example.beautyclub.data.local.entity.TransactionEntity

@Database(
    entities = [
        MemberEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BeautyClubDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    abstract fun transactionDao(): TransactionDao

    companion object {

        @Volatile
        private var INSTANCE: BeautyClubDatabase? = null

        fun getDatabase(
            context: Context
        ): BeautyClubDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        BeautyClubDatabase::class.java,
                        "beauty_club_database"
                    ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}