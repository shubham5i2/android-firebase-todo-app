package com.sks.todoappwithfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.sks.todoappwithfirebase.databinding.ActivityNoteDetailsBinding

class NoteDetailsActivity : AppCompatActivity() {

    private lateinit var noteDetailsBinding: ActivityNoteDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteDetailsBinding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        val view = noteDetailsBinding.root
        setContentView(view)

        supportActionBar?.title = "Add note"

        noteDetailsBinding.addNoteTextViewBtn.setOnClickListener {
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

        val note = Note()
        note.title = noteTitle
        note.content = noteContent
        note.timestamp = Timestamp.now()

        saveNoteInFirebase(note)
    }

    private fun saveNoteInFirebase(note: Note) {
        val documentReference: DocumentReference =
            Utility.getCollectionReferenceForNotes().document()

        documentReference.set(note).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Utility.showToast(this@NoteDetailsActivity, "Note added successfully")
                finish()
            } else {
                Utility.showToast(this@NoteDetailsActivity, "Failed while adding note")
            }
        }
    }

    /*private fun deleteNoteFromFirebase() {

        val documentReference: DocumentReference =
            Utility.getCollectionReferenceForNotes().document(docId!!)

        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Utility.showToast(this@NoteDetailsActivity, "Note deleted successfully")
                finish()
            } else {
                Utility.showToast(this@NoteDetailsActivity, "Failed while deleting note")
            }
        }
    }*/
}
