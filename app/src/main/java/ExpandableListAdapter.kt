package program.trainersapp

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ListAdapter
import android.widget.TextView

import kotlin.collections.HashMap


class ExpandableListAdapter(private val context: Context, private val listDataHeader: List<String>, private val listHashMap: HashMap<String, ArrayList<String>>?) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    override fun getChildrenCount(i: Int): Int {
        return listHashMap!![listDataHeader[i]]!!.size
    }

    override fun getGroup(i: Int): Any {
        return listDataHeader[i]
    }

    override fun getChild(i: Int, i1: Int): Any {
        return listHashMap!![listDataHeader[i]]!![i1] // i = Group Item , i1 = ChildItem
    }

    override fun getGroupId(i: Int): Long {
        return i.toLong()
    }

    override fun getChildId(i: Int, i1: Int): Long {
        return i1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(i: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val headerTitle = getGroup(i) as String
        if (view == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_group, null)
        }
        val lblListHeader = view!!.findViewById(R.id.lblListHeader) as TextView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.text = headerTitle
        return view
    }

    override fun getChildView(i: Int, i1: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val childText = getChild(i, i1) as String
        if (view == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item, null)
        }

        val txtListChild = view!!.findViewById(R.id.lblListItem) as TextView
        txtListChild.text = childText
        return view
    }

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }
}