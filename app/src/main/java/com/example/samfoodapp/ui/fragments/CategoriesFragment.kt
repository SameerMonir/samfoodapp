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
import com.example.samfoodapp.databinding.FragmentCategoriesBinding
import com.example.samfoodapp.models.FilterQuery
import com.example.samfoodapp.ui.MainActivity
import com.example.samfoodapp.ui.viewModels.FoodViewModel
import com.example.samfoodapp.util.Resource

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    companion object {
        const val TAG = "CategoriesFragment"
    }
    private lateinit var viewModel: FoodViewModel
    private lateinit var catergoriesAdapter: CategoriesAdapter
    private lateinit var binding: FragmentCategoriesBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_categories,container,false)
        
        viewModel=(activity as MainActivity).viewModel

        viewModel.getCategories()
        
        setUpRecyclerView()

        catergoriesAdapter.setOnItemClickListener {
            Log.d(TAG,"Clicked")
            val bundle=Bundle().apply {
                putSerializable("Filter",FilterQuery(queryType = "c", query = it.strCategory))
            }
            findNavController().navigate(
                R.id.action_categoriesFragment_to_filterFragment,
                bundle
            )
        }
        
        viewModel.categories.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let{
                        catergoriesAdapter.differ.submitList(it.categories.toList())
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
        catergoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            adapter = catergoriesAdapter
            layoutManager=LinearLayoutManager(requireContext())
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}