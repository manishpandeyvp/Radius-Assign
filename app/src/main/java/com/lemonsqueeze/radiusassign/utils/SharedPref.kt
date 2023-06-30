package com.lemonsqueeze.radiusassign.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPref {
    companion object {
        private var instance: SharedPreferences? = null

        @Synchronized
        fun getInstance(activity: Activity): SharedPreferences {
            if (instance == null) {
                instance =
                    activity.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)
            }
            return instance!!
        }
    }
}