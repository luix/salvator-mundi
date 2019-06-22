package com.xinay.droid.salvatormundi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.main_text_view)
        textView.setText("Salvator Mundi")
        textView.setTextColor(Color.YELLOW)

        val imageView = findViewById<ImageView>(R.id.salvator_mundi_image_view)
        imageView.setImageDrawable(ContextCompat.getDrawable(
            applicationContext,
            R.drawable.))
    }
}
