package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.Models.Notes
import com.example.notesapp.Models.noteViewModel
import com.example.notesapp.adapter.adapter
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), adapter.notesClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NotesDatabase
    lateinit var adapter: adapter
    lateinit var selectedNote : Notes
    lateinit var viewModel : noteViewModel

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val note = result.data?.getSerializableExtra("note") as? Note
            if (note != null) {

                viewModel.updateNote(note)

            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Initialize UI
        initUI()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[noteViewModel::class.java]

        viewModel.allNotes.observe(this) {list ->

            list?.let {

                adapter.updateList(list)

            }

        }

        database = NotesDatabase.getDb(this)

    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = adapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val note = result.data?.getSerializableExtra("note") as? Note
                if (note != null) {
                    viewModel.insertNote(note)
                }

            }

        }

        binding.floatingActionButton3.setOnClickListener {

            val intent = Intent(this, AddNote::class.java)
            getContent.launch(intent)

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText != null) {
                    adapter.filterList(newText)
                }

                return true

            }

        })

    }

    override fun itemClicked(notes: Notes) {

        val intent = Intent(this@MainActivity, AddNote::class.java)
        intent.putExtra("current_note", notes)
        updateNote.launch(intent)

    }

    override fun longItemClick(notes: Notes, cardView: CardView) {
        selectedNote = notes
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {

        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.deleteNote) {

            viewModel.deleteNote(selectedNote)
            return true

        }
        return false
    }
}
