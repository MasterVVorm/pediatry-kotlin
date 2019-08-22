package com.develop.grizzzly.pediatry.viewmodel.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.MainApplication
import com.develop.grizzzly.pediatry.R

import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.db.model.User
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.util.md5
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }


    fun onLogin(view : View) {
        viewModelScope.launch {
            val response = WebAccess.pediatryApi.login(email.value.toString(), password.value.toString().md5())
            if (response.isSuccessful) {
                Log.d("TAG", response.toString())
                Log.d("TAG", response.body()?.status.toString())
                if (response.body()!!.status == 200L) {
                    WebAccess.token = response.body()?.response?.token ?: ""
                    WebAccess.id = response.body()?.response?.id ?: 0
                    val user = User(0, email.value, password.value.toString().md5())
                    DatabaseAccess.database.userDao().saveUser(user)

                    val context = view.context
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    Toast.makeText(MainApplication.get(), "Wrong email or password", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(MainApplication.get(), "Wrong email or password", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun onRegister(view : View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_login_to_registration)
    }

    fun onRecover(view: View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_login_to_recovery)
    }


}