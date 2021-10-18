package com.jihun.diffutilexample.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jihun.diffutilexample.databinding.ActivityMainBinding
import com.jihun.diffutilexample.util.hideKeyboard

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initEditView()
        initRecyclerView()
        initViewModel()
    }

    private fun initEditView() {

    }

    private fun initViewModel() {
        viewModel.getCurrentMainListData = { getMainListAdapter()?.items?.map { it.copy() }?.toMutableList() }
        viewModel.mainLivedata.observe(this, Observer {
            showLoadingBar(false)
            getMainListAdapter()?.submitList(it.first, it.second)
        })
        showLoadingBar(true)
        viewModel.getData()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MainListAdapter()
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val itemIdx = (layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
                    showMoveTopBtn(!(itemIdx == 0 || itemIdx == null || adapter?.itemCount == 0))
                }
            })

            setOnTouchListener { v, event ->
                hideKeyboard(binding.etSearch)
                false
            }
        }
    }

    private fun getMainListAdapter() = binding.recyclerView.adapter as? MainListAdapter
    private fun showMoveTopBtn(isShow: Boolean) { if (isShow) binding.moveToTop.show() else binding.moveToTop.hide() }
    private fun showLoadingBar(isShow: Boolean) { binding.progressBar.visibility = if (isShow) View.VISIBLE else View.GONE}
}