package com.example.brodie.trainersapp.feature

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.register.setOnClickListener {
            val register = Intent(this, Register::class.java)
            startActivity(register)
        }
    }
}
