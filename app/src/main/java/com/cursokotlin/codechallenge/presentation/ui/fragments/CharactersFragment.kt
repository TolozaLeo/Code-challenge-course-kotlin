package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursokotlin.codechallenge.R
import com.cursokotlin.codechallenge.data.internal.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.databinding.FragmentHomeBinding
import com.cursokotlin.codechallenge.presentation.ui.adapters.CharactersListAdapter
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.CharacterViewModel
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment  : BaseFragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : CharacterViewModel by viewModels()

    private lateinit var adapter: CharactersListAdapter

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
        viewModel.getCharacters()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner){
            it.showCharactersList?.getContentIfNotHandled()?.let { list ->
                setupRecyclerView(list)
            }
            it.showError?.getContentIfNotHandled()?.let { error ->
                showServerError(error)
            }
            if(it.showLoading) showLoadingAnimation() else hideLoadingAnimation()
        }
    }

    private fun setupRecyclerView(list: List<CharacterAdapterItem>) {
        adapter = CharactersListAdapter(this)
        adapter.submitList(list)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun onClickCharacterListener(characterClicked: CharacterAdapterItem){
        navigateToDescriptionFragment(characterClicked)
    }

    private fun navigateToDescriptionFragment(characterClicked: CharacterAdapterItem) {
        val action = CharactersFragmentDirections
            .actionCharactersFragmentToDescriptionFragment(characterClicked)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}