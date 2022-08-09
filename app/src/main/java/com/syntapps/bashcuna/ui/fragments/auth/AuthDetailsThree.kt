package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.CurrentUser
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.other.adapters.FieldsOptionsAdapter
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthDetailsThree : Fragment(), FieldsOptionsAdapter.OnFieldSelectedListener {

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var explanationText: TextView
    private lateinit var fieldsRecyclerView: RecyclerView
    private lateinit var fieldsOptionsAdapter: FieldsOptionsAdapter
    private lateinit var favoriteFields: List<WorkHireField>

    private fun updateFavoriteFields() {
        authViewModel.updateUserFavoriteFields(favoriteFields)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_details_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        explanationText = view.findViewById(R.id.explanation_text)
        authViewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let {
                when (it.role) {
                    CurrentUser.ROLE_WORKER -> {
                        explanationText.text = getString(R.string.choose_work)
                    }
                    CurrentUser.ROLE_EMPLOYER -> {
                        explanationText.text = getString(R.string.choose_hire_for)
                    }
                }
            }
        }

        fieldsRecyclerView = view.findViewById(R.id.fields_options)
        favoriteFields = authViewModel.getFieldOptions()
        fieldsOptionsAdapter = FieldsOptionsAdapter(requireContext(), favoriteFields, this)
        fieldsRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                2 /*aka num of columns*/,
                GridLayoutManager.VERTICAL,
                false
            )
        fieldsRecyclerView.adapter = fieldsOptionsAdapter
    }

    override fun onFieldSelected(field: WorkHireField) {
        updateFavoriteFields()
    }

}