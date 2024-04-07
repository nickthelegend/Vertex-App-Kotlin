package com.nickthelegend.myapplication3.models

import com.google.firebase.Timestamp

class UserModel {
    val email: String
    val phoneNo: String
    val username: String
    val fullname: String
    val password: String
    val isVerified: String
    val idCardUri : String
    val timeStamp: Timestamp = Timestamp.now()

    constructor() {
        email = ""
        phoneNo = ""
        username = ""
        fullname = ""
        password = ""
        isVerified = ""
        idCardUri = ""
    }

    constructor(email: String, phoneNo: String, username: String, fullname: String,password :String,isVerified : String,idCardUri :String) {
        this.email = email
        this.phoneNo = phoneNo
        this.username = username
        this.fullname = fullname
        this.password = password
        this.isVerified = isVerified
        this.idCardUri = idCardUri
    }
}
