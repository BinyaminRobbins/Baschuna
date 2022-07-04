package com.syntapps.bashcuna.ui.fragments.home.worker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class ChartsFragment : Fragment() {

    private val viewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var workComingUp: TextView
    private lateinit var earningsThisMonth: TextView
    private lateinit var workCompleted: TextView
    private lateinit var currentRating: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}