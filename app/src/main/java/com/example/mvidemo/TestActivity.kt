package com.example.mvidemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvidemo.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        test_button_two.setOnClickListener {
            Intent(this, MainActivity::class.java)
                .apply {
                    startActivity(this)
                }
            this.finish()
        }
    }
}
