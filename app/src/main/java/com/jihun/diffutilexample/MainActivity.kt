package com.jihun.diffutilexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jihun.diffutilexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val rootBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)

        initView()
        initRecyclerView()
        initViewModel()

    }

    private fun initView() {
        with (rootBinding) {

        }
    }

    private fun initRecyclerView() {
        rootBinding.mainList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MainListAdapter()
        }
    }

    private fun initViewModel() {

    }
}