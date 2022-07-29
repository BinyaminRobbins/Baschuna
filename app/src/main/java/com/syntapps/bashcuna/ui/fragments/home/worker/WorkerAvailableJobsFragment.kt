package com.syntapps.bashcuna.ui.fragments.home.worker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.syntapps.bashcuna.R

class WorkerAvailableJobsFragment : Fragment() {

    private lateinit var viewpager2: ViewPager2
    private lateinit var counterText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worker_available_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager2 = view.findViewById(R.id.availableJobsViewpager)
        counterText = view.findViewById(R.id.counter_text)

        viewpager2.adapter = AvailableJobsAdapter()

    }

    inner class AvailableJobsAdapter :
        RecyclerView.Adapter<AvailableJobsAdapter.JobsViewHolder>() {

        inner class JobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun setData(position: Int) {

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
            val v =
                LayoutInflater.from(parent.context).inflate(R.layout.available_job_item, parent, false)
            return JobsViewHolder(v)
        }

        override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
            holder.setData(position)
        }

        override fun getItemCount(): Int {
            return 0
        }
    }
}