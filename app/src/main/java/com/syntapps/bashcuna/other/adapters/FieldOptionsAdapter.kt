package com.syntapps.bashcuna.other.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.ui.fragments.auth.AuthDetailsThree
import com.syntapps.bashcuna.ui.fragments.home.employer.newproject.NewProjectTypeFragment

class FieldsOptionsAdapter() : RecyclerView.Adapter<FieldsOptionsAdapter.MyViewHolder>() {

    private lateinit var mContext: Context
    private lateinit var favoriteFields: List<WorkHireField>
    private var authDetailsThree: AuthDetailsThree? = null
    private var newProjectTypeFragment: NewProjectTypeFragment? = null

    private var prevCardView: CardView? = null
    private var prevTextView: TextView? = null
    private var prevIcon: ImageView? = null
    private var prevField: WorkHireField? = null

    constructor(
        mContext: Context,
        favoriteFields: List<WorkHireField>,
        mFragment: AuthDetailsThree
    ) : this() {
        this.mContext = mContext
        this.favoriteFields = favoriteFields
        this.authDetailsThree = mFragment
    }

    constructor(
        mContext: Context,
        favoriteFields: List<WorkHireField>,
        mFragment: NewProjectTypeFragment
    ) : this() {
        this.mContext = mContext
        this.favoriteFields = favoriteFields
        this.newProjectTypeFragment = mFragment
    }


    interface OnFieldSelectedListener {
        fun onFieldSelected(field: WorkHireField)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView = itemView.findViewById<CardView>(R.id.cardView)
        private val icon = itemView.findViewById<ImageView>(R.id.icon_view)
        private val text = itemView.findViewById<TextView>(R.id.text_view)

        fun setData(field: WorkHireField) {
            Glide.with(mContext)
                .load(field.fieldIcon)
                .into(icon)
            text.text = field.fieldName

            if (field.isSelected) {
                //initialize selected field/s
                selectField(field)
            } else unselectField(field)

            cardView.setOnClickListener {
                if (field.isSelected) {
                    unselectField(field)
                } else {
                    selectField(field)
                }
            }
        }

        private fun unselectField(field: WorkHireField) {
            //unselect the field
            field.isSelected = false
            cardView.setCardBackgroundColor(mContext.getColor(R.color.background_light_primary))
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
        }

        private fun selectField(field: WorkHireField) {
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
            if (newProjectTypeFragment != null) {
                if (prevCardView != null && prevTextView != null && prevField != null && prevIcon != null) {
                    prevCardView!!.setCardBackgroundColor(mContext.getColor(R.color.background_light_primary))
                    prevTextView!!.setTextColor(mContext.getColor(R.color.color_accent))
                    ImageViewCompat.setImageTintList(
                        prevIcon!!,
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                mContext,
                                R.color.color_accent
                            )
                        )
                    )
                    prevField!!.isSelected = false
                }
                prevCardView = cardView
                prevTextView = text
                prevIcon = icon
                prevField = field
            }
            //select the field
            if (authDetailsThree != null) authDetailsThree!!.onFieldSelected(field)
            else newProjectTypeFragment?.onFieldSelected(field)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.work_field_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(favoriteFields[position])
    }

    override fun getItemCount(): Int {
        return favoriteFields.size
    }

}
