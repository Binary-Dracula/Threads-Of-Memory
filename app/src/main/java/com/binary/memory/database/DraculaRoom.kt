package com.binary.memory.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.binary.memory.database.dao.FlashDao
import com.binary.memory.database.dao.TaskDao
import com.binary.memory.model.FlashGroup
import com.binary.memory.model.Flashcard
import com.binary.memory.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *
 */
@Database(
    entities = [Task::class, Flashcard::class, FlashGroup::class],
    version = 1,
    exportSchema = false
)
abstract class DraculaRoom : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun flashcardDao(): FlashDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {

                }
            }
        }
    }

    companion object {
        private const val DATABASE_NAME = "dracula_room"

        @Volatile
        private var INSTANCE: DraculaRoom? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DraculaRoom {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DraculaRoom::class.java,
                    DATABASE_NAME
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}