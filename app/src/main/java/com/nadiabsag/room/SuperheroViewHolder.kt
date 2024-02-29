package com.nadiabsag.room
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nadiabsag.database.entities.ListEntity
import com.nadiabsag.room.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroEntity: ListEntity, navigateToDetailActivity: (String) -> Unit) {
        binding.tvSuperheroName.text = superheroEntity.name
        Picasso.get().load(superheroEntity.image).into(binding.ivSuperhero)
        binding.root.setOnClickListener {
            navigateToDetailActivity(superheroEntity.id.toString())
        }
    }
}