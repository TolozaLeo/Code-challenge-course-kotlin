package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursokotlin.codechallenge.data.internal.adapteritems.EventAdapterItem
import com.cursokotlin.codechallenge.databinding.FragmentHomeBinding
import com.cursokotlin.codechallenge.presentation.ui.adapters.EventsListAdapter
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.EventsViewModel
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private val viewModel : EventsViewModel by viewModels()

    private lateinit var adapter : EventsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
//        viewModel.getEvents()
    }

    private fun setupObservers() {
        mainViewModel.uiState.observe(viewLifecycleOwner){
            it.navigateToCharactersFragment?.getContentIfNotHandled()?.let {
                navigateToCharactersFragment()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner){
            it.showEventsList?.getContentIfNotHandled()?.let { eventsList ->
                setupRecyclerView(eventsList)
            }
            it.showError?.getContentIfNotHandled()?.let { error ->
                showServerError(error)
            }
        }
    }

    private fun setupRecyclerView(eventsList: List<EventAdapterItem>) {
        adapter = EventsListAdapter()
        adapter.submitList(eventsList)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun navigateToCharactersFragment() {
        val action = EventsFragmentDirections.actionEventsFragmentToCharactersFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}