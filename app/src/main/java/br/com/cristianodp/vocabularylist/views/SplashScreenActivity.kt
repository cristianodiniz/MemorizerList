package br.com.cristianodp.vocabularylist.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.cristianodp.vocabularylist.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*
import kotlin.concurrent.timerTask

class SplashScreenActivity : AppCompatActivity() {
    private var SPLASH_TIME_OUT = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageViewGean1.rotation = 360f;
        imageViewGean2.rotation = 0f;
        val timer = Timer()
        timer.schedule(timerTask {
            SPLASH_TIME_OUT += 10

            imageViewGean1.rotation += 30f
            imageViewGean2.rotation -= 30f

            if (imageViewGean1.rotation > 360f) {
                imageViewGean1.rotation = 0f
            }

            if (imageViewGean2.rotation < 0f) {
                imageViewGean2.rotation = 360f
            }

            if (SPLASH_TIME_OUT >= 100){
                val intent = Intent(this@SplashScreenActivity, DashboardActivity::class.java)
                startActivity(intent)
                this.cancel()
                this@SplashScreenActivity.finish()
            }

        }, 1, 1000)
    }
}
