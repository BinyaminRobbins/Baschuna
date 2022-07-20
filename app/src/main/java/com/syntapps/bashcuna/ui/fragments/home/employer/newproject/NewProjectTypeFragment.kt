package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.other.adapters.FieldsOptionsAdapter
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class NewProjectTypeFragment : Fragment(), FieldsOptionsAdapter.OnFieldSelectedListener {

    private val viewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var fieldsRecyclerView: RecyclerView
    private lateinit var fieldsOptionsAdapter: FieldsOptionsAdapter
    private lateinit var fieldOptions: List<WorkHireField>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fieldsRecyclerView = view.findViewById(R.id.fieldRv)
        fieldOptions = viewModel.getFieldOptions()
        if (viewModel.newJobOffer.jobFieldCode != null) {
            for (option in fieldOptions) {
                if (option.fieldName == viewModel.newJobOffer.jobFieldCode) {
                    option.isSelected = true
                    break
                }
            }
        }
        fieldsOptionsAdapter = FieldsOptionsAdapter(requireContext(), fieldOptions, this)
        fieldsRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        fieldsRecyclerView.adapter = fieldsOptionsAdapter

    }

    override fun onFieldSelected(field: WorkHireField) {
        viewModel.newJobOffer.jobFieldCode = field.fieldName
    }
}