package com.esmaeel.escalendarview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.esmaeel.calendarlibrary.DateModel
import com.esmaeel.calendarlibrary.EsCalendarListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EsCalendarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myCalendar.setOnDateSelectedListener(this)

    }

    override fun onDateSelectedListener(model: DateModel?, position: Int) {
        ToastUtils.showShort(model?.apiDate)

    }
}