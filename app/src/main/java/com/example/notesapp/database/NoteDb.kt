package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.Models.Notes

@Dao
interface NoteDb {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insert(note : Notes)

    @Delete
    suspend fun delete(note: Notes)

    @Query("Select * from notesTable order_by ASC")
    fun getNotes() : LiveData<List<Notes>>

    @Query("UPDATE notesTable Set title = :title, note = :note WHERE id =  :id")
    suspend fun update(id : Int?, title : String?, note : String?)

}