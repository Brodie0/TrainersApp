package program.trainersapp

import android.app.ActionBar
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.RelativeLayout.*
import kotlinx.android.synthetic.main.activity_info.*
import org.json.JSONObject
import program.trainersapp.model.Dto
import program.trainersapp.model.JsonParser
import java.util.*
import java.util.concurrent.locks.Lock
import kotlin.collections.ArrayList

class Info : Activity() {

    private var listDataHeader: ArrayList<String>? = null
    private var listHash: HashMap<String, ArrayList<String>>? = null
    val database = Dto()
    var bundle: Any? = null

    var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        this.relLay.setBackgroundColor(Color.CYAN)
        bundle = intent.extras

        val dto = Dto()

        var PREFRENCES_NAME = "Name You Like"
        var settings = this.getSharedPreferences(PREFRENCES_NAME, 0)
        id = settings.getString("id", "")

        val result = JSONObject(dto.getById("users", id?.toInt()!!))

        this.nameText.text = result.get("name").toString()
        this.lastnameText.text = result.get("lastname").toString()

        this.history.setBackgroundColor(Color.WHITE)
        this.stats.setBackgroundColor(Color.WHITE)
        this.friends.setBackgroundColor(Color.WHITE)

        //On Click Actions
        this.stats.setOnClickListener {
            this.stats.setBackgroundColor(Color.CYAN)
            this.history.setBackgroundColor(Color.WHITE)
            this.friends.setBackgroundColor(Color.WHITE)
            //clearing ExpandableListAdapter
            this.lvExp.setAdapter(ExpandableListAdapter(this, ArrayList(), HashMap()))
            this.settings.visibility = View.INVISIBLE

            if(listHash!!.isEmpty() || listDataHeader!!.isEmpty())
                createHistory(listHash, listDataHeader)

            val statList = createStats(listHash)
            this.statList.adapter = ArrayAdapter<String>(this, R.layout.adapter , statList)

        }

        if(listDataHeader == null || listHash == null) {
            listDataHeader = ArrayList()
            listHash = HashMap()
        }

        this.history.setOnClickListener {
            this.history.setBackgroundColor(Color.CYAN)
            this.stats.setBackgroundColor(Color.WHITE)
            this.friends.setBackgroundColor(Color.WHITE)
            this.statList.adapter = ArrayAdapter<String>(this, R.layout.adapter , ArrayList())
            this.settings.visibility = View.INVISIBLE

            if(listHash!!.isEmpty() || listDataHeader!!.isEmpty())
                createHistory(listHash, listDataHeader)

            val listAdapter = ExpandableListAdapter(this, listDataHeader!!,listHash)
            this.lvExp.setAdapter(listAdapter)
        }
        this.friends.setOnClickListener {
            this.friends.setBackgroundColor(Color.CYAN)
            this.history.setBackgroundColor(Color.WHITE)
            this.stats.setBackgroundColor(Color.WHITE)
            //clearing Adapters
            this.statList.adapter = ArrayAdapter<String>(this, R.layout.adapter , ArrayList())
            this.lvExp.setAdapter(ExpandableListAdapter(this, ArrayList(), HashMap()))

            this.settings.visibility = View.VISIBLE
        }

        //Edit Settings
        this.edit.setOnClickListener {
            this.nameText.visibility = View.GONE
            this.lastnameText.visibility = View.GONE
            this.changeLastname.visibility = View.VISIBLE
            this.changeName.visibility = View.VISIBLE
            this.acceptEdit.visibility = View.VISIBLE
            this.edit.visibility = View.GONE
        }

        val inputManager:InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //Apply Edit Settings
        this.acceptEdit.setOnClickListener {
            this.nameText.visibility = View.VISIBLE
            this.lastnameText.visibility = View.VISIBLE
            this.changeLastname.visibility = View.INVISIBLE
            this.changeName.visibility = View.INVISIBLE
            this.acceptEdit.visibility = View.GONE
            this.edit.visibility = View.VISIBLE
            val view: View = findViewById(android.R.id.content)
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED)

