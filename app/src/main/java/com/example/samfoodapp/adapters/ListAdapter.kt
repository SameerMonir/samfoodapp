package com.example.samfoodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samfoodapp.databinding.ItemListBinding
import com.example.samfoodapp.models.Meal

class ListAdapter :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    companion object {
        const val TAG = "ListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewCategory: Int): ListViewHolder {
        val itemListBinding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.setList(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setList(meal: Meal) {
            if (meal.strArea != null)
                binding.rvTitle.text = meal.strArea

            if (meal.strIngredient != null)
                binding.rvTitle.text = meal.strIngredient

            binding.itemSource.setOnClickListener {
                onItemClickListener?.let{it(meal)}
            }
        }
    }

    private var onItemClickListener: ((Meal) -> Unit)? = null

    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}