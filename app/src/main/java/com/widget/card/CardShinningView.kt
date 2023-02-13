package com.widget.card

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class CardShinningView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private val mPaint = Paint()
    private val mPath = Path()
    private var mValueAnimator: ValueAnimator? = null
    private var mRadius = 0
    private val mClipPath = Path()
    private val mRect = RectF()
    private var mColors = intArrayOf(0x15FFFFFF, 0x66FFFFFF, 0x00000000, 0x00000000)
    private var mPositions = floatArrayOf(0f, 0.5f, 0.51f, 1f)

    private var mDuration = 1000L
    private var mRepeatCount = 0
    var mSlope = 0.45f
    var mOffset = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CardShinningView)
        val c = a.getString(R.styleable.CardShinningView_colors)
        val p = a.getString(R.styleable.CardShinningView_positions)
        if (c != null && p != null) {
            val cc = c.split(",".toRegex()).toTypedArray()
            val pp = p.split(",".toRegex()).toTypedArray()
            val size = cc.size
            if (size == pp.size) {
                mColors = IntArray(size)
                mPositions = FloatArray(size)
                for (i in 0 until size) {
                    mColors[i] = Color.parseColor(cc[i])
                    mPositions[i] = pp[i].toFloat()
                }
            }
        }
        mRepeatCount = a.getInt(R.styleable.CardShinningView_repeat, 0)
        mDuration = a.getInt(R.styleable.CardShinningView_duration, 1000).toLong()
        mRadius = a.getDimensionPixelSize(R.styleable.CardShinningView_radius, 0)
        mSlope = a.getFloat(R.styleable.CardShinningView_slope, 0.45f)
        mOffset = a.getDimensionPixelSize(R.styleable.CardShinningView_offset, 0)
        a.recycle()
    }

    fun playAnim() {
        initData()
        if (mValueAnimator != null && mValueAnimator?.isStarted == true) {
            mValueAnimator!!.cancel()
        }
        mValueAnimator?.start()
    }

    private fun initData() {
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(measuredWidth.toFloat(), 0f)
        mPath.lineTo(measuredWidth.toFloat(), measuredHeight.toFloat())
        mPath.lineTo(0f, measuredHeight.toFloat())
        mPath.close()

        if (mOffset <= 0) {
            mOffset = measuredWidth
        }

        if(mValueAnimator == null){
            mValueAnimator =
                ValueAnimator.ofFloat(-mOffset.toFloat(), measuredWidth.toFloat() + mOffset)
            mValueAnimator?.setRepeatCount(mRepeatCount)
            mValueAnimator?.setInterpolator(LinearInterpolator())
            mValueAnimator?.setDuration(mDuration)
            mValueAnimator?.addUpdateListener({ animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                val mLinearGradient = LinearGradient(
                    value,
                    mSlope * value,
                    value + mOffset,
                    mSlope * (value + mOffset),
                    mColors,
                    mPositions,
                    Shader.TileMode.CLAMP
                )
                mPaint.shader = mLinearGradient
                invalidate()
            })
        }
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        mClipPath.reset()
//        mRect[0f, 0f, measuredWidth.toFloat()] = measuredHeight.toFloat()
//        mClipPath.addRoundRect(mRect, mRadius.toFloat(), mRadius.toFloat(), Path.Direction.CW)
//        canvas.clipPath(mClipPath)
        canvas.drawPath(mPath, mPaint)
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mValueAnimator != null) {
            mValueAnimator!!.cancel()
        }
    }
}