package com.nickthelegend.myapplication3

import com.nickthelegend.myapplication3.utils.FireBaseUtils
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Transition
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nickthelegend.myapplication3.ui.theme.MyApplication3Theme

class MainActivity : ComponentActivity() {


    fun <A, B> Pair<A, B>.toAndroidPair(): android.util.Pair<A, B> {
        return android.util.Pair(this.first, this.second)
    }
    private lateinit var top_animation: Animation
    private lateinit var bottom_animation: Animation


    private lateinit var Logo : ImageView
    private lateinit var textview1 : TextView
    private lateinit var textview2 : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isLoggedIn : Boolean = FireBaseUtils.isLoggedIn()




        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        top_animation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        bottom_animation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        textview1 = findViewById(R.id.textView)
        textview2= findViewById(R.id.textView2)
        Logo = findViewById(R.id.imageView)


        Logo.startAnimation(top_animation)
        textview2.startAnimation(bottom_animation)
        textview1.startAnimation(bottom_animation)


        Handler().postDelayed(


            {
                val sharedElements = listOf<Pair<View, String>>(
                    Pair(Logo, "logo_image"),
                    Pair(textview1, "logo_text")
                )
                val androidPairs = sharedElements.map { it.toAndroidPair() }
                val options = ActivityOptions.makeSceneTransitionAnimation(this, *androidPairs.toTypedArray())


                if (isLoggedIn){

                    val intent = Intent(this@MainActivity,NavigationActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                }else{
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent,options.toBundle())
                    Handler().postDelayed({
                        // Finish the MainActivity
                        finish()
                    }, 1000)


                }

            },2000
        )





    }
}

