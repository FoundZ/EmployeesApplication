package com.takumi.employee.ui.recycleview

import androidx.recyclerview.widget.RecyclerView
import com.takumi.employee.ui.R
import com.takumi.employee.ui.bean.EmployeeInfo
import com.takumi.employee.ui.databinding.ItemEmployeeViewBinding
import com.takumi.employee.ui.utils.setTextValue

class EmployeeViewHolder(
    private val binding: ItemEmployeeViewBinding,
    private val onUpdateClick: OnUpdateClick?,
    private val onDeleteClick: OnDeleteClick?,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: EmployeeInfo) {
        binding.idTextView.setTextValue(R.string.employee_id, data.id.toString())
        binding.nameTextView.setTextValue(R.string.employee_name, data.name)
        binding.firstNameTextView.setTextValue(R.string.employee_first_name, data.firstName)
        binding.ageTextView.setTextValue(R.string.employee_age, data.age.toString())
        binding.genderTextView.setTextValue(R.string.employee_gender, data.gender.toString())

        binding.updateBtn.setOnClickListener {
            onUpdateClick?.invoke(
                binding.updateBtn, adapterPosition, data
            )
        }
        binding.deleteBtn.setOnClickListener {
            onDeleteClick?.invoke(
                binding.updateBtn, adapterPosition, data
            )
        }
    }
}