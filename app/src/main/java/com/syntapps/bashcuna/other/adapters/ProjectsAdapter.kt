package com.syntapps.bashcuna.other.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syntapps.bashcuna.R

class ProjectsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class mViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.project_card_item, parent, false)
        return mViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}