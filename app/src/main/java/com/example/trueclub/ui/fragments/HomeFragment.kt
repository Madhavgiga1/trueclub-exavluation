package com.example.trueclub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trueclub.R
import kotlin.text.isNullOrEmpty

import com.example.trueclub.Utils.NetworkResult
import com.example.trueclub.adapter.CountryAdapter
import com.example.trueclub.databinding.FragmentHomeBinding
import com.example.trueclub.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    lateinit var mainViewModel:MainViewModel
    private val mAdapter by lazy { CountryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        binding.submitBtn.setOnClickListener {
            if(binding.inputValueField.text.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Please type your Name",Toast.LENGTH_SHORT)
            }
            else{
                requestApiData(binding.inputValueField.text.toString())
            }

        }
        return binding.root
    }
    private fun setupRecyclerView() {
        binding.countryRecyclerview.adapter=mAdapter
        binding.countryRecyclerview.layoutManager= LinearLayoutManager(requireContext())
    }
    private fun requestApiData(name:String) {
        mainViewModel.getDetails(applyQueries(name))
        mainViewModel.detialsResponse.observe(viewLifecycleOwner,{response ->
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let {


                        mAdapter.countrylist=it.country

                        setupRecyclerView()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    //showShimmerEffect()
                }
            }
        })
    }
    private fun applyQueries(name:String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["name"] = name
        return queries
    }


}