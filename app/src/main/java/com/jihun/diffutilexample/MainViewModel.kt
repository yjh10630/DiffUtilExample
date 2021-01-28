package com.jihun.diffutilexample

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel: ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}