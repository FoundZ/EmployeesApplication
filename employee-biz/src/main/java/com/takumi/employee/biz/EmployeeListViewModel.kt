package com.takumi.employee.biz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takumi.employee.biz.bean.EmployeeNotifyAction
import com.takumi.employee.biz.bean.EmployeeStatisticsData
import com.takumi.employee.biz.bean.NotifyAction
import com.takumi.employee.biz.utils.toEmployeeInfo
import com.takumi.employee.biz.utils.toUser
import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.ui.bean.EmployeeInfo
import com.takumi.employee.ui.bean.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val repository: EmployeesRepository
) : ViewModel() {

    private var _listLiveData = MutableLiveData<EmployeeNotifyAction>()

    val listLiveData: LiveData<EmployeeNotifyAction> = _listLiveData

    private var _statisticsLiveData = MutableLiveData<EmployeeStatisticsData>()

    val statisticsLiveData = _statisticsLiveData

    fun loadDataList() {
        viewModelScope.launch(Dispatchers.IO) {
            val getEmployeesDeferred = async {
                getDataList()
            }
            val dataList = getEmployeesDeferred.await()
            _listLiveData.postValue(
                EmployeeNotifyAction(
                    dataList = dataList, action = NotifyAction.Show
                )
            )
        }
    }

    private suspend fun getDataList(): MutableList<EmployeeInfo> {
        val dataList = repository.getAllEmployees().map {
            it.toEmployeeInfo()
        }
        return dataList.toMutableList()
    }


    fun updateData(info: EmployeeInfo) {
        viewModelScope.launch(Dispatchers.IO) {

            val updateEmployeeDeferred = async {
                repository.updateEmployee(info.toUser())
            }

            val notifyEmployeeListDeferred = async {
                val originDataList = _listLiveData.value?.dataList ?: mutableListOf()
                originDataList.find {
                    info.id == it.id
                }?.let { oldData ->
                    originDataList.indexOf(oldData)
                }?.takeIf { index ->
                    index > -1
                }?.also { index ->
                    originDataList[index] = info
                }
                originDataList
            }

            updateEmployeeDeferred.await()
            val dataList = notifyEmployeeListDeferred.await()

            _listLiveData.postValue(
                EmployeeNotifyAction(
                    dataList = dataList, action = NotifyAction.Update
                )
            )
        }
    }

    fun deleteData(employeeInfo: EmployeeInfo) {
        viewModelScope.launch(Dispatchers.IO) {

            val deleteEmployeeDeferred = async {
                repository.deleteEmployee(employeeInfo.toUser())
            }

            val notifyEmployeeListDeferred = async {
                val originDataList = _listLiveData.value?.dataList ?: mutableListOf()
                originDataList.removeIf {
                    it.id == employeeInfo.id
                }
                originDataList
            }

            deleteEmployeeDeferred.await()
            val dataList = notifyEmployeeListDeferred.await()


            _listLiveData.postValue(
                EmployeeNotifyAction(
                    dataList = dataList, action = NotifyAction.Delete
                )
            )
        }
    }

    fun installData(info: EmployeeInfo) {
        viewModelScope.launch(Dispatchers.IO) {

            val installEmployeeDeferred = async {
                repository.addEmployees(info.toUser())
                getDataList()
            }

            val dataList = installEmployeeDeferred.await()

            _listLiveData.postValue(
                EmployeeNotifyAction(
                    dataList = dataList, action = NotifyAction.Install
                )
            )
        }
    }


    fun showStatisticsData() {
        viewModelScope.launch(Dispatchers.IO) {
            val statisticsDeferred = async {
                val averageAge = repository.getAverageAge()
                val sumFemale = repository.getTotalGenderForType(Gender.Female.value)
                val sumMale = repository.getTotalGenderForType(Gender.Male.value)
                val sumGender = repository.getTotalGender()

                val formatAverageAge =
                    BigDecimal.valueOf(averageAge).setScale(1, RoundingMode.HALF_UP)
                        .toPlainString()

                var femalePercentage = "0%"
                var malePercentage = "0%"
                if (sumGender != 0.toDouble()) {
                    femalePercentage = BigDecimal.valueOf(sumFemale)
                        .divide(BigDecimal.valueOf(sumGender), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal(100)).stripTrailingZeros().toPlainString() + "%"

                    malePercentage = BigDecimal.valueOf(sumMale)
                        .divide(BigDecimal.valueOf(sumGender), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal(100)).stripTrailingZeros().toPlainString() + "%"
                }

                EmployeeStatisticsData(
                    averageAge = formatAverageAge,
                    femalePercentage = femalePercentage,
                    malePercentage = malePercentage,
                )
            }

            val result = statisticsDeferred.await()

            statisticsLiveData.postValue(result)
        }
    }
}