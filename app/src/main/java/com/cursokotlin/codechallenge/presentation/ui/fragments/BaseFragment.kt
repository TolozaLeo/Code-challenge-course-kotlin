package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cursokotlin.codechallenge.R
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.NavigationViewModel
import com.cursokotlin.codechallenge.utils.gone
import com.cursokotlin.codechallenge.utils.visible
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make

open class BaseFragment : Fragment() {

    private var loadingAnimation : View? = null

    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingAnimation = requireActivity().findViewById(R.id.progressBar)
    }

    fun changeToolbarTitle(title: String){
        navigationViewModel.changeToolBarTitle(title)
    }

    fun showServerError(error: ServerError) {
        Toast.makeText(context, error.getErrorMessage(), Toast.LENGTH_LONG).show()
    }

    fun showLoadingAnimation(){
        loadingAnimation.visible()
    }
    fun hideLoadingAnimation(){
        loadingAnimation.gone()
    }

}