            dto.post("updateName", """{"name" : """" + this.changeName.text.toString() + """", "lastname": """" + this.changeLastname.text.toString() + """", "id": """" + id + """"}""")

            val result = JSONObject(dto.getById("users", id?.toInt()!!))

            this.nameText.text = result.get("name").toString()
            this.lastnameText.text = result.get("lastname").toString()
        }

        //LOG OUT
        this.logout.setOnClickListener {
            settings.edit().putString("Logged in", "false").putString("id", id).apply()
            this.finish()
        }
    }

    fun switchToMapActivity(view : View){
        val next = Intent(this, GPS::class.java)
        startActivity(next)
    }

    private fun createHistory(listHash: HashMap<String, ArrayList<String>>?, listDataHeader: ArrayList<String>?) {
        val jsonHistory = JsonParser().parse(database.getById("history", id?.toInt()!!))
        for (it in jsonHistory) {
            val some = JSONObject(it)
            listDataHeader?.add("Dnia " + some.get("workout_date").toString().replace("T", " o godz ").replace("Z", ""))
            val innerList = ArrayList<String>()
            loop@ for (elem in some.keys()) {
                var tmpKey = elem
                when (elem) {
                    "name" -> {
                        tmpKey = "Imię "
                    }
                    "lastname" -> {
                        tmpKey = "Nazwisko "
                    }
                    "distance" -> {
                        tmpKey = "Odległość[km] "
                    }
                    "total_time" -> {
                        tmpKey = "Czas "
                    }
                    "workout_date" -> {
                        continue@loop
                    }
                }
                innerList.add(tmpKey + ": " + some.get(elem))
            }
            listHash?.put(listDataHeader!![jsonHistory.indexOf(it)], innerList)
        }
    }

    private fun createStats(Data: HashMap<String, ArrayList<String>>?): ArrayList<String> {
        var fullDistance = 0.0
        var fullTime = 0.0    //in minutes
        var bestFriend = ""
        var fullDistanceWithFriend = 0
        var averageSpeed = 0.0
        val tmpMapOfFriends = HashMap<String, Int>()
        var tmpFriend = ""

        if (Data != null) {
            for(elem in Data) {
                for(innerElem in elem.value) {
                    if(innerElem.substring(0, 9) == "Odległość") {
                        fullDistance += innerElem.substring(16).toDouble()
                    } else if(innerElem.substring(0, 4) == "Czas") {
                        fullTime += innerElem.substring(7, 9).toDouble() * 60 +
                                    innerElem.substring(10, 12).toDouble() +
                                    innerElem.substring(13, 15).toDouble() / 60
                    } else if(innerElem.substring(0, 4) == "Imię") {
                        tmpFriend = innerElem.substring(7) + " "
                    } else if(innerElem.substring(0, 8) == "Nazwisko") {
                        tmpFriend += innerElem.substring(11)
                        if (tmpMapOfFriends.isEmpty() || !tmpMapOfFriends.containsKey(tmpFriend)) {
                            tmpMapOfFriends.put(tmpFriend, 1)
                        }
                        else if (tmpMapOfFriends.containsKey(tmpFriend)) {
                            tmpMapOfFriends.put(tmpFriend, tmpMapOfFriends.getValue(tmpFriend) + 1)
                        }
                    }
                }

            }
            fullDistanceWithFriend = Collections.max(tmpMapOfFriends.values)
            for(entry in tmpMapOfFriends) {
                if(entry.value == fullDistanceWithFriend) {
                    bestFriend = entry.key
                    break
                }
            }
            averageSpeed = fullDistance / (fullTime / 60)
        }

        val resultList = ArrayList<String>()
        resultList.add("Łączna odległość : " + fullDistance.toString())
        resultList.add("Łączny czas : " + fullTime.toString())
        resultList.add("Średnia prędkość(km/h) : " + averageSpeed)
        resultList.add("Największy łączny czas z osobą : " + bestFriend)
        resultList.add("Liczba biegów z tą osobą : " + fullDistanceWithFriend.toString())

        return resultList
    }
}
