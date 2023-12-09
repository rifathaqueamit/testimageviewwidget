package com.example.tipsdrawtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log

class TextWidget(context: Context): ViewWidget(context) {
    private val TAG = "TextWidget"

    private var mTextSize: Float = 18F
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

    private var mTypeface = context.resources.getFont(R.font.opensansregular)

    private var textPaint: TextPaint
    private var staticLayout: StaticLayout

    private var parentWidthInPixels: Int = 0
    private var parentHeightInPixels: Int = 0
    private var parentWidth: Float = 0F
    private var parentHeight: Float = 0F

    private var preferredParentWidth: Float = 0F
    private var preferredParentHeight: Float = 0F
    private var scaleByParentSize = false

    init {
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = mTextColor
            textSize = getPreferredFontSize()
            typeface = mTypeface
        }
        staticLayout = StaticLayout.Builder.obtain(mText, 0, mText.length, textPaint, mTextWidthInPixels).build()
    }

    fun getFontSize(): Float {
        return mTextSize
    }

    fun setFontSize(size: Float) {
        mTextSize = size
        reCreateFont()
    }

    private fun reCreateFont() {
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = mTextColor
            textSize = getPreferredFontSize()
            typeface = mTypeface
        }
        reCreateStaticLayout()
    }

    override fun onViewAttached() {

    }

    override fun onViewDetached() {

    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate(getPreferredStart(), getPreferredTop())
        canvas.clipRect(0, 0, getPreferredWidthInPixels(), getPreferredHeightInPixels())
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
        parentWidth = UIUtils.pxToDp(parentWidthInPixels, mContext)
        parentHeightInPixels = h
        parentHeight = UIUtils.pxToDp(parentHeightInPixels, mContext)
        Log.i(TAG, "onSizeChanged() $parentWidth:$parentHeight")

        if (scaleByParentSize) {
            reCreateFont()
        }
    }

    fun getScaleByParentSize(): Boolean {
        return scaleByParentSize
    }

    fun setScaleByParentSize(on: Boolean) {
        scaleByParentSize = on
    }

    fun getPreferredParentSize(): PointF {
        return PointF(preferredParentWidth, preferredParentHeight)
    }

    fun setPreferredParentSize(size: PointF) {
        preferredParentWidth = size.x
        preferredParentHeight = size.y
    }

    private fun getPreferredWidth(): Float {
        if (!scaleByParentSize) {
            return mTextWidth
        } else {
            return mTextWidth / preferredParentWidth * parentWidth
        }
    }

    private fun getPreferredWidthInPixels(): Int {
        return UIUtils.dpToPx(getPreferredWidth(), mContext)
    }

    private fun getPreferredHeight(): Float {
        if (!scaleByParentSize) {
            return mTextHeight
        } else {
            return mTextHeight / preferredParentHeight * parentHeight
        }
    }

    private fun getPreferredHeightInPixels(): Int {
        return UIUtils.dpToPx(getPreferredHeight(), mContext)
    }

    private fun getPreferredStart(): Float {
        if (!scaleByParentSize) {
            return mTextMarginStart
        } else {
            return mTextMarginStart / preferredParentWidth * parentWidth
        }
    }

    private fun getPreferredStartInPixels(): Int {
        return UIUtils.dpToPx(getPreferredStart(), mContext)
    }

    private fun getPreferredTop(): Float {
        if (!scaleByParentSize) {
            return mTextMarginTop
        } else {
            return mTextMarginTop / preferredParentWidth * parentWidth
        }
    }

    private fun getPreferredTopInPixels(): Int {
        return UIUtils.dpToPx(getPreferredTop(), mContext)
    }

    private fun getPreferredFontSize(): Float {
        if (!scaleByParentSize) {
            return mTextSize
        } else {
            return mTextSize / preferredParentWidth * parentWidth
        }
    }

    private fun getPreferredFontSizeInPixels(): Int {
        return UIUtils.dpToPx(getPreferredFontSize(), mContext)
    }
}