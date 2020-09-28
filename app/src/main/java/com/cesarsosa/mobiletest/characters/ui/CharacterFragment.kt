package com.cesarsosa.mobiletest.characters.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cesarsosa.mobiletest.R
import com.cesarsosa.mobiletest.binding.bindImageFromUrl
import com.cesarsosa.mobiletest.characters.data.Character
import com.cesarsosa.mobiletest.databinding.FragmentCharacterBinding
import com.cesarsosa.mobiletest.di.Injectable
import com.cesarsosa.mobiletest.di.injectViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

import com.cesarsosa.mobiletest.data.Result;
import com.cesarsosa.mobiletest.util.*

/**
 * A fragment representing a single Character detail screen.
 */
class CharacterFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CharacterViewModel

    private val args: CharacterFragmentArgs by navArgs()
    private lateinit var characterInfo: Character

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.id = args.id

        val binding = DataBindingUtil.inflate<FragmentCharacterBinding>(
            inflater, R.layout.fragment_character, container, false).apply {
            lifecycleOwner = this@CharacterFragment

        }

        subscribeUi(binding)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                intentShareText(requireActivity(), getString(R.string.share_character, characterInfo.name, characterInfo.urlDetail ?: ""))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(binding: FragmentCharacterBinding) {
        viewModel.character.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.spinner.hide()
                    result.data?.let { bindView(binding, it) }
                }
                Result.Status.LOADING -> binding.spinner.show()
                Result.Status.ERROR -> {
                    binding.spinner.hide()
                    Snackbar.make(binding.coordinatorLayout, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun bindView(binding: FragmentCharacterBinding, character: Character) {
        character.apply {
            setTitle(name)
            bindImageFromUrl(binding.image, imageURL)
            binding.description.text = description

            binding.comics.text = comics;
            binding.series.text = series;
            binding.stories.text = stories;
            binding.events.text = events;

            binding.comicsTitle.visibility = if (comics.isEmpty()) View.GONE else View.VISIBLE
            binding.seriesTitle.visibility = if (series.isEmpty()) View.GONE else View.VISIBLE
            binding.storiesTitle.visibility = if (stories.isEmpty()) View.GONE else View.VISIBLE
            binding.eventsTitle.visibility = if (events.isEmpty()) View.GONE else View.VISIBLE

            binding.btnMoreDetail.setOnClickListener { _ -> character.urlDetail?.let { intentOpenWebsite(requireActivity(),it) } }
            binding.btnWiki.setOnClickListener { _ -> character.urlWiki?.let { intentOpenWebsite(requireActivity(),it) } }
            binding.btnComic.setOnClickListener { _ -> character.urlComicLink?.let { intentOpenWebsite(requireActivity(),it) } }

            binding.btnMoreDetail.visibility = if (urlDetail.isEmpty()) View.GONE else View.VISIBLE
            binding.btnWiki.visibility = if (urlWiki.isEmpty()) View.GONE else View.VISIBLE
            binding.btnComic.visibility = if (urlComicLink.isEmpty()) View.GONE else View.VISIBLE

            characterInfo = character
        }
    }
}
