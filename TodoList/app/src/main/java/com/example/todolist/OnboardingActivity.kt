package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var nextButton: MaterialButton
    private lateinit var finishButton: MaterialButton

    private val onboardingItems: List<OnboardingItem> = listOf(
        OnboardingItem(R.drawable.img_home_1, R.string.title_home_1, R.string.p_home_1),
        OnboardingItem(R.drawable.img_home_2, R.string.title_home_2, R.string.p_home_2),
        OnboardingItem(R.drawable.img_home_3, R.string.title_home_3, R.string.p_home_3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        nextButton = findViewById(R.id.nextButton)
        finishButton = findViewById(R.id.finishButton)

        val adapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onboardingItems.size - 1) {
                    finishButton.visibility = View.VISIBLE
                    nextButton.visibility = View.GONE
                } else {
                    finishButton.visibility = View.GONE
                    nextButton.visibility = View.VISIBLE
                }
            }
        })

        nextButton.setOnClickListener {
            val nextItem = viewPager.currentItem + 1
            if (nextItem < onboardingItems.size) {
                viewPager.currentItem = nextItem
            }
        }

        finishButton.setOnClickListener {
            val sharedPref = getSharedPreferences("onboarding", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean("onboarding_complete", true)
                apply()
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}