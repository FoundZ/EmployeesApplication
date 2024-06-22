package com.takumi.employee.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.takumi.employee.ui.R
import com.takumi.employee.ui.bean.EmployeeInfo
import com.takumi.employee.ui.bean.Gender
import com.takumi.employee.ui.databinding.DialogEmployeeEditBinding
import com.takumi.employee.ui.utils.showToast
import kotlin.random.Random

typealias OnDialogPositiveBtnClick = (employeeInfo: EmployeeInfo) -> Unit

fun View.showEmployeeDialog(employeeInfo: EmployeeInfo? = null, click: OnDialogPositiveBtnClick) {
    this.context.showEmployeeDialog(employeeInfo = employeeInfo, click = click)
}

fun Context.showEmployeeDialog(
    employeeInfo: EmployeeInfo? = null, click: OnDialogPositiveBtnClick
) {
    val binding = DialogEmployeeEditBinding.inflate(LayoutInflater.from(this), null, false)

    if (employeeInfo != null) {
        binding.bindData(employeeInfo)
    }

    binding.random.setOnClickListener {
        binding.randomData()
    }

    AlertDialog.Builder(this).setView(binding.root)
        .setPositiveButton(R.string.dialog_btn_ok) { _, _ ->
            if (binding.checkInputCompleted()) {
                val info = binding.getDataFromView()
                click.invoke(info)
            }
        }.setNeutralButton(R.string.dialog_btn_cancel) { dialog, _ ->
            dialog.dismiss()
        }.create().show()
}

private fun DialogEmployeeEditBinding.bindData(employeeInfo: EmployeeInfo) {
    idTitleTextView.visibility =  View.VISIBLE
    idTextView.visibility =  View.VISIBLE
    idTextView.text = employeeInfo.id?.toString()
    nameEditText.setText(employeeInfo.name)
    firstNameEditText.setText(employeeInfo.firstName)
    ageEditText.setText(employeeInfo.age.toString())
    genderRadioGroup.check(
        when (employeeInfo.gender) {
            Gender.Male -> R.id.maleRadioBtn
            Gender.Female -> R.id.femaleRadioBtn
        }
    )
}

private fun DialogEmployeeEditBinding.checkInputCompleted(): Boolean {
    val context = this.root.context
    val name = nameEditText.text.toString()
    if (name.isEmpty()) {
        name.showToast(context)
        return false
    }

    val firstName = firstNameTextView.text.toString()
    if (firstName.isEmpty()) {
        firstName.showToast(context)
        return false
    }

    val age = ageEditText.text.toString()
    if (age.isEmpty()) {
        age.showToast(context)
        return false
    }

    return true
}

private fun DialogEmployeeEditBinding.getDataFromView(): EmployeeInfo {
    return EmployeeInfo(
        id = if (idTextView.isVisible) {
            idTextView.text.toString().toInt()
        } else {
            null
        },
        name = nameEditText.text.toString(),
        firstName = firstNameEditText.text.toString(),
        age = ageEditText.text.toString().toInt(),
        gender = when (genderRadioGroup.checkedRadioButtonId) {
            R.id.maleRadioBtn -> Gender.Male
            R.id.femaleRadioBtn -> Gender.Female
            else -> Gender.Male
        }
    )
}

private fun DialogEmployeeEditBinding.randomData() {
    val firstNames = listOf("John", "Jane", "Emily", "Michael", "David", "Sophia", "James")
    val lastNames = listOf("Doe", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia")
    val age = Random.nextInt(22, 50)
    val gender = Random.nextBoolean()

    this.nameEditText.setText(lastNames.random())
    this.firstNameEditText.setText(firstNames.random())
    this.ageEditText.setText(age.toString())
    this.genderRadioGroup.check(
        if (gender) {
            R.id.maleRadioBtn
        } else {
            R.id.femaleRadioBtn
        }
    )
}