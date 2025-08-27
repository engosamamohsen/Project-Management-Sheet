package com.example.projectmanagement.preferences

import android.content.Context
import android.content.SharedPreferences
import android.provider.SyncStateContract
import android.util.Log
import com.example.projectmanagement.common.helper.Constants
import com.example.projectmanagement.model.UserModel
import com.google.gson.Gson
import javax.inject.Inject

class AppPreferences @Inject constructor(private val context: Context) {

  private val STORE_NAME = "app_data_store"

  private val USER_DATA = Pair("USER_DATA", "")

  private val appPreferences: SharedPreferences =
    context.getSharedPreferences(Constants.APP_PREFERENCES_NAME, Context.MODE_PRIVATE)

  private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
  }

  fun getIsLoggedIn(): Boolean {
//    Log.d(TAG, "getIsLoggedIn: ${appPreferences.getInt("id",-1)}")
    return appPreferences.getString("id", "") != ""
  }

  fun saveUser(user: UserModel) {
    Log.d(TAG, "saveUser: SAVING USER ${user.token}")
    appPreferences.edit {
      it.putString(USER_DATA.first, Gson().toJson(user))
      it.putString(Constants.TOKEN, user.token)
      it.putString("id", user.id.toString())
      it.apply()
    }
  }

  private  val TAG = "AppPreferences"
  fun getUser(): UserModel {
    val value: String? = appPreferences.getString(USER_DATA.first, USER_DATA.second)
    Log.d(TAG, "getUser: $value")
    if (value != null && value.isNotEmpty())
      return Gson().fromJson(value, UserModel::class.java)
    else return UserModel()
  }


  fun clearUser() {
    appPreferences.edit {
      it.putString(USER_DATA.first, "")
      it.putString(Constants.TOKEN, "")
      it.putString("id", "")
      it.apply()
    }
  }

  fun saveKey(key: String, value: String) {
    appPreferences.edit {
      it.putString(key, value)
    }
  }

  fun getKey(key: String): String {
    return appPreferences.getString(key, "").toString()
  }

  fun getKeyInt(key: String): Int {
    return appPreferences.getInt(key, -1)
  }

  fun clearPreferences() {
    appPreferences.edit {
      it.clear().apply()
    }
  }


}