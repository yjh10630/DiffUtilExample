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
import java.util.*
import java.util.concurrent.TimeUnit

typealias GetCurrentMainListData = (() -> MutableList<MainDataSet>?)
class MainViewModel: BaseViewModel() {

    private var mainAllList: MutableList<MainDataSet>? = null
    var getCurrentMainListData: GetCurrentMainListData? = null

    private val _mainLivedata: MutableLiveData<Pair<MutableList<MainDataSet>?, DiffUtil.DiffResult>> = MutableLiveData()
    val mainLivedata: LiveData<Pair<MutableList<MainDataSet>?, DiffUtil.DiffResult>>
        get() = _mainLivedata

    fun searchKeywordGetData(editTextString: CharSequence) {
        compositeDisposable.clear()
        Observable.create<CharSequence> { emitter -> emitter.onNext(editTextString) }
            .debounce(200L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .map(::createViewSearchKeywordView)
            .map(::calculateDiffUtilResult)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _mainLivedata.value = it
            }, {
                _mainLivedata.value = null
            })
            .addTo(compositeDisposable)
    }

    private fun createViewSearchKeywordView(txt: CharSequence): MutableList<MainDataSet>? {
        if (txt.isEmpty()) return mainAllList?.map { it.copy() }?.toMutableList()

        var items = mainAllList?.map { it.copy() }?.toMutableList()?.map { it.data as? ResponseSampleDataInfo }
            ?.filter { it?.name?.toLowerCase(Locale.getDefault())?.contains(txt.toString().toLowerCase(Locale.ROOT)) ?: false }
            ?.map { MainDataSet(ViewTypeConst.ITEM, it) }
            ?.toMutableList()

        if (items.isNullOrEmpty()) {
            items = mutableListOf(
                MainDataSet(ViewTypeConst.SEARCH_EMPTY, null)
            )
        }

        return items

    }

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
        mainAllList = modules.map { it.copy() }.toMutableList()
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