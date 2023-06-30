package com.lemonsqueeze.radiusassign.utils

import com.lemonsqueeze.radiusassign.R

class RadioButtonDrawable {
    private val hm: HashMap<String, Int> = HashMap()

    init {
        hm["apartment"] = R.drawable.ic_apartment
        hm["condo"] = R.drawable.ic_condo
        hm["boat"] = R.drawable.ic_boat
        hm["land"] = R.drawable.ic_boat
        hm["rooms"] = R.drawable.ic_room
        hm["no-rooms"] = R.drawable.ic_no_room
        hm["swimming"] = R.drawable.ic_swimming
        hm["garden"] = R.drawable.ic_garden
        hm["garage"] = R.drawable.ic_garage
    }

    fun getDrawable(key: String): Int {
        return hm.getOrDefault(key, R.drawable.ic_no_room)
    }

}