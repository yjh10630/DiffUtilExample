package com.jihun.diffutilexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

typealias GetCurrentData = (() -> MutableList<SelectItem.Item>?)

class MainViewModel: ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    val mainResponseLiveData: MutableLiveData<MutableList<SelectItem.Item>?> = MutableLiveData()
    var getCurrentData: GetCurrentData? = null

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun requestData() {
        compositeDisposable.add(
            Observable.fromCallable { createData() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mainResponseLiveData.value = it
                    }, {
                        mainResponseLiveData.value = null
                    }
                )
        )
    }

    fun setShuffle() {
        val dataSet = getCurrentData?.invoke()?.map { it.copy() }?.toMutableList()
        dataSet?.shuffle()
        mainResponseLiveData.value = dataSet
    }

    fun setChecker(isAllEnable: Boolean) {
        val dataSet = getCurrentData?.invoke()?.map { it.copy() }?.toMutableList()
        dataSet?.forEach {
            it.isSelected = isAllEnable
        }
        mainResponseLiveData.value = dataSet
    }

    private fun createData(): MutableList<SelectItem.Item> =
        mutableListOf(
            SelectItem.Item(name = "가나다", isSelected = false),
            SelectItem.Item(name = "Android", isSelected = false),
            SelectItem.Item(name = "iOS", isSelected = false),
            SelectItem.Item(name = "ViewModel", isSelected = false),
            SelectItem.Item(name = "Rxjava", isSelected = false),
            SelectItem.Item(name = "Observable", isSelected = false),
            SelectItem.Item(name = "갤럭시노트20+", isSelected = false),
            SelectItem.Item(name = "배아프다", isSelected = false),
        )
}