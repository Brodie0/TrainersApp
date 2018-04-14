package program.trainersapp.model

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.os.AsyncTask
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonDoc
import java.io.OutputStreamWriter


class Dto {

    fun get(table : String) : String {
        val path = Config.entryPoint + table
        val json = MyDownloadTask().execute(path).get()
        return json.toString()
    }

    fun getById(table : String, id : Int) : String{
        val path = Config.entryPoint + table + "/" + id.toString()
        val json = MyDownloadTask().execute(path).get()
        return json.toString()
    }

	fun post(table : String, body : String) : String {
        val path = Config.entryPoint + table
        val json = MyDownloadTask().execute(path, "POST", body).get()
        return json.toString()
	}
}

class ToObject{

    fun activeUser(json : String) : ActiveUser {
        val getter = Dto()
        val result = Klaxon()
                .parse<ActiveUser>(json)

        return result!!
    }
}

class ActiveUser(
        val id: Int,
        val user: Int,
        val longt: Float,
        val latt: Float
)


internal class MyDownloadTask : AsyncTask<String, Void, String>() {


    override fun onPreExecute() {
        //display progress dialog.
    }

    override fun doInBackground(vararg params: String): String {
        val response = StringBuffer()
        val obj = URL(params[0])
        val method = params[1]
        with(obj.openConnection() as HttpURLConnection) {
            requestMethod = method
            if(method == "POST"){
                setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                doInput = true
                instanceFollowRedirects = false
                connect()
                val writer = OutputStreamWriter(outputStream, "UTF-8")
                writer.write(params[2])
                writer.close()
            }
            BufferedReader(InputStreamReader(inputStream)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
            }
        }
        return response.toString()
    }


    protected override fun onPostExecute(result: String?) {
        // dismiss progress dialog and update ui
    }
}