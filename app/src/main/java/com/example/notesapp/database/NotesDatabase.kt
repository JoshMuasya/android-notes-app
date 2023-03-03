package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.Models.Notes
import com.example.notesapp.utilities.DB_NAME

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao() : NoteDb

    companion object{

        @Volatile
        private var INST : NotesDatabase? = null

        fun getDb(context : Context) : NotesDatabase {

            return INST ?: synchronized(this){

                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    DB_NAME
                ).build()

                INST = inst

                inst

            }

        }

    }

}