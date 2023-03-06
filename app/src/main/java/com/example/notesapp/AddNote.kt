package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.Models.Notes
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var notes: Notes
    private lateinit var oldNotes: Notes
    var isUpdate = false

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {

            oldNotes = intent.getSerializableExtra("current_note") as Notes
            binding.editTitle.setText(oldNotes.title)
            binding.editNote.setText(oldNotes.note)
            isUpdate = true

        } catch (e : Exception) {
            e.printStackTrace()
        }

        binding.check.setOnClickListener{

            val title = binding.editTitle.text.toString()
            val note_desc = binding.editNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()){

                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate){

                    notes = Notes(
                        oldNotes.id, title, note_desc,formatter.format(Date())
                    )

                } else {

                    notes = Notes(
                        null, title, note_desc, formatter.format(Date())
                    )

                }

                val intent = Intent()
                intent.putExtra("note", notes)
                setResult(Activity.RESULT_OK,intent)
                finish()

            } else {
                Toast.makeText(this@AddNote, "Please enter some data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        binding.backArrow.setOnClickListener{
            onBackPressed()
        }

    }
}