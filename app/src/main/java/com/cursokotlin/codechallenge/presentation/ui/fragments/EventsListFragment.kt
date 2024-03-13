package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cursokotlin.codechallenge.databinding.FragmentEventListBinding
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsListFragment : Fragment() {
    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        mainViewModel.uiState.observe(viewLifecycleOwner){
            it.navigateToCharactersFragment?.getContentIfNotHandled()?.let {
                navigateToCharactersFragment()
            }
        }
    }

    private fun navigateToCharactersFragment() {
        val action = EventsListFragmentDirections.actionEventsListFragmentToCharactersListFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}