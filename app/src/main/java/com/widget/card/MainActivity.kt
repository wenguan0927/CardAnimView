package com.widget.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPlayAnim.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                mCardView.playAnim()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        mCardView.cancelAnim()
    }
}