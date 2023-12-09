package com.example.tipsdrawtest

import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageViewWithWidgets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageViewWidgets)

        val textWidget = TextWidget(this)
        textWidget.setText("Hello world!")
        textWidget.setTextPosition(PointF(30F, 30F))
        textWidget.setSize(PointF(200F, 200F))
        imageView.addWidget(textWidget)
    }
}