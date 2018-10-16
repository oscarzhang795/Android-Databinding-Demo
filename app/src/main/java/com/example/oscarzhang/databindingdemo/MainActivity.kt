package com.example.oscarzhang.databindingdemo

import android.animation.ValueAnimator
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.OnRebindCallback
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.oscarzhang.databindingdemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.addOnRebindCallback(object : OnRebindCallback<ViewDataBinding>() {
            override fun onPreBind(binding: ViewDataBinding?): Boolean {
                TransitionManager.beginDelayedTransition(binding!!.root as ViewGroup)

                return super.onPreBind(binding)
            }
        })
        val user = User("Oscar", "Zhang")
        //binding.usermodel = user
        binding.setVariable(BR.usermodel, user)
        binding.executePendingBindings()


        btn_test.setOnClickListener{
            //user.firstname.set("safas")
            user.firstname = "James"
            user.lastname = "Brown"
        }
    }

}

@BindingAdapter("oscars_fancy_text_size", "oscars_fancy_text_size2")
fun fancyText(view: TextView, user: String, user2: String) {
//    if (user == "Oscar") {
//        view.textSize = 10f
//    }
//    else {
//        view.textSize = 80f
//    }
}

@BindingAdapter("my_animation")
fun myAnimation(view: View, user: String) {
//    if(user == "Zhang") {
//        (view as TextView).textSize = 30f
//    }
    popText(view as TextView)
}

private fun flashColor(textView: TextView) {
    if (textView.measuredHeight > 0) {
        val origColor = textView.currentTextColor
        textView.setTextColor(textView.resources.getColor(android.R.color.holo_green_dark))
        textView.postDelayed({
            val from = FloatArray(3)
            val to = FloatArray(3)
            Color.colorToHSV(textView.resources.getColor(android.R.color.holo_green_dark), from)
            Color.colorToHSV(origColor, to)

            val anim = ValueAnimator.ofFloat(0F, 1F)
            anim.duration = 1000

            val hsv = FloatArray(3)
            anim.addUpdateListener { animation ->
                // Transition along each axis of HSV (hue, saturation, value)
                hsv[0] = from[0] + (to[0] - from[0]) * animation.animatedFraction
                hsv[1] = from[1] + (to[1] - from[1]) * animation.animatedFraction
                hsv[2] = from[2] + (to[2] - from[2]) * animation.animatedFraction

                textView.setTextColor(Color.HSVToColor(hsv))
            }

            anim.start()
        }, 1000)
    }
}

private fun popText(textView: TextView) {
    if (textView.measuredHeight > 0) {
        textView.startAnimation(AnimationUtils.loadAnimation(textView.context, R.anim.oscar_animation))
    }
}