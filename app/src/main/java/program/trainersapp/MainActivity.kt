package program.trainersapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import program.trainersapp.model.Dto

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("dupsko", "heh")
        var dto = Dto()
        var t = dto.get("users")
        Log.d("dupsko", "heh1")
        Log.d("dupsko", t)
        this.register.setOnClickListener {
            val reg = Intent(this, Register::class.java)
            startActivity(reg)
        }
    }
}
