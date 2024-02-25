package com.sks.todoappwithfirebase

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentReference

class NoteAdapter(options: FirestoreRecyclerOptions<Note>, var context: Context) :
    FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(options) {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.note_title_text_view)
        var content: TextView = itemView.findViewById(R.id.note_content_text_view)
        var timestamp: TextView = itemView.findViewById(R.id.note_timestamp_text_view)
        var editNote: ImageView = itemView.findViewById(R.id.edit_note_btn)
        var deleteNote: ImageView = itemView.findViewById(R.id.delete_note_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, note: Note) {
        holder.title.text = note.title
        holder.content.text = note.content
        holder.timestamp.text = Utility.timestampToString(note.timestamp)

        //edit an existing note
        holder.editNote.setOnClickListener {
            val intent = Intent(context, NoteUpdateActivity::class.java)
            intent.putExtra("title", note.title)
            intent.putExtra("content", note.content)
            val docId = snapshots.getSnapshot(position).id
            intent.putExtra("docId", docId)
            context.startActivity(intent)
        }

        //delete an existing note
        holder.deleteNote.setOnClickListener {
            val docId = snapshots.getSnapshot(position).id
            val documentReference: DocumentReference =
                Utility.getCollectionReferenceForNotes().document(docId)

            val dialogMessage = AlertDialog.Builder(context)
            dialogMessage.setTitle("Delete note")
            dialogMessage.setMessage(
                "Are you sure you want to delete the note?"
            )
            dialogMessage.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
            dialogMessage.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    documentReference.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Utility.showToast(context, "Note deleted successfully")
                        }
                    }
                })
            dialogMessage.setCancelable(false)
            dialogMessage.create().show()
        }
    }
}