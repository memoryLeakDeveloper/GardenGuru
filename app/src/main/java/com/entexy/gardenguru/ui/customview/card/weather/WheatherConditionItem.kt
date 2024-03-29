package com.entexy.gardenguru.ui.customview.card.weather

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.databinding.ItemWheatherConditionBinding
import com.entexy.gardenguru.utils.bugger
import com.entexy.gardenguru.utils.setDrawable
import com.entexy.gardenguru.utils.setString


class WheatherConditionItem(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding: ItemWheatherConditionBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemWheatherConditionBinding.inflate(inflater, this)
        val a = context.obtainStyledAttributes(attrs, R.styleable.WheatherConditionItem, 0, 0)
        initView(a.getString(R.styleable.WheatherConditionItem_condition))
        a.recycle()
    }

    private fun initView(type: String?) = binding.apply {
        when (type?.toInt()) {
            1 -> {
                image.setDrawable(R.drawable.ic_temperature_new)
                textView1.setString(R.string.temperature)
            }
            2 -> {
                image.setDrawable(R.drawable.ic_watering_new)
                textView1.setString(R.string.watering)
            }
            3 -> {
                image.setDrawable(R.drawable.ic_lighting_new)
                textView1.setString(R.string.lighting)
            }
        }
    }

    fun setSunRelation(data: SunRelation) {
        binding.textView2.text = context.getString(data.id)
    }

    fun changeImage(drawable: Drawable) {
        binding.image.setImageDrawable(drawable)
    }

    fun setTextInfo(info: String) {
        binding.textView2.text = info
    }

}