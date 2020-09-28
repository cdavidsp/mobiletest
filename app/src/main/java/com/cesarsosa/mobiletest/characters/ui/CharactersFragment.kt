package com.cesarsosa.mobiletest.characters.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cesarsosa.mobiletest.R
import com.cesarsosa.mobiletest.databinding.FragmentCharactersBinding
import com.cesarsosa.mobiletest.di.Injectable
import com.cesarsosa.mobiletest.di.injectViewModel
import com.cesarsosa.mobiletest.ui.GridSpacingItemDecoration
import com.cesarsosa.mobiletest.ui.VerticalItemDecoration
import com.cesarsosa.mobiletest.util.hide
import com.cesarsosa.mobiletest.util.ConnectivityUtil
import javax.inject.Inject

class CharactersFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CharactersViewModel


    private lateinit var binding: FragmentCharactersBinding
    private val adapter: CharactersAdapter by lazy { CharactersAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var gridLayoutManager: GridLayoutManager
    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
            resources.getDimension(R.dimen.margin_normal).toInt())
    }
    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
            SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt())
    }

    private var isLinearLayoutManager: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = context?.let { ConnectivityUtil.isConnected(it) }!!

        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        context ?: return binding.root

        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)
        setLayoutManager()
        binding.recyclerView.adapter = adapter


        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_representation, menu)
        setDataRepresentationIcon(menu.findItem(R.id.list))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.list -> {
                isLinearLayoutManager = !isLinearLayoutManager
                setDataRepresentationIcon(item)
                setLayoutManager()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: CharactersAdapter) {
        viewModel.characters.observe(viewLifecycleOwner) {

            binding.spinner.hide()
            adapter.submitList(it)
        }
    }

    private fun setLayoutManager() {
        val recyclerView = binding.recyclerView

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        if (isLinearLayoutManager) {
            recyclerView.removeItemDecoration(gridDecoration)
            recyclerView.addItemDecoration(linearDecoration)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.removeItemDecoration(linearDecoration)
            recyclerView.addItemDecoration(gridDecoration)
            recyclerView.layoutManager = gridLayoutManager
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun setDataRepresentationIcon(item: MenuItem) {
        item.setIcon(if (isLinearLayoutManager)
            R.drawable.ic_grid_list_24dp else R.drawable.ic_list_white_24dp)
    }

    companion object {
        const val SPAN_COUNT = 2
    }
}

