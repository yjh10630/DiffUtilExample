package com.jihun.diffutilexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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
            allEnableBtn click {
                viewModel.setChecker(true)
            }
            allDisableBtn click {
                viewModel.setChecker(false)
            }
            shuffleBtn click {
                viewModel.setShuffle()
            }
            refreshBtn click {
                viewModel.requestData()
            }
        }
    }

    private fun initRecyclerView() {
        rootBinding.mainList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MainListAdapter()
        }
    }

    private fun getCurrentData() = (rootBinding.mainList.adapter as? MainListAdapter)?.items

    private fun initViewModel() {
        with (viewModel) {
            requestData()
            getCurrentData = this@MainActivity::getCurrentData
            mainResponseLiveData.observe(this@MainActivity, Observer {
                it?.let {
                    (rootBinding.mainList.adapter as? MainListAdapter)?.items = it
                } ?: run {
                    //TODO 데이터가 없을 때 노출 할 수 있는 페이지 만들기.
                    (rootBinding.mainList.adapter as? MainListAdapter)?.items = mutableListOf() // 우선 임시..
                }
            })
        }
    }
}