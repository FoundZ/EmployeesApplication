package com.takumi.employee.ui.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.takumi.employee.ui.bean.EmployeeInfo
import com.takumi.employee.ui.databinding.ItemEmployeeViewBinding

typealias OnDeleteClick = (view: View, position: Int, data: EmployeeInfo) -> Unit
typealias OnUpdateClick = (view: View, position: Int, data: EmployeeInfo) -> Unit

class EmployeeAdapter : ListAdapter<EmployeeInfo, EmployeeViewHolder>(DiffCallback) {

    private var onDeleteClick: OnDeleteClick? = null
    private var onUpdateClick: OnUpdateClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            ItemEmployeeViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onUpdateClick,
            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data)
    }

    fun setOnDeleteClick(onDeleteClick: OnDeleteClick) {
        this.onDeleteClick = onDeleteClick
    }

    fun setOnUpdateClick(onUpdateClick: OnUpdateClick) {
        this.onUpdateClick = onUpdateClick
    }

    companion object DiffCallback : DiffUtil.ItemCallback<EmployeeInfo>() {
        override fun areItemsTheSame(oldItem: EmployeeInfo, newItem: EmployeeInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EmployeeInfo, newItem: EmployeeInfo): Boolean {
            return oldItem == newItem
        }
    }


}