package com.kotlin.mvvm.kt.presentation.car

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mvvm.R
import com.kotlin.mvvm.databinding.ActivityCarListBinding
import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.domain.models.cars.Car
import com.kotlin.mvvm.kt.presentation.base.MyDataBindingComponent
import com.kotlin.mvvm.kt.presentation.car.adapter.CarListAdapter
import com.kotlin.mvvm.kt.presentation.car.viewmodel.CarListActivityViewModel
import com.kotlin.mvvm.kt.utility.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarListBinding
    private lateinit var dataBindingComponent: DataBindingComponent
    private val viewModel: CarListActivityViewModel by viewModels()
    private var carListAdapter: CarListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initAdapter()
        fetchCarsData()
        observeCarsData()
    }

    private fun initUI() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        binding = ActivityCarListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        dataBindingComponent = MyDataBindingComponent(this)

        binding.srlCars.setOnRefreshListener {
            fetchCarsData()
        }
    }

    private fun initAdapter() {
        carListAdapter = CarListAdapter(dataBindingComponent, this)
        val recyclerLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvCars.apply {
            layoutManager = recyclerLayoutManager
            adapter = carListAdapter
            setHasFixedSize(false)
        }
    }

    private fun fetchCarsData() {
        viewModel.getCars(AppUtils.isOnline(this))
    }

    private fun observeCarsData() {
        viewModel.carResponse.observe(this) { resultData ->
            when (resultData) {
                is ResultData.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultData.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.srlCars.isRefreshing = false

                    resultData.data?.let { carList ->
                        onGetCarListResultSuccess(carList)
                    } ?: run {
                        println("Parsing issue")
                    }

                }
                is ResultData.Failed -> {
                    binding.progressBar.visibility = View.GONE
                    binding.srlCars.isRefreshing = false
                    resultData.message?.let { onGetCarListResultFailure(it) }

                }
                is ResultData.Exception -> {
                    binding.progressBar.visibility = View.GONE
                    binding.srlCars.isRefreshing = false
                    onGetCarListResultFailure("Exception Found")
                }
            }

        }
    }

    private fun onGetCarListResultSuccess(result: List<Car?>) {
        result.let {
            if (it.isNotEmpty()) {
                binding.rvCars.visibility = View.VISIBLE
                carListAdapter?.submitList(it)
                binding.rvCars.let {
                    binding.rvCars.adapter = carListAdapter
                }
            }
        }
    }

    private fun onGetCarListResultFailure(message: String) {
        binding.rvCars.visibility = View.GONE
        binding.tvZeroData.visibility = View.VISIBLE
        binding.tvZeroData.text = message
    }

}