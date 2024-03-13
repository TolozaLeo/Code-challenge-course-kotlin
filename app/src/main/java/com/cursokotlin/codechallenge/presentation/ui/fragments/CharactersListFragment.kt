package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cursokotlin.codechallenge.databinding.FragmentCharactersListBinding
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.CharacterViewModel
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersListFragment  : Fragment() {
    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private val viewModel : CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.getCharacters()
//        viewModel.getEvents()
    }

    private fun setupObservers() {
        mainViewModel.uiState.observe(viewLifecycleOwner){
            it.navigateToEventsFragment?.getContentIfNotHandled()?.let {
                navigateToEventsFragment()
            }
        }
    }

    private fun navigateToEventsFragment() {
        val action = CharactersListFragmentDirections.actionCharactersListFragmentToEventListFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}