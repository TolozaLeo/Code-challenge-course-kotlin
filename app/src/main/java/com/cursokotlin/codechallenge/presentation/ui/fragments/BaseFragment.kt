package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make

open class BaseFragment : Fragment() {

//    val navigationViewModel: NavigationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun changeToolbarTitle(title: String){
//        navigationViewModel.changeToolBarTitle(title)
    }

    fun showServerError(error: ServerError) {
        Toast.makeText(context, error.getErrorMessage(), Toast.LENGTH_LONG).show()
    }

}