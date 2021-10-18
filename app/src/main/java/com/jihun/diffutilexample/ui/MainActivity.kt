package com.jihun.diffutilexample.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jihun.diffutilexample.ViewTypeConst
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
        with(binding) {
            etSearch.apply {
                setOnKeyListener { _, keyCode, event ->
                    if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        hideKeyboard(this)
                        return@setOnKeyListener true
                    }
                    return@setOnKeyListener false
                }

                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) { }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        s?.let {
                            when (it.isEmpty()) {
                                true -> { binding.searchRemove.visibility = View.GONE }
                                false -> { binding.searchRemove.visibility = View.VISIBLE }
                            }
                            viewModel.searchKeywordGetData(it)
                        }
                    }
                })
            }
            searchRemove.setOnClickListener { removeEditText() }
            moveToTop.setOnClickListener { binding.recyclerView.scrollToPosition(0) }
        }
    }

    private fun removeEditText() {
        with (binding) {
            if (etSearch.text.isEmpty()) return
            etSearch.text.clear()
            searchRemove.visibility = View.GONE
        }
    }

    private fun initViewModel() {
        viewModel.getCurrentMainListData = { getMainListAdapter()?.items?.map { it.copy() }?.toMutableList() }
        viewModel.mainLivedata.observe(this, Observer {
            showLoadingBar(false)
            getMainListAdapter()?.submitList(it.first, it.second)
            val count = if (it.first?.getOrNull(0)?.viewType == ViewTypeConst.SEARCH_EMPTY) 0 else (it.first?.count() ?: 0)
            setCount(count)
        })
        showLoadingBar(true)
        viewModel.getData()
    }

    private fun setCount(count: Int) {
        binding.count.text = "$count 개"
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MainListAdapter().apply {
                registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        scrollToPosition(0)
                    }
                })
            }
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