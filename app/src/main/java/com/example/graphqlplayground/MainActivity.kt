package com.example.graphqlplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.exception.ApolloException
import com.example.graphqlplayground.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(LaunchListQuery()).execute()
            } catch (e: ApolloException){
                Log.d("LaunchList", "failure", e)
                null
            }

            val launches = response?.data?.launches?.launches?.filterNotNull()
            if (launches != null && !response.hasErrors()){
                val adapterLaunch = LaunchListAdapter(launches)
                binding.launches.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.launches.adapter = adapterLaunch
            }
        }
    }
}