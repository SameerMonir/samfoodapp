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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samfoodapp.R
import com.example.samfoodapp.adapters.CategoriesAdapter
import com.example.samfoodapp.adapters.ListAdapter
import com.example.samfoodapp.databinding.FragmentCategoriesBinding
import com.example.samfoodapp.databinding.FragmentIngrediantBinding
import com.example.samfoodapp.models.FilterQuery
import com.example.samfoodapp.ui.MainActivity
import com.example.samfoodapp.ui.viewModels.FoodViewModel
import com.example.samfoodapp.util.Resource

class IngredientFragment : Fragment(R.layout.fragment_ingrediant) {

    companion object {
        const val TAG = "IngredientFragment"
    }
    private lateinit var viewModel: FoodViewModel
    private lateinit var listAdapter: ListAdapter
    private lateinit var binding: FragmentIngrediantBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_ingrediant,container,false)

        viewModel=(activity as MainActivity).viewModel

        viewModel.getIngredientList()

        setUpRecyclerView()

        listAdapter.setOnItemClickListener {
            Log.d(CategoriesFragment.TAG,"Clicked")
            val bundle=Bundle().apply {
                putSerializable("Filter",
                    it.strIngredient?.let { it1 -> FilterQuery(queryType = "i", query = it1) })
            }
            findNavController().navigate(
                R.id.action_ingrediantFragment_to_filterFragment,
                bundle
            )
        }

        viewModel.filters.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let{
                        listAdapter.differ.submitList(it.meals?.toList())
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
        listAdapter = ListAdapter()
        binding.rvCategories.apply {
            adapter = listAdapter
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