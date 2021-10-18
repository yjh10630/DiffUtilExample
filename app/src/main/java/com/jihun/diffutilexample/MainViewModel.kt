package com.jihun.diffutilexample

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jihun.diffutilexample.api.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun getData() {
        RetrofitClient.getInstance().getService().requestSampleApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("####", "Success")
            }, {
                Log.d("####", "fail >> ${it.message}")
            })
            .addTo(compositeDisposable)
    }

}