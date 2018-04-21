package net.sytes.roneymaia.personalvr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.view.Window


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SingletonControlCanvas.imagensgif = assets!!.list("imagensloginScreen")
        SingletonControlCanvas.assets = assets

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)
        val handler = Handler()
        handler.postDelayed(Runnable { showMainActivity() }, 3000)
    }

    private fun showMainActivity()
    {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
