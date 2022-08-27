package com.example.samfoodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samfoodapp.R
import com.example.samfoodapp.adapters.CategoriesAdapter
import com.example.samfoodapp.adapters.FilterAdapter
import com.example.samfoodapp.databinding.FragmentCategoriesBinding
import com.example.samfoodapp.databinding.FragmentFilterBinding
import com.example.samfoodapp.models.FilterQuery
import com.example.samfoodapp.ui.MainActivity
import com.example.samfoodapp.ui.viewModels.FoodViewModel
import com.example.samfoodapp.util.Resource

class FilterFragment : Fragment(R.layout.fragment_filter) {

    companion object {
        const val TAG = "FilterFragment"
    }
    private lateinit var viewModel: FoodViewModel
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var binding: FragmentFilterBinding
    private val args:FilterFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_filter,container,false)

        viewModel=(activity as MainActivity).viewModel

        setUpRecyclerView()

        val filter=args.filter

        when(filter.queryType){
            "a"->{
                viewModel.getFilterByArea(filter.query)
            }
            "c"->{
                viewModel.getFilterByCategory(filter.query)
            }
            "i"->{
                viewModel.getFilterByIngredient(filter.query)
            }
        }

        Log.d(TAG,"Inside filter fragment")

        filterAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putString("RecipeID", it.idMeal)
            }
            findNavController().navigate(
                R.id.action_filterFragment_to_recipeFragment,
                bundle
            )
        }

        viewModel.filters.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let{
                        filterAdapter.differ.submitList(it.meals?.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(requireContext(),"An error occurred", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        return binding.root
    }

    private fun setUpRecyclerView() {
        filterAdapter = FilterAdapter()
        binding.rvCategories.apply {
            adapter = filterAdapter
            layoutManager= LinearLayoutManager(requireContext())
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}