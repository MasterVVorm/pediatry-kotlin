package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import kotlinx.android.synthetic.main.fragment_registration_info.*
import android.content.Intent
import android.app.Activity
import com.develop.grizzzly.pediatry.util.setImage


class RegistrationInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        ivChangePhoto.setOnClickListener {
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"
            startActivityForResult(i, 200)
        }


        btnNext.setOnClickListener {
            navController.navigate(R.id.action_registration_info_to_registration_speciality)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?: return

        if (resultCode == Activity.RESULT_OK) {
            //todo save image url in fragment
            val mImageUri = data.data
            mImageUri?.toString()?.let { setImage(it, ivPhoto, android.R.color.white) }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}