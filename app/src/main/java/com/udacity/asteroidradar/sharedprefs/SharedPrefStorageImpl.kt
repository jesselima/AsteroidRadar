package com.udacity.asteroidradar.sharedprefs

import android.content.SharedPreferences

private const val FILE_USER_PREFS = "USER_PREFS"

class SharedPrefStorageImpl(
    private val prefs: SharedPreferences,
) : SharedPrefStorage {

    override fun saveValue(key: String, value: String) {
        prefs.edit()
            .putString(key, value)
            .apply()
    }

    override fun saveValue(key: String, value: Int) {
        prefs.edit()
            .putInt(key, value)
            .apply()
    }

    override fun saveValue(key: String, value: Boolean) {
        prefs.edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun saveValue(key: String, value: Float) {
        prefs.edit()
            .putFloat(key, value)
            .apply()
    }

    override fun saveValue(key: String, value: Long) {
        prefs.edit()
            .putLong(key, value)
            .apply()
    }

    override fun getStringValue(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun getIntValue(key: String): Int? {
        return prefs.getInt(key, -1)
    }

    override fun getFloatValue(key: String): Float? {
        return prefs.getFloat(key, -1f)
    }

    override fun getLongValue(key: String): Long? {
        return prefs.getLong(key, -1L)
    }

    override fun getBooleanValue(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    override fun clearPrefs() {
        prefs.edit()
            .remove(FILE_USER_PREFS)
            .apply()
    }
}