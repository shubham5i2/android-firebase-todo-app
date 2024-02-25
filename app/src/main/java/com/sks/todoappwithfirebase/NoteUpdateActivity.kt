package com.sks.todoappwithfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.sks.todoappwithfirebase.databinding.ActivityNoteUpdateBinding

class NoteUpdateActivity : AppCompatActivity() {

    private lateinit var noteUpdateBinding: ActivityNoteUpdateBinding

    private var title: String? = ""
    private var content: String? = ""
    private var docId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteUpdateBinding = ActivityNoteUpdateBinding.inflate(layoutInflater)
        val view = noteUpdateBinding.root
        setContentView(view)

        supportActionBar?.title = "Update note"

        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        docId = intent.getStringExtra("docId")

        noteUpdateBinding.updateNotesTitleText.setText(title)
        noteUpdateBinding.updateNotesContentText.setText(content)

        noteUpdateBinding.updateNoteTextViewBtn.setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote() {
        val noteTitle = noteUpdateBinding.updateNotesTitleText.text.toString()
        val noteContent = noteUpdateBinding.updateNotesContentText.text.toString()

        if (noteTitle.isEmpty()) {
            noteUpdateBinding.updateNotesTitleText.error = "Title is required"
            return
        }
        if (noteContent.isEmpty()) {
            noteUpdateBinding.updateNotesContentText.error = "Content is required"
            return
        }

        val note = Note()
        note.title = noteTitle
        note.content = noteContent
        note.timestamp = Timestamp.now()

        updateNoteInFirebase(note)
    }

    private fun updateNoteInFirebase(note: Note) {
        val documentReference: DocumentReference =
            Utility.getCollectionReferenceForNotes().document(docId!!)

        documentReference.set(note).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Utility.showToast(this@NoteUpdateActivity, "Note updated successfully")
                finish()
            } else {
                Utility.showToast(this@NoteUpdateActivity, "Failed while updating note")
            }
        }
    }
}