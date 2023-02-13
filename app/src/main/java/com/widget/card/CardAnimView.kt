package com.widget.card

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout

import kotlinx.android.synthetic.main.view_anim_card.view.*

class CardAnimView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {
    private var mShowAnimSet: AnimatorSet? = null
    private var mAnimSetSequence: AnimatorSet? = null

    init {
        inflate(context, R.layout.view_anim_card, this)
        initAnim()
    }

    private fun initAnim() {
        val ratationAnim = ObjectAnimator.ofFloat(mIvCardfront, "rotation", -45f, 0f)
        val scaleXAnim: ObjectAnimator =
            ObjectAnimator.ofFloat(mIvCardfront, "scaleX", 0.3f, 1f)
        val scaleYAnim: ObjectAnimator =
            ObjectAnimator.ofFloat(mIvCardfront, "scaleY", 0.3f, 1f)

        mShowAnimSet = AnimatorSet()
        mShowAnimSet?.interpolator = AccelerateInterpolator()
        mShowAnimSet?.playTogether(ratationAnim, scaleXAnim, scaleYAnim)
        mShowAnimSet?.setDuration(500)
        mShowAnimSet?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                // 初始状态重置
                mIvCardfront.visibility = View.VISIBLE
                mIvCardfront.rotationY = 0f
                mIvCardBack.visibility = View.GONE
            }

            override fun onAnimationEnd(p0: Animator) {
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {

            }
        })

        val rotationYAnimFront = ObjectAnimator.ofFloat(mIvCardfront, "rotationY", 0f, -90f)
        rotationYAnimFront.setDuration(250)
        rotationYAnimFront?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                mIvCardfront.visibility = View.GONE
                mIvCardBack.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {

            }
        })
        val rotationYAnimBack = ObjectAnimator.ofFloat(mIvCardBack, "rotationY", 90f, 0f)
        rotationYAnimBack.setDuration(250)
        rotationYAnimBack?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                mCardShinningView.playAnim()
            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {

            }
        })

        mAnimSetSequence = AnimatorSet()
        mAnimSetSequence?.playSequentially(mShowAnimSet, rotationYAnimFront, rotationYAnimBack)
    }

    fun playAnim() {
        mAnimSetSequence?.start()
    }

    fun cancelAnim() {
        mAnimSetSequence?.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimSetSequence?.cancel()
    }
}