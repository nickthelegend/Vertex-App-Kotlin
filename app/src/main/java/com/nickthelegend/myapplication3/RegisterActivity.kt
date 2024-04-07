package com.nickthelegend.myapplication3

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.nickthelegend.myapplication3.utils.FireBaseUtils

class RegisterActivity : ComponentActivity(){
    private val auth = FirebaseAuth.getInstance()

    fun <A, B> Pair<A, B>.toAndroidPair(): android.util.Pair<A, B> {
        return android.util.Pair(this.first, this.second)
    }
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
        setContentView(R.layout.activity_register)
        val fullname_textInput: TextInputLayout = findViewById(R.id.fullname)
        val phoneno_textInput: TextInputLayout = findViewById(R.id.phoneno)
        val email_textInput: TextInputLayout = findViewById(R.id.email)
        val username_textInput: TextInputLayout = findViewById(R.id.username)
        val password_textInput: TextInputLayout = findViewById(R.id.password)
        val login_signup_button : Button = findViewById(R.id.login_signup_button)
        val already_a_user : Button = findViewById(R.id.already_a_user)
        val progressbar_progressBar :ProgressBar = findViewById(R.id.progressBar)

//        val already_a_user : Button = findViewById(R.id.already_a_user)
//        already_a_user.setOnClickListener{
//                val intent = Intent(this,LoginActivity::class.java)
//
//                startActivity(
//                    intent
//                )
//                finish()
//
//        }
fun validateFullname() : Boolean {
    val fullname : String = fullname_textInput.editText?.text.toString().trim()

    // Check if the full name is empty or contains only whitespace
    if (fullname.isBlank()) {
        fullname_textInput.error = "Fullname cannot be blank"
        return false
    }
// Clear any existing error message
    fullname_textInput.error = null
    return true
}

        fun validateUsername() : Boolean {
            val username : String = username_textInput.editText?.text.toString().trim()

            // Check if the full name is empty or contains only whitespace
            if (username.isBlank()) {
                username_textInput.error = "Username Cannot Be Blank"
                return false
            }


            if (username.all { it.isWhitespace() }) {
                username_textInput.error = "Username cannot contain empty spaces"
                return false
            }

            else if(username.length > 15){
                username_textInput.error = "Username cannot be more than 15 characters"
                return false
            }
            // Clear any existing error message
            username_textInput.error = null
            return true
        }

        fun validatePhoneNumber(): Boolean {
            val phoneNumber = phoneno_textInput.editText?.text.toString().trim()

            val pattern = Regex("\\d{10}") // Matches a 10-digit phone number

            if (phoneNumber.isBlank()) {
                phoneno_textInput.error = "Phone number cannot be blank"
                return false
            } else if (!pattern.matches(phoneNumber)) {
                phoneno_textInput.error = "Invalid phone number"
                return false
            }

            // Clear any existing error message
            phoneno_textInput.error = null
            return true
        }

        fun validatePassword(): Boolean {
            val password = password_textInput.editText?.text.toString().trim()

            val pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}\$")
            // Matches a password with at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character

            if (password.isBlank()) {
                password_textInput.error = "Password cannot be blank"
                return false
            } else if (!pattern.matches(password)) {
                password_textInput.error = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character"
                return false
            }

            // Clear any existing error message
            password_textInput.error = null
            return true
        }

        fun validateEmailAddress(): Boolean {
            val emailAddress = email_textInput.editText?.text.toString().trim()

            val pattern = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
            // Matches a standard email address format

            if (emailAddress.isBlank()) {
                email_textInput.error = "Email address cannot be blank"
                return false
            } else if (!pattern.matches(emailAddress)) {
                email_textInput.error = "Invalid email address"
                return false
            }

            // Clear any existing error message
            email_textInput.error = null
            return true
        }





        fun registerUser(

        ) {

            setInProgress(progressbar_progressBar,true)

            // Validate user input
            if (!validateFullname() || !validatePhoneNumber() || !validateEmailAddress() || !validateUsername() || !validatePassword()) {
                return
            }

            // Get user input values
            val fullname: String = fullname_textInput.editText?.text.toString()
            val username: String = username_textInput.editText?.text.toString().trim()
            val password: String = password_textInput.editText?.text.toString().trim()
            val phoneNumber: String = phoneno_textInput.editText?.text.toString()
            val email: String = email_textInput.editText?.text.toString().trim()

            // Create user with email and password
            FireBaseUtils.registerUserFireUtils(
                this, // Context
                email, // Email
                password, // Password
                phoneNumber, // Phone number
                username, // Username
                fullname // Fullname
            )
            setInProgress(progressbar_progressBar,true)

        }

        login_signup_button.setOnClickListener{
            registerUser()

        }




        already_a_user.setOnClickListener {

            val username : TextInputLayout = findViewById(R.id.username)
            val password : TextInputLayout = findViewById(R.id.password)
            val sharedElements = listOf<Pair<View, String>>(
                Pair(username, "username_trans"),
                Pair(password, "password_trans"),
                Pair(login_signup_button, "login_signup_trans")
            )
            val androidPairs = sharedElements.map { it.toAndroidPair() }
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *androidPairs.toTypedArray())

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent,options.toBundle())
        }


    }






}
