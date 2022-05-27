package com.example.gardenguru.ui.customview.card.weather

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.gardenguru.R
import com.example.gardenguru.databinding.ItemWheatherConditionBinding
import com.example.gardenguru.utils.Extensions.setDrawable
import com.example.gardenguru.utils.Extensions.setString


class WheatherConditionItem(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding: ItemWheatherConditionBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemWheatherConditionBinding.inflate(inflater, this)
        val a = context.obtainStyledAttributes(attrs, R.styleable.WheatherConditionItem, 0, 0)
        initView(a.getString(R.styleable.WheatherConditionItem_condition))
        a.recycle()
    }

    private fun initView(type: String?) {
        when (type?.toInt()) {
            1 -> {
                binding.image.setDrawable(R.drawable.ic_temperature)
                binding.textView1.setString(R.string.temperature)
            }
            2 -> {
                binding.image.setDrawable(R.drawable.ic_watering_2)
                binding.textView1.setString(R.string.watering)
            }
            3 -> {
                binding.image.setDrawable(R.drawable.ic_lighting)
                binding.textView1.setString(R.string.lighting)
            }
        }
    }

    fun changeImage(drawable: Drawable) {
        binding.image.setImageDrawable(drawable)
    }

    fun setTextInfo(info: String) {
        binding.textView2.text = info
    }

}