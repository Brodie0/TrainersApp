package program.trainersapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_info.*
import org.json.JSONObject
import program.trainersapp.model.Dto
import program.trainersapp.model.JsonParser

class Info : Activity() {

    private var listDataHeader: ArrayList<String>? = null
    private var listHash: HashMap<String, ArrayList<String>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val database = Dto()

        //On Click Actions
        this.stats.setOnClickListener {
            this.stats.setBackgroundColor(Color.CYAN)
            this.history.setBackgroundColor(Color.WHITE)
            this.friends.setBackgroundColor(Color.WHITE)
            //clearing ExpandableListAdapter
            this.lvExp.setAdapter(ExpandableListAdapter(this, ArrayList(), HashMap()))


        }

        this.history.setOnClickListener {
            this.history.setBackgroundColor(Color.CYAN)
            this.stats.setBackgroundColor(Color.WHITE)
            this.friends.setBackgroundColor(Color.WHITE)
            val jsonHistory = JsonParser().parse(database.getById("history", 1))
            listDataHeader = ArrayList()
            listHash = HashMap()
            for(it in jsonHistory) {
                val some = JSONObject(it)
                listDataHeader?.add("Dnia " + some.get("workout_date").toString().replace("T", " o godz ").replace("Z", ""))
                val innerList = ArrayList<String>()
                loop@ for (elem in some.keys()) {
                    var tmpKey = elem
                    when(elem) {
                        "name" -> {tmpKey = "Imię "}
                        "lastname" -> {tmpKey = "Nazwisko "}
                        "distance" -> {tmpKey = "Odległość[km] "}
                        "total_time" -> {tmpKey = "Czas "}
                        "workout_date" -> {continue@loop}
                    }
                    innerList.add(tmpKey + ": " + some.get(elem))
                }
                listHash?.put(listDataHeader!![jsonHistory.indexOf(it)], innerList)
            }
            val listAdapter = ExpandableListAdapter(this, listDataHeader!!,listHash)
            this.lvExp.setAdapter(listAdapter)

        }
        this.friends.setOnClickListener {
            this.friends.setBackgroundColor(Color.CYAN)
            this.history.setBackgroundColor(Color.WHITE)
            this.stats.setBackgroundColor(Color.WHITE)
        }
    }

    fun switchToMapActivity(view : View){
        val next = Intent(this, GPS::class.java)
        startActivity(next)
    }
}
