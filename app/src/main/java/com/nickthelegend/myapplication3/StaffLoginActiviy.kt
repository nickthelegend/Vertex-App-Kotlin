package com.nickthelegend.myapplication3

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

class StaffLoginActiviy : ComponentActivity() {

    fun <A, B> Pair<A, B>.toAndroidPair(): android.util.Pair<A, B> {
        return android.util.Pair(this.first, this.second)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_staff)
        val loginAsStudent : Button = findViewById(R.id.student_login)
        loginAsStudent.setOnClickListener {






            val textview3 : TextView = findViewById(R.id.textView3)
            val signnIn : Button = findViewById(R.id.login_signup_button)


            val sharedElements = listOf<Pair<View, String>>(
                Pair(signnIn, "login_signup_trans"),
                Pair(textview3, "logo_text")
            )
            val androidPairs = sharedElements.map { it.toAndroidPair() }
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *androidPairs.toTypedArray())
            val intent = Intent(this, LoginActivity::class.java )
            startActivity(intent,options.toBundle())
            Handler().postDelayed({
                // Finish the MainActivity
                finish()
            }, 1000)



        }








    }

}