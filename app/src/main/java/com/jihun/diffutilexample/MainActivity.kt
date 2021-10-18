package com.jihun.diffutilexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jihun.diffutilexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val rootBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getData()



    }
}