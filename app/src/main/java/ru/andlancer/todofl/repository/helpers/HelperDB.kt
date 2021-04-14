package ru.andlancer.todofl.repository.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.andlancer.spacefit.data.db.Note


import ru.andlancer.todofl.repository.dao.*

//инициализация базы
@Database(entities = [ Note::class], version = 1)
abstract class HelperDB: RoomDatabase() {

    abstract fun noteDao(): NoteDao


    companion object {
        @Volatile
        private var databseInstance: HelperDB? = null

        fun getDatabasenIstance(mContext: Context): HelperDB =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, HelperDB::class.java, "Atodo")
                .fallbackToDestructiveMigration()
                .build()

    }
}