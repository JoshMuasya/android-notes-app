package com.example.notesapp.database

import androidx.lifecycle.LiveData
import com.example.notesapp.Models.Notes

class NotesRepo(private val NoteDb: NoteDb) {

    val allNotes : LiveData<List<Notes>> = NoteDb.getNotes()

    suspend fun insert(notes: Notes) {

        NoteDb.insert(notes)

    }

    suspend fun delete(notes: Notes) {

        NoteDb.delete(notes)

    }

    suspend fun update(notes: Notes) {
        NoteDb.update(notes.id, notes.title, notes.note)
    }

}