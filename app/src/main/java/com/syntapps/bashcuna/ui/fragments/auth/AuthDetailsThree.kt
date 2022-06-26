package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthDetailsThree : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var explanationText: TextView
    private lateinit var fieldsRecyclerView: RecyclerView

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
        viewModel.getCurrentUser()?.let {
            explanationText.text = when (it.getRole()) {
                it.ROLE_WORKER -> {
                    getString(R.string.choose_work)
                }
                it.ROLE_EMPLOYER -> {
                    getString(R.string.choose_hire_for)
                }
                else -> {
                    return
                }
            }
        }

        fieldsRecyclerView = view.findViewById(R.id.fields_options)


    }

    inner class FieldsOptionsAdapter(
        private val fields: List<WorkHireField>
    ) : RecyclerView.Adapter<FieldsOptionsAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            return fields.size
        }

    }


}