package program.trainersapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import program.trainersapp.model.Dto
import program.trainersapp.model.JsonToObjectConverter

class Register : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}
