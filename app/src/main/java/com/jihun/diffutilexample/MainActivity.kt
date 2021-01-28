package com.jihun.diffutilexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jihun.diffutilexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val rootBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)

        
    }
}