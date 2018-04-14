package program.trainersapp.model.entities

class User (
    val id : Int,
    val login : String,
    val password : String,
    val name : String?,
    val lastname : String?,
    val email : String?
)