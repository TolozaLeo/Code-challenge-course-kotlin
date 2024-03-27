package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cursokotlin.codechallenge.data.internal.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.databinding.FragmentHomeBinding
import com.cursokotlin.codechallenge.presentation.ui.adapters.CharactersListAdapter
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.CharacterViewModel
import com.cursokotlin.codechallenge.utils.EndlessScrollRecyclerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels()

    private lateinit var adapter: CharactersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        viewModel.uiState.observe(viewLifecycleOwner) {
            it.showCharactersList?.getContentIfNotHandled()?.let { list ->
                setupRecyclerView(list)
            }

            it.refreshCharactersList?.getContentIfNotHandled()?.let { newList ->
                updateRecyclerViewData(newList)
            }

            it.showError?.getContentIfNotHandled()?.let { error ->
                showServerError(error)
            }

            if (it.showLoading) showLoadingAnimation() else hideLoadingAnimation()
        }
    }

    private fun setupRecyclerView(list: List<CharacterAdapterItem>) {
        adapter = CharactersListAdapter(this)
        binding.recyclerView.adapter = adapter
        adapter.submitList(list)

        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.addOnScrollListener(object : EndlessScrollRecyclerListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.getCharacters(page)
            }
        })

    }

    private fun updateRecyclerViewData(newList: List<CharacterAdapterItem>) {
        adapter.submitList(newList)
    }

    fun onClickCharacterListener(characterClicked: CharacterAdapterItem) {
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