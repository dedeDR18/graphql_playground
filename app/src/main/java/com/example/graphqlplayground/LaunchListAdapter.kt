package com.example.graphqlplayground

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.graphqlplayground.databinding.LaunchItemBinding

class LaunchListAdapter(private val launches: List<LaunchListQuery.Launch>) : RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = LaunchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    var onItemClicked: ((LaunchListQuery.Launch) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launches[position]
        holder.binding.site.text = launch.site ?: ""
        holder.binding.missionName.text = launch.mission?.name
        holder.binding.missionPatch.load(launch.mission?.missionPatch){
            placeholder(R.drawable.ic_baseline_image_24)
        }

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(launch)
        }
    }

    override fun getItemCount() = launches.size

    class ViewHolder(val binding: LaunchItemBinding): RecyclerView.ViewHolder(binding.root)
}