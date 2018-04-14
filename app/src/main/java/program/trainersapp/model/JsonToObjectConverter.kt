package program.trainersapp.model

import com.beust.klaxon.Klaxon
import program.trainersapp.model.Entities.ActiveUser

class JsonToObjectConverter{
    fun activeUser(json : String) : ActiveUser {
        val result = Klaxon()
                .parse<ActiveUser>(json)
        return result!!
    }
}