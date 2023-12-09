package com.example.tipsdrawtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log

class TextWidget(context: Context): ViewWidget(context) {
    private val TAG = "TextWidget"

    private val mTextSize: Float = 18F
    private var mTextSizeInPixels: Int = UIUtils.spToPx(mTextSize, mContext)

    private val mTextColor: Int = Color.BLACK

    private var mTextMarginStart: Float = 0F
    private var mTextMarginStartInPixels: Int = UIUtils.dpToPx(mTextMarginStart, mContext)

    private var mTextMarginEnd: Float = 100F
    private var mTextMarginEndInPixels: Int = UIUtils.dpToPx(mTextMarginEnd, mContext)

    private var mTextMarginTop: Float = 0F
    private var mTextMarginTopInPixels: Int = UIUtils.dpToPx(mTextMarginTop, mContext)

    private var mTextMarginBottom: Float = 100F
    private var mTextMarginBottomInPixels: Int = UIUtils.dpToPx(mTextMarginBottom, mContext)

    private var mTextWidth: Float = mTextMarginEnd - mTextMarginStart
    private var mTextWidthInPixels: Int = UIUtils.dpToPx(mTextWidth, mContext)

    private var mTextHeight: Float = mTextMarginBottom - mTextMarginTop
    private var mTextHeightInPixels: Int = UIUtils.dpToPx(mTextHeight, mContext)

    private var mText: String = ""

    private var textPaint: TextPaint
    private var staticLayout: StaticLayout

    private var parentWidthInPixels = 0
    private var parentHeightInPixels = 0

    init {
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = mTextColor
            textSize = mTextSizeInPixels.toFloat()
        }
        staticLayout = StaticLayout.Builder.obtain(mText, 0, mText.length, textPaint, mTextWidthInPixels).build()
    }

    override fun onViewAttached() {

    }

    override fun onViewDetached() {

    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate(mTextMarginStartInPixels.toFloat(), mTextMarginTopInPixels.toFloat())
        canvas.clipRect(0, 0, mTextWidthInPixels, mTextHeightInPixels)
        canvas.drawColor(Color.RED)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    fun getText(): String {
        return mText
    }

    fun setText(txt: String) {
        mText = txt
        reCreateStaticLayout()
    }

    fun getTextPosition(): PointF {
        return PointF(mTextMarginStart, mTextMarginTop)
    }

    fun setTextPosition(position: PointF) {
        mTextMarginStart = position.x
        mTextMarginStartInPixels = UIUtils.dpToPx(mTextMarginStart, mContext)

        mTextMarginTop = position.y
        mTextMarginTopInPixels = UIUtils.dpToPx(mTextMarginTop, mContext)

        mTextMarginEnd = mTextMarginStart + mTextWidth
        mTextMarginEndInPixels = mTextMarginStartInPixels + mTextWidthInPixels

        mTextMarginBottom = mTextMarginTop + mTextHeight
        mTextMarginBottomInPixels = mTextMarginTopInPixels + mTextHeightInPixels
    }

    fun getSize(): PointF {
        return PointF(mTextWidth, mTextHeight)
    }

    fun setSize(size: PointF) {
        mTextMarginEnd = mTextMarginStart + size.x
        mTextMarginEndInPixels = UIUtils.dpToPx(mTextMarginEnd, mContext)
        mTextWidth = size.x
        mTextWidthInPixels = UIUtils.dpToPx(mTextWidth, mContext)

        mTextMarginBottom = mTextMarginTop + size.y
        mTextMarginBottomInPixels = UIUtils.dpToPx(mTextMarginBottom, mContext)
        mTextHeight = size.y
        mTextHeightInPixels = UIUtils.dpToPx(mTextHeight, mContext)

        reCreateStaticLayout()
    }

    private fun reCreateStaticLayout() {
        staticLayout = StaticLayout.Builder.obtain(mText, 0, mText.length, textPaint, mTextWidthInPixels).build()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        parentWidthInPixels = w
        parentHeightInPixels = h
        Log.i(TAG, "onSizeChanged() $parentWidthInPixels:$parentHeightInPixels")
    }
}