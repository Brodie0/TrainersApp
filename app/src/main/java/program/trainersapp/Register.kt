package program.trainersapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import program.trainersapp.model.Dto
import program.trainersapp.model.ToObject

class Register : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    }

    fun tets(view: View) {
        var asd = Dto()
//        var chuj = asd.get("activeUsers")
        var jebac = ToObject()
        var gunwo = jebac.activeUser("""
            {
            "id":1,"user":1,
            "longt":54.3729,
            "latt":18.6329
            }
            """)


        Log.d("asd", gunwo.toString())

    }
}
