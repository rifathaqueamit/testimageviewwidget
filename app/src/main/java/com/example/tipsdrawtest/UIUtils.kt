package com.example.tipsdrawtest

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import java.security.AccessController.getContext


object UIUtils {
    fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    fun pxToDp(px: Int, context: Context): Float {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)
    }
}