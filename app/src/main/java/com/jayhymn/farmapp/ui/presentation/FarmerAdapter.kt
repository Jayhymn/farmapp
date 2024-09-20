package com.jayhymn.farmapp.ui.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.data.Farmer
import com.jayhymn.farmapp.databinding.FarmerRecordItemBinding
import com.jayhymn.farmapp.ui.state.FarmerItemUiState

class FarmerAdapter(
    private var farmerList: List<FarmerItemUiState>
) :
    RecyclerView.Adapter<FarmerAdapter.FarmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmerViewHolder {
        val binding = FarmerRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FarmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FarmerViewHolder, position: Int) {
        val farmer = farmerList[position]
        holder.bind(farmer)
    }

    fun updateList(newFarmers: List<FarmerItemUiState>) {
        farmerList = newFarmers
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = farmerList.size

    inner class FarmerViewHolder(private val binding: FarmerRecordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(farmer: FarmerItemUiState) {
            binding.tvFarmerName.text = farmer.name
//            binding.tvFarmerGender.text = binding.root.context.getString(R.string.gender_format, farmer.gender)
            binding.tvFarmerPhone.text = binding.root.context.getString(R.string.phone_format, farmer.phoneNumber)
            binding.tvCropType.text = binding.root.context.getString(R.string.crop_type_format, farmer.cropType)
        }
    }
}
