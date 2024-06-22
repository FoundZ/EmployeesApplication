package com.takumi.employee.biz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class EmployeeNotifyViewModel @Inject constructor() : ViewModel() {

    private val _notifyInstallEmployeeLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val notifyInstallEmployeeLiveData: LiveData<Boolean> = _notifyInstallEmployeeLiveData

    private val _notifyShowStatisticsLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val notifyShowStatisticsLiveData: LiveData<Boolean> = _notifyShowStatisticsLiveData

    fun notifyInstall() {
        val value = _notifyInstallEmployeeLiveData.value ?: false
        _notifyInstallEmployeeLiveData.postValue(value.not())
    }

    fun notifyShowStatistics() {
        val value = _notifyShowStatisticsLiveData.value ?: false
        _notifyShowStatisticsLiveData.postValue(value.not())
    }
}