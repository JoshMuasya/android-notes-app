package com.example.notesapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.example.notesapp.MainActivity
import com.example.notesapp.Models.Notes
import com.example.notesapp.R
import kotlin.random.Random

class adapter(private val context: Context, private val listener: MainActivity) : RecyclerView.Adapter<adapter.NotesHolder>() {

    private val noteList = ArrayList<Notes>()
    private val fullNotes = ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        return NotesHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Notes>){
        fullNotes.clear()
        fullNotes.addAll(newList)

        noteList.clear()
        noteList.addAll(fullNotes)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(search: String){

        noteList.clear()

        for(item in fullNotes){

            if(item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true){

                noteList.add(item)

            }

        }

        notifyDataSetChanged()

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

        holder.notesLayout.setOnClickListener{

            listener.itemClicked(noteList[holder.adapterPosition])

        }

        holder.notesLayout.setOnLongClickListener{
            listener.longItemClick(noteList[holder.adapterPosition], holder.notesLayout)
            true
        }

    }

    inner class NotesHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val notesLayout: CardView = itemView.findViewById(R.id.itemsList)
        val title: TextView = itemView.findViewById(R.id.displayTitle)
        val note: TextView = itemView.findViewById(R.id.displayNote)
        val date: TextView = itemView.findViewById(R.id.displayDate)

    }

    interface notesClickListener {
        fun itemClicked(notes: Notes)
        fun longItemClick(notes: Notes, cardView: CardView)
    }

}