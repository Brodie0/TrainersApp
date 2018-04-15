package program.trainersapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import program.trainersapp.model.Dto

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Reading from Shared Preferences
        var PREFRENCES_NAME = "Name You Like"
        val settings = getSharedPreferences(PREFRENCES_NAME, 0)
        val logged = settings.getString("Logged in", "")
        val id = settings.getString("id", "")

        //If already logged go to next window
        if (logged == "true")
        {
            val next = Intent(this, Info::class.java)
            startActivity(next)
        }

        this.register.setOnClickListener {
            val reg = Intent(this, Register::class.java)
            startActivity(reg)
        }
        this.LoginButton.setOnClickListener {
            val loginTV = findViewById<EditText>(R.id.LoginText)
            val passTV = findViewById<EditText>(R.id.PassText)

            val login = loginTV.text
            val pass = passTV.text
            Log.d("asd","{'login' : '" + login + "', 'password': '" + pass + "'}")
            val dto = Dto()
            val result = dto.post("checkUser", """{"login" : """" + login + """", "password": """" + pass + """"}""")
            val resultJSON = JSONObject(result)

            if(resultJSON.get("status").toString() == "true"){
                //Writing to SharedPreferences
                var id = resultJSON.get("id").toString()
                var settings = this.getSharedPreferences(PREFRENCES_NAME, 0)
                settings.edit().putString("Logged in", "true").putString("id", id).commit()
                //Go to next window
                val next = Intent(this, Info::class.java)
                startActivity(next)
            }
            else {
                loginTV.setText("")
                passTV.setText("")
                Toast.makeText(this@MainActivity, "Nieprawidłowy login i/lub hasło!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
