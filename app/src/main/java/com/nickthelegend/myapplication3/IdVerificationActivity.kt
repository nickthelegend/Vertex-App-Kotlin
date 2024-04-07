package com.nickthelegend.myapplication3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nickthelegend.myapplication3.utils.FireBaseUtils
import java.io.ByteArrayOutputStream

class IdVerificationActivity: ComponentActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var uploadImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_verification)


        uploadImage  = findViewById(R.id.uploadImageView)
        val uploadImageButton : Button = findViewById(R.id.uploadAnImageButton)

        uploadImageButton.setOnClickListener {



            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//            finish()


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {


                uploadImage.setImageBitmap(imageBitmap)

                uploadImage.visibility = View.VISIBLE
                // Upload the image to Firebase Storage
                uploadImageToFirebaseStorage(imageBitmap)
            } else {
                Log.e("HomeActivity", "Failed to retrieve bitmap from data Intent")
                Toast.makeText(this, "Failed to retrieve the image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToFirebaseStorage(bitmap: Bitmap) {
        val storageRef = Firebase.storage.reference
        val imagesRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")

        // Convert Bitmap to ByteArray
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Upload ByteArray to Firebase Storage
        val uploadTask = imagesRef.putBytes(data)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully
            imagesRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                FireBaseUtils.updateIDCardUri(this,downloadUrl)
                Log.d("HomeActivity", "Image uploaded successfully. Download URL: $downloadUrl")
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()

                // Now you can use the downloadUrl for your further processing
                // For example, you can pass it to another function to display the image
                // displayImage(downloadUrl)
            }.addOnFailureListener { exception ->
                // Handle failure to get download URL
                Log.e("HomeActivity", "Failed to get download URL", exception)
                Toast.makeText(this, "Failed to get download URL", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful uploads
            Log.e("HomeActivity", "Failed to upload image", exception)
            Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }


}