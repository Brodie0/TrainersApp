package program.trainersapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.register.setOnClickListener {
            val reg = Intent(this, Register::class.java)
            startActivity(reg)
        }
    }
}