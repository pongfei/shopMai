package com.egci428.ex19_firestore

class Message(val id : String, val message : String, val rating : Float, val timeStamp : String) {
    constructor() : this("","", 0.0F, "")
}