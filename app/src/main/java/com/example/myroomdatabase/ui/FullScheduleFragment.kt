package com.example.myroomdatabase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroomdatabase.BusScheduleApplication
import com.example.myroomdatabase.BusStopAdapter
import com.example.myroomdatabase.databinding.FragmentFullScheduleBinding
import com.example.myroomdatabase.viewmodels.BusScheduleViewModel
import com.example.myroomdatabase.viewmodels.BusScheduleViewModelFactory
import kotlinx.coroutines.launch

class FullScheduleFragment : Fragment() {

    private val viewModel: BusScheduleViewModel by activityViewModels{
        BusScheduleViewModelFactory((activity?.application as BusScheduleApplication).database.scheduleDao())
    }

    private var _binding: FragmentFullScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val busStopAdapter = BusStopAdapter {
            val action = FullScheduleFragmentDirections
                .actionFullScheduleFragmentToStopScheduleFragment(
                    it.stopName
                )
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = busStopAdapter

        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect() {
                busStopAdapter.submitList(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}