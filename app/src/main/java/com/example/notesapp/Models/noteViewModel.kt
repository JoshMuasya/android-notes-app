package com.example.notesapp.Models

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.database.NotesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class noteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NotesRepo

    val allNotes : LiveData<List<Notes>>

    init {
        val dao = NotesDatabase.getDb(application).notesDao()
        repository = NotesRepo(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {

        repository.delete(notes)

    }

    fun insertNote(notes: ContactsContract.CommonDataKinds.Note?) = viewModelScope.launch(Dispatchers.IO) {

        repository.insert(notes)

    }

    fun updateNote(notes: ContactsContract.CommonDataKinds.Note?) = viewModelScope.launch(Dispatchers.IO) {

        repository.update(notes)

    }

}