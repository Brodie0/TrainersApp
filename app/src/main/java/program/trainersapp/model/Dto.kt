package program.trainersapp.model


class Dto {
    fun get(table : String) : String {
        val path = Config.entryPoint + table
        val json = DownloadTask().execute(path, "GET").get()
        return json.toString()
    }

    fun getById(table : String, id : Int) : String{
        val path = Config.entryPoint + table + "/" + id.toString()
        val json = DownloadTask().execute(path, "GET").get()
        return json.toString()
    }

	fun post(table : String, body : String) : String {
        val path = Config.entryPoint + table
        val json = DownloadTask().execute(path, "POST", body)
        return json.toString()
	}
}


