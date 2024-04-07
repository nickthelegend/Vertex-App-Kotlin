package com.nickthelegend.myapplication3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.nickthelegend.myapplication3.models.UserModel
import com.nickthelegend.myapplication3.utils.FireBaseUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class HomeActivity : ComponentActivity() {


    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var uploadImage: ImageView

    fun setInProgress(progressBar: ProgressBar, inProgress: Boolean) {
        if (inProgress) {
            // Show the progress bar
            progressBar.visibility = View.VISIBLE
        } else {
            // Hide the progress bar
            progressBar.visibility = View.GONE
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_profile)
        val fullNameTextView :TextView = findViewById(R.id.nameTextView)
        val usernameTextView :TextView = findViewById(R.id.usernameTextView)
//        val phoneNumberTextView :TextView = findViewById(R.id.phoneNumberTextView)
        val logoutButton : Button = findViewById(R.id.logoutButton)
        // In your activity or fragment
        val progressbar_progressBar : ProgressBar = findViewById(R.id.progressBar)
        setInProgress(progressbar_progressBar,true)

//        uploadImage  = findViewById(R.id.uploadImageView)
//        val uploadImageButton : Button = findViewById(R.id.uploadAnImageButton)

// Call the function to fetch user data
        FireBaseUtils.fetchUserData(
            onSuccess = { userData ->
                // Handle success, for example, store the user data in a variable
                val username = userData.username
                val email = userData.email
                val phoneNumber = userData.phoneNo
                val fullname = userData.fullname
                fullNameTextView.text = fullname
                usernameTextView.text = fullname

//                emailTextView.text = email
//                phoneNumberTextView.text = phoneNumber
                setInProgress(progressbar_progressBar,false)

                // Store the user data in variables or perform other operations
                // For example:
                // val user = User(username, email, phoneNumber, fullname)
            },
            onFailure = { errorMessage ->
                // Handle failure, for example, show a toast message with the error
                setInProgress(progressbar_progressBar,false)
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )


        logoutButton.setOnClickListener {
            FireBaseUtils.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

//        uploadImageButton.setOnClickListener {
//
//
//
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
//            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
////            finish()
//
//        }











        }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            // Check if the "data" Intent contains bitmap
//            val imageBitmap = data?.extras?.get("data") as? Bitmap
//            if (imageBitmap != null) {
//                // Bitmap obtained successfully, set it to the ImageView
//                uploadImage.setImageBitmap(imageBitmap)
//                Toast.makeText(this, "DOne", Toast.LENGTH_SHORT).show()
//
//            } else {
//                // Log a message if bitmap is null
//                Log.e("HomeActivity", "Failed to retrieve bitmap from data Intent")
//                Toast.makeText(this, "Failed to retrieve the image", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}