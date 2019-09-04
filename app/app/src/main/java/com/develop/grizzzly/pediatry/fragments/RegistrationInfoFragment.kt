package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import android.content.Intent
import android.app.Activity
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import com.develop.grizzzly.pediatry.databinding.FragmentRegistrationInfoBinding
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_registration_info.*

const val TAG = "REGISTRATION INFO F"

class RegistrationInfoFragment : Fragment() {


    lateinit var model : RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentRegistrationInfoBinding>(
            inflater,
            R.layout.fragment_registration_info,
            container,
            false
        )

        val listener = MaskedTextChangedListener("+7 ([000]) [000]-[00]-[00]", binding.tePhone)
        binding.tePhone.addTextChangedListener(listener)

        model = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        }!!
        model.fragment = this

        binding.model = model
        binding.lifecycleOwner = this


        model.city.observe(this, Observer {

            if (model.isValidCity()) {
                teCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0)
            } else {
                teCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_light_cross, 0)
            }

            model.infoValid.value = model.isInfoValid()
        })

        model.phoneNumber.observe(this, Observer {
            model.infoValid.value = model.isInfoValid()

            if (model.isValidPhone()) {
                tePhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0)
            } else {
                tePhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_light_cross, 0)
            }

        })

        model.fullname.observe(this, Observer {
            model.infoValid.value = model.isInfoValid()
            if (model.isValidFullname()) {
                teFullName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0)
            } else {
                teFullName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_light_cross, 0)
            }
        })

        return binding.root
    }

    override fun onDetach() {
        model.clearInfo()
        super.onDetach()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?: return
        if (resultCode == Activity.RESULT_OK) {
            model.imageUrl.postValue(data.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}