package com.jihun.diffutilexample.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.jihun.diffutilexample.ViewTypeConst
import com.jihun.diffutilexample.api.RetrofitClient
import com.jihun.diffutilexample.data.MainDataSet
import com.jihun.diffutilexample.data.ResponseSampleDataInfo
import com.jihun.diffutilexample.ui.base.BaseViewModel
import com.jihun.diffutilexample.util.diffUtilResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

typealias GetCurrentMainListData = (() -> MutableList<MainDataSet>?)
class MainViewModel: BaseViewModel() {

    var getCurrentMainListData: GetCurrentMainListData? = null

    private val _mainLivedata: MutableLiveData<Pair<MutableList<MainDataSet>?, DiffUtil.DiffResult>> = MutableLiveData()
    val mainLivedata: LiveData<Pair<MutableList<MainDataSet>?, DiffUtil.DiffResult>>
        get() = _mainLivedata

    fun getData() {
        RetrofitClient.getInstance().getService().requestSampleApi()
            .subscribeOn(Schedulers.io())
            .map(::createViewEntity)
            .map(::calculateDiffUtilResult)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("####", "Success")
                _mainLivedata.value = it
            }, {
                Log.d("####", "fail >> ${it.message}")
                _mainLivedata.value = null
            })
            .addTo(compositeDisposable)
    }

    private fun createViewEntity(data: List<ResponseSampleDataInfo>): MutableList<MainDataSet> {
        val modules = mutableListOf<MainDataSet>()
        data.forEach {
            modules.add(MainDataSet(ViewTypeConst.ITEM, it))
        }
        return modules
    }

    private fun calculateDiffUtilResult(data: MutableList<MainDataSet>): Pair<MutableList<MainDataSet>?, DiffUtil.DiffResult> {
        val getCurrentList = getCurrentMainListData?.invoke()

        val result = diffUtilResult(
            oldList = getCurrentList,
            newList = data,
            itemCompare = { o, n -> o?.viewType == n?.viewType },
            contentCompare = { o, n -> o == n }
        )
        return Pair(data, result)
    }

}