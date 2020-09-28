package com.cesarsosa.mobiletest.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cesarsosa.mobiletest.App
import com.cesarsosa.mobiletest.MainActivity
import com.cesarsosa.mobiletest.R
import com.cesarsosa.mobiletest.databinding.ActivitySplashBinding
import com.cesarsosa.mobiletest.di.injectViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SplashViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).activityInjector().inject(this)

        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_splash)


        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        viewModel = injectViewModel(viewModelFactory)

        subscribeUi()
    }
    private fun subscribeUi() {
        viewModel.initSplashScreen()
        val observer = Observer<String> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        viewModel.liveData.observe(this, observer)
    }
}