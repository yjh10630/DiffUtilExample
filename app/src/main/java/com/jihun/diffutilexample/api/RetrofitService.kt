package com.jihun.diffutilexample.api

import com.jihun.diffutilexample.data.ResponseSampleDataInfo
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitService {
    @GET("sampleApi") fun requestSampleApi(): Observable<List<ResponseSampleDataInfo>>
}