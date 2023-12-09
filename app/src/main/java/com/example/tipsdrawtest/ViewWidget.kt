package com.example.tipsdrawtest

import android.content.Context
import android.graphics.Canvas

abstract class ViewWidget(val mContext: Context) {
    abstract fun onDraw(canvas: Canvas)
    abstract fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    abstract fun onViewAttached()
    abstract fun onViewDetached()
}