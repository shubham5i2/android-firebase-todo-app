package com.sks.todoappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.sks.todoappwithfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.addNoteBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteDetailsActivity::class.java)
            startActivity(intent)
        }

        mainBinding.menuBtn.setOnClickListener {
            showMenu()
        }

        setupDataInRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        noteAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        noteAdapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.notifyDataSetChanged()
    }

    private fun setupDataInRecyclerView() {
        val query = Utility.getCollectionReferenceForNotes()
            .orderBy("timestamp", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<Note> =
            FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note::class.java).build()

        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        noteAdapter = NoteAdapter(options, this@MainActivity)
        mainBinding.recyclerView.adapter = noteAdapter
    }

    private fun showMenu() {
        val popupMenu = PopupMenu(this@MainActivity, mainBinding.menuBtn)
        popupMenu.menu.add("Logout")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            if (item.title == "Logout") {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                return@OnMenuItemClickListener true
            }
            return@OnMenuItemClickListener false
        })
    }
}