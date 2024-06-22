package com.takumi.employee.biz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.takumi.employee.biz.databinding.FragmentEmployeesBinding
import com.takumi.employee.ui.bean.EmployeeInfo
import com.takumi.employee.ui.dialog.showEmployeeDialog
import com.takumi.employee.ui.recycleview.EmployeeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesFragment : Fragment() {

    private lateinit var binding: FragmentEmployeesBinding

    private val listViewModel by viewModels<EmployeeListViewModel>()
    private val notifyViewModel by activityViewModels<EmployeeNotifyViewModel>()

    private var listAdapter: EmployeeAdapter = EmployeeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentEmployeesBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addObserve()
        loadData()
    }

    private fun initView() {
        val onItemDeleteClick = { _: View, _: Int, data: EmployeeInfo ->
            listViewModel.deleteData(data)
        }

        val onItemUpdateClick = { view: View, _: Int, data: EmployeeInfo ->
            view.showEmployeeDialog(employeeInfo = data) { info ->
                listViewModel.updateData(info)
            }
        }

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter.also {
                it.setOnDeleteClick(onItemDeleteClick)
                it.setOnUpdateClick(onItemUpdateClick)
            }
        }
    }

    private fun addObserve() {
        listViewModel.listLiveData.observe(viewLifecycleOwner) {
            val context = context ?: return@observe
            listAdapter.submitList(it.dataList.toList())
            Toast.makeText(context, getString(it.action.messageStringResID), Toast.LENGTH_SHORT)
                .show()
        }

        listViewModel.statisticsLiveData.observe(viewLifecycleOwner) {
            val view = view ?: return@observe
            Snackbar.make(
                view,
                "Average Age: ${it.averageAge} \n Male: ${it.malePercentage}  Female: ${it.femalePercentage}",
                Snackbar.LENGTH_LONG
            ).show()
        }

        notifyViewModel.notifyInstallEmployeeLiveData.observe(viewLifecycleOwner) {
            context?.showEmployeeDialog { info ->
                listViewModel.installData(info)
            }
        }

        notifyViewModel.notifyShowStatisticsLiveData.observe(viewLifecycleOwner) {
            listViewModel.showStatisticsData()
        }
    }

    private fun loadData() {
        listViewModel.loadDataList()
    }


}