package com.example.notesapp.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.example.notesapp.Models.Notes
import com.example.notesapp.R
import kotlin.random.Random

class Adapter(private val context : Context) : RecyclerView.Adapter<Adapter.NotesHolder> {

    private val noteList : ArrayList<Notes>()
    private val fullNotes : ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        return NotesHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun noteColor() : Int{

        val list = ArrayList<Int>()
        list.add(R.color.color1)
        list.add(R.color.color2)
        list.add(R.color.color3)
        list.add(R.color.color4)
        list.add(R.color.color5)
        list.add(R.color.color6)
        list.add(R.color.color7)

        val num = System.currentTimeMillis().toInt()
        val anyIndex = Random(num).nextInt(list.size)

        return list[anyIndex]

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val noteCurrent = noteList[position]
        holder.title.text = noteCurrent.title
        holder.title.isSelected = true

        holder.note.text = noteCurrent.note
        holder.date.text = noteCurrent.date
        holder.date.isSelected = true

        holder.notesLayout.setCardBackgroundColor(holder.itemView.resources.getColor(noteColor(), null))
    }

    inner class NotesHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val notesLayout = itemView.findViewById<CardView>(R.id.itemsList)
        val title: TextView = itemView.findViewById(R.id.displayTitle)
        val note: TextView = itemView.findViewById(R.id.displayNote)
        val date: TextView = itemView.findViewById(R.id.displayDate)

    }

}