package com.sks.todoappwithfirebase

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.sks.todoappwithfirebase.databinding.ActivityNoteDetailsBinding

class NoteDetailsActivity : AppCompatActivity() {

    private lateinit var noteDetailsBinding: ActivityNoteDetailsBinding

    private var title: String? = ""
    private var content: String? = ""
    private var docId: String? = ""
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteDetailsBinding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        val view = noteDetailsBinding.root
        setContentView(view)

        //for update notes scenario
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        docId = intent.getStringExtra("docId")

        if (docId != null && docId!!.isNotEmpty()) {
            isEditMode = true
        }

        noteDetailsBinding.notesTitleText.setText(title)
        noteDetailsBinding.notesContentText.setText(content)
        if (isEditMode) {
            supportActionBar?.title = "Edit your note"
        } else {
            supportActionBar?.title = "Add your note"
        }

        /*noteDetailsBinding.saveNoteBtn.setOnClickListener {
            saveNote()
        }*/

        noteDetailsBinding.deleteNoteTextViewBtn.setOnClickListener {
            deleteNoteFromFirebase()
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
        val documentReference: DocumentReference

        if (isEditMode) {
            documentReference = Utility.getCollectionReferenceForNotes().document(docId!!)
        } else {
            documentReference = Utility.getCollectionReferenceForNotes().document()
        }

        documentReference.set(note).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Utility.showToast(this@NoteDetailsActivity, "Note added successfully")
                finish()
            } else {
                Utility.showToast(this@NoteDetailsActivity, "Failed while adding note")
            }
        }
    }

    private fun deleteNoteFromFirebase() {

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
    }
}
