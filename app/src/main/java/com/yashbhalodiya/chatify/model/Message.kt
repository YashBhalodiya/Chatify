package com.yashbhalodiya.chatify.model

class Message {
    var message: String? = null
    var senderId: String? = null

    constructor()
    constructor(message: String?, senderId: String?) {
        this.message = message
        this.senderId = senderId
    }
}

// features to be added in future
// 1. Timestamp of message
