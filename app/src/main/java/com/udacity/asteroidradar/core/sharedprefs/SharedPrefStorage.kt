package com.udacity.asteroidradar.core.sharedprefs


interface SharedPrefStorage {
    fun saveValue(key: String, value: String)
    fun saveValue(key: String, value: Int)
    fun saveValue(key: String, value: Boolean)
    fun saveValue(key: String, value: Float)
    fun saveValue(key: String, value: Long)
    fun getStringValue(key: String): String?
    fun getIntValue(key: String): Int?
    fun getBooleanValue(key: String): Boolean
    fun getFloatValue(key: String): Float?
    fun getLongValue(key: String): Long?
    fun clearPrefs()
}