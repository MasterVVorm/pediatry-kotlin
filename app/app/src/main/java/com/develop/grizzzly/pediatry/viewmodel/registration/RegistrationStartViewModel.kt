package com.develop.grizzzly.pediatry.viewmodel.registration

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.coroutines.launch

class RegistrationStartViewModel : ViewModel()  {


    val phoneNumber = MutableLiveData<String>().apply { value = "" }

    var valid = MutableLiveData<Boolean>().apply { value = false }


    fun onRegister(view : View) {
        viewModelScope.launch {
            val login = WebAccess.pediatryApi.login("", "")
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_registration_start_to_registration_code)
        }


    }

}