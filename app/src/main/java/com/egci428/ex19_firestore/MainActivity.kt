package com.egci428.ex19_firestore

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    lateinit var submitbtn : Button
    lateinit var ratingBar: RatingBar
    lateinit var listView: ListView

    lateinit var msgList : MutableList<Message>
    lateinit var adapter: MessageAdapter

    lateinit var dataReference : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editText = findViewById(R.id.editText)
        submitbtn = findViewById(R.id.submitBtn)
        ratingBar = findViewById(R.id.ratingBar)
        listView = findViewById(R.id.listView)

        msgList = mutableListOf()
        dataReference = FirebaseFirestore.getInstance()

        submitbtn.setOnClickListener {
            submitData()
            readFirestoreData()
        }
        readFirestoreData()
    }

    private fun readFirestoreData() {
        var db = dataReference.collection("dataMessage")
        db.orderBy("timeStamp").get()
            // data kept in snapshot
            .addOnSuccessListener { snapshot ->
                if(snapshot != null){
                    msgList.clear()
                    val messages = snapshot.toObjects(Message::class.java)
                    for(message in messages){
                        msgList.add(message)
                    }

                    adapter = MessageAdapter(applicationContext, R.layout.messages, msgList)
                    listView.adapter = adapter
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Failed to read message from Firestore!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun submitData() {
        val msg = editText.text.toString()

        // check error
        if(msg.isEmpty()){
            editText.error = "Please submit a message."
            return
        }

        // setup the messageData object
        var db = dataReference.collection("dataMessage")
        val messageId = db.document().id
        val messageData = Message(messageId, msg, ratingBar.rating.toFloat(), System.currentTimeMillis().toString())

        // add document
        db.add(messageData)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Message is saved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Failed to save message!", Toast.LENGTH_SHORT).show()
            }

    }
}