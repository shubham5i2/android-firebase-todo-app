package com.sks.todoappwithfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.sks.todoappwithfirebase.databinding.ActivityNoteDetailsBinding

class NoteDetailsActivity : AppCompatActivity() {

    private lateinit var noteDetailsBinding: ActivityNoteDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteDetailsBinding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        val view = noteDetailsBinding.root
        setContentView(view)

        noteDetailsBinding.saveNoteBtn.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val noteTitle = noteDetailsBinding.notesTitleText.text.toString()
        val noteContent = noteDetailsBinding.notesContentText.text.toString()

        if (noteTitle.isEmpty()) {
            noteDetailsBinding.notesTitleText.error = "Title is required"
            return
        }
        if (noteContent.isEmpty()) {
            noteDetailsBinding.notesContentText.error = "Content is required"
            return
        }

        val note: Note = Note()
        note.title = noteTitle
        note.content = noteContent
        note.timestamp = Timestamp.now()

        saveNoteInFirebase(note)
    }

    private fun saveNoteInFirebase(note: Note) {

    }
}
