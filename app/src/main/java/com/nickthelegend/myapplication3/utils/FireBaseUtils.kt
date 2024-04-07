package com.nickthelegend.myapplication3.utils

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nickthelegend.myapplication3.models.UserModel
import com.google.firebase.firestore.ktx.toObject
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.ktx.storage
import com.nickthelegend.myapplication3.LoginActivity
import java.io.ByteArrayOutputStream

class FireBaseUtils {

 companion object {
  // Function to get the current user's UID
  fun currentUserUid(): String? {
   return FirebaseAuth.getInstance().uid
  }

  // Function to get a DocumentReference for the current user's details
  fun currentUserDetails(): DocumentReference? {
   val uid = currentUserUid()
   if (uid != null) {
    return FirebaseFirestore.getInstance().collection("users").document(uid)
   }
   return null
  }
  fun isLoggedIn() : Boolean {
   val currentUser = Firebase.auth.currentUser
   if (currentUser!=null){
    
    return  true
    
    
   }else
   {
    
    return false
    
   }

  }


  fun signOut(): Unit? {

   FirebaseAuth.getInstance().signOut()

return null



  }
  fun fetchUserData(onSuccess: (UserModel) -> Unit, onFailure: (String) -> Unit) {
   val uid = FirebaseAuth.getInstance().currentUser?.uid

   if (uid != null) {
    val userDocumentRef = FirebaseFirestore.getInstance().collection("users").document(uid)

    userDocumentRef.get()
     .addOnSuccessListener { documentSnapshot ->
      if (documentSnapshot.exists()) {
       // Document exists, retrieve user data
       val userData = documentSnapshot.toObject<UserModel>()
       if (userData != null) {
        onSuccess(userData) // Invoke success callback with user data
       } else {
        onFailure("User data is null")
       }
      } else {
       // Document does not exist
       onFailure("Error: This Document does not exist")
      }
     }
     .addOnFailureListener { exception ->
      // Failed to retrieve document
      onFailure("Error: ${exception.message}")
     }
   }
  }

  fun registerUserFireUtils(
   context: Context,
   email: String,
   password: String,
   phoneNumber: String,
   username: String,
   fullname: String,
  ) {
   val auth = FirebaseAuth.getInstance()
   val firestore = FirebaseFirestore.getInstance()

   auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
     if (task.isSuccessful) {
      // Registration successful

      val user = UserModel(email, phoneNumber, username, fullname,password,"false","") ///is verified is default false for everyone
      firestore.collection("users").document(auth.currentUser!!.uid)
       .set(user)
       .addOnCompleteListener { documentTask ->
        if (documentTask.isSuccessful) {
         Toast.makeText(
          context,
          "Successfully Registered!",
          Toast.LENGTH_SHORT
         ).show()
         val intent = Intent(context, LoginActivity::class.java)
         context.startActivity(intent)
        } else {
         // Data write failed
         // Handle failure, for example, show a toast message with the error
         val errorMessage =
          documentTask.exception?.message ?: "Unknown error occurred"
         Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT)
          .show()
        }
       }
     } else {
      // Registration failed
      // Handle failure, for example, show a toast message with the error
      val errorMessage = task.exception?.message ?: "Unknown error occurred"
      Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
     }
    }
  }

  fun updateCurrentUserDetails(
   context: Context,
   phoneNumber: String,
   username: String,
   fullname: String
  ) {
   val auth = FirebaseAuth.getInstance()
   val firestore = FirebaseFirestore.getInstance()

   auth.currentUser?.let { currentUser ->
    val userRef = firestore.collection("users").document(currentUser.uid)
    val updatedData = mapOf(
     "phoneNumber" to phoneNumber,
     "username" to username,
     "fullname" to fullname
    )

    userRef.update(updatedData)
     .addOnSuccessListener {
      Toast.makeText(
       context,
       "Successfully updated user details!",
       Toast.LENGTH_SHORT
      ).show()
     }
     .addOnFailureListener { exception ->
      // Update failed
      // Handle failure, for example, show a toast message with the error
      Toast.makeText(
       context,
       "Error updating user details: ${exception.message}",
       Toast.LENGTH_SHORT
      ).show()
     }
   } ?: run {
    Toast.makeText(context, "No current user found!", Toast.LENGTH_SHORT).show()
   }
  }


  fun updateIDCardUri(
   context: Context,
   idCardUri: String
  ) {
   val auth = FirebaseAuth.getInstance()
   val firestore = FirebaseFirestore.getInstance()

   auth.currentUser?.let { currentUser ->
    val userRef = firestore.collection("users").document(currentUser.uid)
    val updatedData = mapOf(
     "idCardUri" to idCardUri // Add only idCardUri to updated data
    )

    userRef.update(updatedData)
     .addOnSuccessListener {
      Toast.makeText(
       context,
       "Successfully updated ID card image!",
       Toast.LENGTH_SHORT
      ).show()
     }
     .addOnFailureListener { exception ->
      // Update failed
      // Handle failure, for example, show a toast message with the error
      Toast.makeText(
       context,
       "Error updating ID card image: ${exception.message}",
       Toast.LENGTH_SHORT
      ).show()
     }
   } ?: run {
    Toast.makeText(context, "No current user found!", Toast.LENGTH_SHORT).show()
   }
  }

  fun uploadImageToFirebaseStorage(context: Context,bitmap: Bitmap) {
   val storageRef = com.google.firebase.ktx.Firebase.storage.reference
   val imagesRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")

   // Convert Bitmap to ByteArray
   val baos = ByteArrayOutputStream()
   bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
   val data = baos.toByteArray()

   // Upload ByteArray to Firebase Storage
   val uploadTask = imagesRef.putBytes(data)
   uploadTask.addOnSuccessListener { taskSnapshot ->
    // Image uploaded successfully
    Log.d("HomeActivity", "Image uploaded successfully: ${taskSnapshot.metadata?.path}")
    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
   }.addOnFailureListener { exception ->
    // Handle unsuccessful uploads
    Log.e("HomeActivity", "Failed to upload image", exception)
    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
   }
  }
 }
}


