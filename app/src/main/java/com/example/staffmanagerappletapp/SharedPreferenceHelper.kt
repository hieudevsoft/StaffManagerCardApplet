package com.example.staffmanagerappletapp

import android.content.Context

class SharedPreferenceHelper(private val context:Context) {
    private val pref = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)

    fun getNumberValid(codeNumberCard:String) = pref.getInt("NUMBER_VALID_"+codeNumberCard,3)
    fun setNumberValid(value:Int,codeNumberCard:String) = pref.edit().putInt("NUMBER_VALID_"+codeNumberCard,value).apply()

    fun getNumberCard() = pref.getString("NUMBER_CARD","909080807070")
    fun setNumberCard(value:String) = pref.edit().putString("NUMBER_CARD",value).apply()

    fun getStatusLockCard() = pref.getBoolean("STATUS_CARD",false)
    fun setStatusLockCard(value:Boolean) = pref.edit().putBoolean("STATUS_CARD",value).apply()

}