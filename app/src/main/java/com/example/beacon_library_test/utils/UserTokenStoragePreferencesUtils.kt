package com.example.beacon_library_test.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.beacon_library_test.data.models.TokenWithAccessEnd
import javax.inject.Inject

class UserTokenStoragePreferencesUtils @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun writeNewToken(token: TokenWithAccessEnd) {
        sharedPreferences.edit {
            putString(TOKEN_SHARED_PREF_KEY, token.token)
            putString(TOKEN_ACCESS_END_SHARED_PREF_KEY, token.accessEnd)
        }
    }

    fun getToken(): TokenWithAccessEnd? {
        val token = sharedPreferences.getString(TOKEN_SHARED_PREF_KEY, "")
        val end = sharedPreferences.getString(TOKEN_ACCESS_END_SHARED_PREF_KEY, "")
        return if (token == "" || token == null || end == "" || end == null) null else TokenWithAccessEnd(
            token,
            end
        )
    }

    companion object {
        const val TOKEN_SHARED_PREF_KEY = "token"
        const val TOKEN_ACCESS_END_SHARED_PREF_KEY = "accessEnd"
    }

}