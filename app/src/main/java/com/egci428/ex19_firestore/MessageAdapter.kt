package com.egci428.ex19_firestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MessageAdapter(val mContext : Context, val layoutResId : Int, val messageList : List<Message>) : ArrayAdapter<Message>(mContext, layoutResId, messageList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(layoutResId, null)
        val msgTextView = view.findViewById<TextView>(R.id.msgView)
        val ratingTextView = view.findViewById<TextView>(R.id.rateView)
        val idTextView = view.findViewById<TextView>(R.id.idView)
        msgTextView.text = "Messgae : " + messageList[position].message
        ratingTextView.text = "Rating Value : " + messageList[position].rating.toString()
        idTextView.text = "ID : " + messageList[position].id

        return view
    }
}