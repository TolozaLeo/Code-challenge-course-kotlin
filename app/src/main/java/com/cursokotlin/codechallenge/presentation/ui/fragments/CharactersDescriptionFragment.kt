package com.cursokotlin.codechallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursokotlin.codechallenge.R
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicAdapterHeaderItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicAdapterItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicItem
import com.cursokotlin.codechallenge.databinding.FragmentHomeBinding
import com.cursokotlin.codechallenge.presentation.ui.adapters.ComicsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersDescriptionFragment : BaseFragment() {
    private val args: CharactersDescriptionFragmentArgs by navArgs()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    private val viewModel : CharacterDescriptionViewModel by viewModels()

    private lateinit var adapter: ComicsListAdapter

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
//        TODO THIS FUNCTION CONVERTS THE characterData TO A comicAdapterItenm
//        viewModel.setupData(args.characterData)
        val comics = ComicItem("el hombre reloco(2000)", "2000")
        setupRecyclerView(
            ComicAdapterItem(
                headerModel = ComicAdapterHeaderItem(R.string.lorem_ipsum.toString(), ""),
                comics = listOf(comics)
            )
        )
    }

    private fun setupRecyclerView(item: ComicAdapterItem) {
        adapter = ComicsListAdapter(item)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    /*  Necesito cambiar la forma de manejar la toolbar y el bottomNavBar
    entonces la idea es tener un fragment padre del que todos heredan de el,
    este padre va a tener la definicion de funciones tales como:
    - changeTitleToolbar(title) por defecto esta "MARVEL" pero se cambiaria cuando se crea este fragment
    - hideBottomNavBar() utulizada en login y aca para esconder la barra de navegacion
    - hideToolbar() utilizada por el login fragment
    - showBackButton() utilizada por este fragment
    - showServerError(ServerError) para mostrar una snackBar con los errores de la api */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}