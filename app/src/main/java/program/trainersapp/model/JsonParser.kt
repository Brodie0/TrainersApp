package program.trainersapp.model

class JsonParser{
    fun parse(json : String) : List<String> {
        return json.replace("[", "").replace("]", "").replace("},{", "}&,{").split("&,")
    }
}