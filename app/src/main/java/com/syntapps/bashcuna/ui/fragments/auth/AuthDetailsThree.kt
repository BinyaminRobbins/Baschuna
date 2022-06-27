package com.syntapps.bashcuna.ui.fragments.auth

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthDetailsThree : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var explanationText: TextView
    private lateinit var fieldsRecyclerView: RecyclerView
    private lateinit var fieldsOptionsAdapter: FieldsOptionsAdapter

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
            when (it.getRole()) {
                it.ROLE_WORKER -> {
                    explanationText.text = getString(R.string.choose_work)
                }
                it.ROLE_EMPLOYER -> {
                    explanationText.text = getString(R.string.choose_hire_for)
                }
            }
        }

        fieldsRecyclerView = view.findViewById(R.id.fields_options)
        fieldsOptionsAdapter = FieldsOptionsAdapter(requireContext(), viewModel.getFieldOptions())
        fieldsRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                2 /*aka num of columns*/,
                GridLayoutManager.VERTICAL,
                false
            )
        fieldsRecyclerView.adapter = fieldsOptionsAdapter

    }

    inner class FieldsOptionsAdapter(
        private val mContext: Context,
        private val fields: List<WorkHireField>
    ) : RecyclerView.Adapter<FieldsOptionsAdapter.MyViewHolder>() {

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val cardView = itemView.findViewById<CardView>(R.id.cardView)
            private val icon = itemView.findViewById<ImageView>(R.id.icon_view)
            private val text = itemView.findViewById<TextView>(R.id.text_view)
            fun setData(field: WorkHireField) {
                Glide.with(mContext)
                    .load(field.fieldIcon)
                    .into(icon)
                text.text = field.fieldName

                cardView.setOnClickListener {
                    if (field.isSelected) {
                        //unselect the field
                        field.isSelected = false
                        cardView.setCardBackgroundColor(mContext.getColor(R.color.background_light_secondary))
                        text.setTextColor(mContext.getColor(R.color.color_accent))
                        ImageViewCompat.setImageTintList(
                            icon,
                            ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    mContext,
                                    R.color.color_accent
                                )
                            )
                        )
                    } else {
                        //select the field
                        field.isSelected = true
                        cardView.setCardBackgroundColor(mContext.getColor(R.color.color_accent))
                        text.setTextColor(mContext.getColor(R.color.background_light_primary))
                        ImageViewCompat.setImageTintList(
                            icon,
                            ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    mContext,
                                    R.color.background_light_primary
                                )
                            )
                        )
                    }
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(mContext).inflate(R.layout.work_field_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.setData(fields[position])
        }

        override fun getItemCount(): Int {
            return fields.size
        }

    }
}