package com.example.tipsdrawtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

class ImageViewWithWidgets(val mContext: Context, val mAttrs: AttributeSet): View(mContext, mAttrs) {

    private var imageSrc: Drawable?
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var widgets = ArrayList<ViewWidget>()

    init {
        mContext.theme.obtainStyledAttributes(mAttrs, R.styleable.ImageViewWithText, 0, 0).apply {
            try {
                if (hasValue(R.styleable.ImageViewWithText_src)) {
                    imageSrc = getDrawable(R.styleable.ImageViewWithText_src)
                } else {
                    imageSrc = null
                }
            } finally {
                recycle()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        for (widget in widgets) {
            widget.onViewAttached()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        for (widget in widgets) {
            widget.onViewDetached()
        }
    }

    fun getSrc(): Drawable? {
        return imageSrc
    }

    fun setSrc(drawable: Drawable) {
        imageSrc = drawable
        reRender()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
        // Widgets
        for (widget in widgets) {
            widget.onSizeChanged(w, h, oldw, oldh)
        }
    }

    override fun onDraw(canvas: Canvas) {
        // Background
        imageSrc?.setBounds(0, 0, viewWidth, viewHeight)
        imageSrc?.draw(canvas)

        // Widgets
        for (widget in widgets) {
            widget.onDraw(canvas)
        }
    }

    fun addWidget(widget: ViewWidget) {
        widgets.add(widget)
        reRender()
    }

    fun reRender() {
        invalidate()
        requestLayout()
    }
}