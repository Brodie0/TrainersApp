package program.trainersapp

import android.app.Activity
import android.content.Intent
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
                val next = Intent(this, Info::class.java)
                startActivity(next)
            }
            else {
                loginTV.setText("")
                passTV.setText("")
                Toast.makeText(this@MainActivity, "Wrong login and/or password!", Toast.LENGTH_SHORT).show()
            }



        }
    }

    fun login(view: View){
        //val loginTV: EditText = findViewById(R.id.LoginText) as ed




    }
}
