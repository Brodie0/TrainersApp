package program.trainersapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import program.trainersapp.model.Dto

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
