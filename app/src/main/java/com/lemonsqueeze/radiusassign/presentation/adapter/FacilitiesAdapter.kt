package com.lemonsqueeze.radiusassign.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lemonsqueeze.radiusassign.R
import com.lemonsqueeze.radiusassign.data.model.OptionModel
import com.lemonsqueeze.radiusassign.data.model.exclusion.ExclusionModel

class FacilitiesAdapter(
    private var options: List<OptionModel>,
    private val context: Context
): BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var itemImage: ImageView
    private lateinit var itemText: TextView

    override fun getCount(): Int {
        return options.size
    }

    override fun getItem(position: Int): Any {
        return options[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        if (layoutInflater == null) layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var counterView = view
        if (counterView == null) counterView = layoutInflater!!.inflate(R.layout.facility_item, null)

        itemImage = counterView!!.findViewById(R.id.item_img)
        itemText = counterView.findViewById(R.id.item_text)

        itemText.text = options[position].name

        counterView.setOnClickListener {
            Log.d("manish_cli", "${options[position].name}")
        }

        return counterView
    }

    fun setData(options: List<OptionModel>) {
        this.options = options
        notifyDataSetChanged()
    }

    fun toggleSelectionToNegate() {

    }
}