package program.trainersapp.model

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

internal class DownloadTask : AsyncTask<String, Void, String>() {

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
}