package com.sks.todoappwithfirebase

import com.google.firebase.Timestamp

class Note {

    var title: String = ""
        get() = field
        set(value) {
            field = value
        }

    var content: String = ""
        get() = field
        set(value) {
            field = value
        }

    var timestamp: Timestamp = Timestamp.now()
        get() = field
        set(value) {
            field = value
        }

}