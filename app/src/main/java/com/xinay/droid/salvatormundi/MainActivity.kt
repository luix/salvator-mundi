package com.xinay.droid.salvatormundi

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.main_text_view)
        textView.setText("Salvator Mundi")
        textView.setTextColor(Color.YELLOW)

    }

    override fun onResume() {
        super.onResume()
        AnimationUtils.loadAnimation(this, R.anim.jump).also { jump ->
            textView.startAnimation(jump)
        }
    }
}
