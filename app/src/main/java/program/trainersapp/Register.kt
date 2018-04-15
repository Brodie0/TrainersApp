package program.trainersapp

import android.app.Activity
import android.app.job.JobServiceEngine
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import program.trainersapp.model.Dto
import java.util.regex.Pattern

class Register : Activity() {

    val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun register(view : View){
        var emailTV = findViewById<TextView>(R.id.EmailText)
        var loginTV = findViewById<TextView>(R.id.LoginText)
        var pwdTV = findViewById<TextView>(R.id.PassText)
        var reppwdTV = findViewById<TextView>(R.id.RepPassText)

        val email = emailTV.text
        val login = loginTV.text
        val pwd = pwdTV.text
        val reppwd = reppwdTV.text

        val ifRightEmail = validateEmail(email.toString())
        if(!ifRightEmail){
            Toast.makeText(this@Register, "Nieprawidłowy email!", Toast.LENGTH_SHORT).show();
            clear(emailTV, loginTV, pwdTV, reppwdTV)
            return;
        }

        if(pwd.toString() != reppwd.toString()){
            Toast.makeText(this@Register, "Hasła są różne!", Toast.LENGTH_SHORT).show();
            clear(emailTV, loginTV, pwdTV, reppwdTV)
            return;
        }

        val dto = Dto()
        val response = dto.post("users", """{"login" : """" + login + """", "password" : """" + pwd + """", "email": """" + email + """"}""")
        val responseText = JSONObject(response).get("message").toString()
        Toast.makeText(this@Register, responseText, Toast.LENGTH_SHORT).show()
        clear(emailTV, loginTV, pwdTV, reppwdTV)
    }

    fun clear(vararg params : TextView){
        for (i in params){
            i.text = ""
        }
    }

    fun validateEmail(email: String) : Boolean{
        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email)
        return matcher.find()
    }
}
