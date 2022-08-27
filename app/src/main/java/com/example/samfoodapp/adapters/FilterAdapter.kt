package com.example.samfoodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samfoodapp.databinding.ItemFilterBinding
import com.example.samfoodapp.models.Meal

class FilterAdapter:
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    companion object {
        const val TAG = "FilterAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewCategory: Int): FilterViewHolder {
        val itemFilterBinding = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilterViewHolder(itemFilterBinding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.setFilter(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setFilter(meal: Meal) {
            Glide.with(binding.root)
                .load(meal.strMealThumb)
                .into(binding.rvImage)
            binding.rvTitle.text=meal.strMeal
            binding.itemSource.setOnClickListener {
                onItemClickListener?.let{it(meal)}
            }
        }
    }

    private var onItemClickListener: ((Meal) -> Unit)? = null

    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback= object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

}