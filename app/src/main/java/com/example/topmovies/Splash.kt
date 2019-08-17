package com.example.topmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash : AppCompatActivity() {
    private var mDelayHanlder: Handler? = null
    private val SPLASH_DELAY: Long = 3000 // 3 seconds

    internal val mRunnable: Runnable = Runnable {
        if(!isFinishing){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //For initialize Handler
        mDelayHanlder = Handler()

        //Navigate with delay
        mDelayHanlder!!.postDelayed(mRunnable,SPLASH_DELAY)
    }

    override fun onDestroy() {
        if(mDelayHanlder != null){
            mDelayHanlder!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}
