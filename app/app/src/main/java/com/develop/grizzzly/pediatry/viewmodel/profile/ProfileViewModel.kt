package com.develop.grizzzly.pediatry.viewmodel.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Speciality

class ProfileViewModel : ViewModel() {

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }
    val city = MutableLiveData<String>().apply { value = "" }
    val name = MutableLiveData<String>().apply { value = "" }
    val lastname = MutableLiveData<String>().apply { value = "" }
    val middlename = MutableLiveData<String>().apply { value = "" }
    val mainSpeciality = MutableLiveData<Long>().apply { value = null }
    val firstAdditionalSpeciality = MutableLiveData<Long>().apply { value = null }
    val secondAdditionalSpeciality = MutableLiveData<Long>().apply { value = null }
    val avatarUrl = MutableLiveData<String>().apply { value = "error" }
    val phoneNumber = MutableLiveData<String>().apply { value = "" }

    var  mainSpecialities : List<Speciality> = listOf()
    var  additionalSpecialities : List<Speciality> = listOf()

    fun getMainSpecialityName() : String {
        for (spec in mainSpecialities) {
            if (spec.id == mainSpeciality.value) {
                return spec.name
            }
        }
        return ""
    }

    fun getFirstAdditionalSpecialityName() : String {
        for (spec in additionalSpecialities) {
            if (spec.id == firstAdditionalSpeciality.value) {
                return spec.name
            }
        }
        return ""
    }

    fun getSecondAdditionalSpecialityName() : String {
        for (spec in additionalSpecialities) {
            if (spec.id == secondAdditionalSpeciality.value) {
                return spec.name
            }
        }
        return ""
    }

}