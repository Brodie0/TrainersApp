package program.trainersapp.model

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL

class Dto {

    fun get(table : String) : String {
        val path = Config.entryPoint + table
        val obj = URL(path)
        val response = StringBuffer()
        with(obj.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
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

    fun getById(table : String, id : Int) : String{
        val path = Config.entryPoint + table + "/" + id.toString()
        val obj = URL(path)
        val response = StringBuffer()
        with(obj.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            BufferedReader(InputStreamReader(inputStream) as Reader?).use {
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