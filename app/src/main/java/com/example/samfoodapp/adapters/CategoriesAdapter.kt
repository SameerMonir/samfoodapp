package com.example.samfoodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samfoodapp.databinding.ItemCategoryBinding
import com.example.samfoodapp.models.Category

class CategoriesAdapter:
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    companion object {
        const val TAG = "CategoriesAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewCategory: Int): CategoriesViewHolder {
        val itemCategoryBinding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoriesViewHolder(itemCategoryBinding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.setCategories(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CategoriesViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setCategories(Category: Category) {
            binding.rvTitle.text=Category.strCategory
            Glide.with(binding.root)
                .load(Category.strCategoryThumb)
                .into(binding.rvImage)
            binding.itemSource.setOnClickListener {
                onItemClickListener?.let{it(Category)}
            }
        }
    }

    private var onItemClickListener: ((Category) -> Unit)? = null

    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback= object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory==newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

}