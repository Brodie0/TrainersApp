package program.trainersapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_info.*
import program.trainersapp.model.Dto

class Info : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val database = Dto()

        //On Click Actions
        this.stats.setOnClickListener {
            this.stats.setBackgroundColor(Color.CYAN)
            this.history.setBackgroundColor(Color.WHITE)
            this.friends.setBackgroundColor(Color.WHITE)
        }
        this.history.setOnClickListener {
            this.history.setBackgroundColor(Color.CYAN)
            this.stats.setBackgroundColor(Color.WHITE)
            this.friends.setBackgroundColor(Color.WHITE)
            val jsonHistory = database.getById("records", 1)
            jsonHistory.length
        }
        this.friends.setOnClickListener {
            this.friends.setBackgroundColor(Color.CYAN)
            this.history.setBackgroundColor(Color.WHITE)
            this.stats.setBackgroundColor(Color.WHITE)
        }
    }

    fun switchToMapActivity(view : View){
        val next = Intent(this, GPS::class.java)
        startActivity(next)
    }
}
