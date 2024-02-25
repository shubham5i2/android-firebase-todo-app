package com.sks.todoappwithfirebase

import android.content.Context
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Utility {

    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun getCollectionReferenceForNotes(): CollectionReference {
            val currentUser = FirebaseAuth.getInstance().currentUser
            return FirebaseFirestore.getInstance().collection("notes").document(currentUser!!.uid)
                .collection("my_notes")
        }

        fun timestampToString(timestamp: Timestamp): String {
            return SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate())
        }

        fun getGreeting(): String {
            val calendar = Calendar.getInstance()
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

            return when {
                hourOfDay < 12 -> "Good morning!"
                hourOfDay < 18 -> "Good afternoon!"
                else -> "Good evening!"
            }
        }

        fun formatDate(date: Date): String {
            val dateFormat = SimpleDateFormat("EEEE, dd MMMM, yyyy", Locale.getDefault())
            return dateFormat.format(date).uppercase(Locale.getDefault())
        }
    }
}