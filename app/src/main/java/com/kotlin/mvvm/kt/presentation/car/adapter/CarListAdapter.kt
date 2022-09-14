package com.kotlin.mvvm.kt.presentation.car.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.kotlin.mvvm.R
import com.kotlin.mvvm.databinding.CarItemLayoutBinding
import com.kotlin.mvvm.kt.domain.models.cars.Car
import com.kotlin.mvvm.kt.presentation.base.BaseListAdapter
import com.kotlin.mvvm.kt.utility.AppUtils.Companion.convertToDateFormat
import com.kotlin.mvvm.kt.utility.AppUtils.Companion.loadImage

class CarListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val context: Context
) :
    BaseListAdapter<Car, CarItemLayoutBinding>(diffCallback = diffCallback) {
    override fun createBinding(parent: ViewGroup, viewType: Int): CarItemLayoutBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.car_item_layout,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: CarItemLayoutBinding, item: Car, position: Int) {
        loadImage(context, item.image, binding.carLogo)
        item.title.let { binding.carTitle.text = it }
        item.ingress.let { binding.carIngress.text = it }
        item.dateTime.let { binding.carDate.text = convertToDateFormat(it, context) }
    }
}


private val diffCallback: DiffUtil.ItemCallback<Car> =
    object : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(
            oldItem: Car,
            newItem: Car,
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: Car,
            newItem: Car,
        ): Boolean {
            return false
        }
    }