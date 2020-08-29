package com.valentinhinov.ganandroidtest.feature.browser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.valentinhinov.ganandroidtest.R
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import kotlinx.android.synthetic.main.item_character.view.*

class BrowserAdapter(context: Context, private val onCharacterClicked: (SeriesCharacter) -> Unit) :
    ListAdapter<SeriesCharacter, BrowserAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<SeriesCharacter>() {

            override fun areItemsTheSame(
                oldItem: SeriesCharacter,
                newItem: SeriesCharacter
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SeriesCharacter,
                newItem: SeriesCharacter
            ): Boolean = oldItem == newItem
        }
    ) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(layoutInflater.inflate(R.layout.item_character, parent, false))
            .apply {
                itemView.setOnClickListener {
                    onCharacterClicked(getItem(bindingAdapterPosition))
                }
            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)

        holder.itemView.characterTitle.text = character.name

        holder.itemView.characterImageView.load(character.imgUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_person_24)
            error(R.drawable.ic_baseline_person_24)
        }
    }

    class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)
}