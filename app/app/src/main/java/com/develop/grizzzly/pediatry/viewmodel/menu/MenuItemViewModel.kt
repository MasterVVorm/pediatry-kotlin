package com.develop.grizzzly.pediatry.viewmodel.menu

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.util.setImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuItemViewModel(val menuItem: MenuItem) : ViewModel() {


    fun onMenuItem(view : View) {
        viewModelScope.launch {
            delay(100)
            val navController = Navigation.findNavController(view)
            navController.navigate(menuItem.direction)
        }

    }


    companion object {
        @BindingAdapter("bind:resource")
        @JvmStatic
        fun loadImage(view: ImageView, resource: Int?) {
            resource?.let { it -> setImage(it.toString(), view, resource) }
        }
    }
}