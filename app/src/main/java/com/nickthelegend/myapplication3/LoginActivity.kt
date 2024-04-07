package com.nickthelegend.myapplication3

import android.app.ActivityOptions
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.nickthelegend.myapplication3.RegisterActivity
import android.widget.ProgressBar

class LoginActivity : ComponentActivity() {
    private val auth = FirebaseAuth.getInstance()

    companion object {
        private const val TAG = "YourActivityName"
    }
    fun <A, B> Pair<A, B>.toAndroidPair(): android.util.Pair<A, B> {
        return android.util.Pair(this.first, this.second)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

            setContentView(R.layout.activity_login)
        val signUpButton: Button = findViewById<Button>(R.id.signUp)
        val loginButton: Button = findViewById<Button>(R.id.login_signup_button)

        val username_textInput : TextInputLayout = findViewById(R.id.username)
        val password_textInput : TextInputLayout = findViewById(R.id.password)
        val progressbar_progressBar : ProgressBar = findViewById(R.id.progressBar)

        val loginAsStaff : Button = findViewById(R.id.staff_login)

        fun validatePassword(): Boolean {
            val password = password_textInput.editText?.text.toString().trim()

            // Matches a password with at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character

            if (password.isBlank()) {
                password_textInput.error = "Password cannot be blank"
                return false
            }

            // Clear any existing error message
            password_textInput.error = null
            return true
        }

        fun validateUsername(): Boolean {
            val username = username_textInput.editText?.text.toString().trim()


            if (username.isBlank()) {
                password_textInput.error = "Password cannot be blank"
                return false
            }

            // Clear any existing error message
            password_textInput.error = null
            return true
        }

        fun signInWithEmailAndPassword(email: String, password: String, callback: (Boolean) -> Unit) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        Log.d(TAG, "signInWithEmailAndPassword:success")
                        val user = auth.currentUser
                        callback(true) // Notify the caller that sign-in was successful
                    } else {
                        // Sign in failed
                        Log.w(TAG, "signInWithEmailAndPassword:failure", task.exception)
                        // Notify the caller that sign-in failed
                        callback(false)
                    }
                }
        }


// Inside your activity or wherever you want to define the function

        fun setInProgress(progressBar: ProgressBar, inProgress: Boolean) {
            if (inProgress) {
                // Show the progress bar
                progressBar.visibility = View.VISIBLE
            } else {
                // Hide the progress bar
                progressBar.visibility = View.GONE
            }
        }
        fun loginUser() {
            if (!validateUsername() || !validatePassword()) {
                Toast.makeText(this, "Please Check The Fields Properly", Toast.LENGTH_SHORT).show()
                return
            }
            val username = username_textInput.editText?.text.toString().trim()
            val password = password_textInput.editText?.text.toString().trim()
            setInProgress(progressbar_progressBar,true)
            // Call signInWithEmailAndPassword with a callback
            signInWithEmailAndPassword(username, password) { success ->
                if (success) {
                    // Handle successful login
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    setInProgress(progressbar_progressBar,false)
                    // Proceed to HomeActivity or wherever needed
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Handle login failure
                    Toast.makeText(this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show()
                    setInProgress(progressbar_progressBar,false)

                }
            }
        }




        loginButton.setOnClickListener {

                    loginUser()


            }


        signUpButton.setOnClickListener {
            // Your onClick action here
            // For example, you can start a new activity or perform some other action
            // Example: startActivity(Intent(this, NextActivity::class.java))
            Log.d("Button Click", "Pressed Button Register")
            val logo_view : ImageView = findViewById(R.id.logo_view)
            val logo_text : TextView = findViewById(R.id.textView3)
            val username : TextInputLayout = findViewById(R.id.username)
            val password : TextInputLayout = findViewById(R.id.password)
            val login_signup_button : Button = findViewById(R.id.login_signup_button)
            val sharedElements = listOf<Pair<View, String>>(
                Pair(logo_view, "logo_image"),
                Pair(logo_text, "logo_text"),
                Pair(username, "username_trans"),
                Pair(password, "password_trans"),
                Pair(login_signup_button, "login_signup_trans")
            )
            val androidPairs = sharedElements.map { it.toAndroidPair() }
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *androidPairs.toTypedArray())


            val intent: Intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent,options.toBundle())
//            finish()


        }

        loginAsStaff.setOnClickListener {
            val textview3 : TextView = findViewById(R.id.textView3)
            val Logo : ImageView = findViewById(R.id.logo_view)


            val sharedElements = listOf<Pair<View, String>>(
                Pair(Logo, "logo_image"),
                Pair(textview3, "logo_text")
            )
            val androidPairs = sharedElements.map { it.toAndroidPair() }
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *androidPairs.toTypedArray())
            val intent = Intent(this, StaffLoginActiviy::class.java )
            startActivity(intent,options.toBundle())
            Handler().postDelayed({
                // Finish the MainActivity
                finish()
            }, 1000)

        }


    }





}