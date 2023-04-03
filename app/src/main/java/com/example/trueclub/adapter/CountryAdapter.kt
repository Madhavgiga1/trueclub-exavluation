package com.example.trueclub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trueclub.databinding.CountryRowLayoutBinding
import com.example.trueclub.models.Country

class CountryAdapter:RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){

    var countrylist=emptyList<Country>()

    class CountryViewHolder(private val binding: CountryRowLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(country: Country){
            binding.country=country
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):CountryViewHolder{
                val layoutinflater=LayoutInflater.from(parent.context)
                val binding=CountryRowLayoutBinding.inflate(layoutinflater,parent,false)
                return CountryViewHolder(binding)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        var currentcountry=countrylist[position]
        holder.bind(currentcountry)
    }

    override fun getItemCount(): Int {
        return countrylist.size
    }
}