package com.cesarsosa.mobiletest.characters.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cesarsosa.mobiletest.characters.data.Character
import com.cesarsosa.mobiletest.databinding.ListItemCharacterBinding


/**
 * Adapter for the [RecyclerView] in [CharactersFragment].
 */
class CharactersAdapter : PagedListAdapter<Character, CharactersAdapter.ViewHolder>(CharactertDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        character?.let {
            holder.apply {
                bind(createOnClickListener(character.id), character)
                itemView.tag = character
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {

            val direction = CharactersFragmentDirections.actionCharactersFragmentToCharacterFragment(id)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(private val binding: ListItemCharacterBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Character
                 ) {
            binding.apply {
                clickListener = listener
                character = item
                executePendingBindings()
            }
        }
    }
}

private class CharactertDiffCallback : DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id.equals(newItem.id)
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id.equals(newItem.id)
    }
}