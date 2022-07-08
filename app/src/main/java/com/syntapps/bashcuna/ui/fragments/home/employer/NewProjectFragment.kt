package com.syntapps.bashcuna.ui.fragments.home.employer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.other.adapters.FieldsOptionsAdapter
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class NewProjectFragment : Fragment(), FieldsOptionsAdapter.OnFieldSelectedListener {

    private val viewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var fieldsRecyclerView: RecyclerView
    private lateinit var fieldsOptionsAdapter: FieldsOptionsAdapter
    private lateinit var fieldOptions: List<WorkHireField>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fieldsRecyclerView = view.findViewById(R.id.fieldRv)
        fieldOptions = viewModel.getFieldOptions()
        fieldsOptionsAdapter = FieldsOptionsAdapter(requireContext(), fieldOptions, this)
        fieldsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fieldsRecyclerView.adapter = fieldsOptionsAdapter
    }

    override fun onFieldSelected(field: WorkHireField) {
        TODO("Not yet implemented")
    }
}