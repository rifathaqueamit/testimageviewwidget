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

        val screenCenterText = TextWidget(this)
        screenCenterText.setPreferredParentSize(PointF(400F, 400F))
        screenCenterText.setScaleByParentSize(true)
        screenCenterText.setFontSize(50F)
        screenCenterText.setText("Hello world!")
        screenCenterText.setTextPosition(PointF(400F, 490F))
        screenCenterText.setSize(PointF(100F, 50F))
        imageView.addWidget(screenCenterText)

        val topBatteryText = TextWidget(this)
        topBatteryText.setPreferredParentSize(PointF(400F, 400F))
        topBatteryText.setScaleByParentSize(true)
        topBatteryText.setFontSize(30F)
        topBatteryText.setText("82%")
        topBatteryText.setTextPosition(PointF(505F, 290F))
        topBatteryText.setSize(PointF(100F, 50F))
        imageView.addWidget(topBatteryText)
    }
}