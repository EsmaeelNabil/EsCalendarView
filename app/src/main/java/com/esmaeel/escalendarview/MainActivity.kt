package com.esmaeel.escalendarview

import android.os.Bundle
import android.widget.Toast
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

        getit.setOnClickListener {
            ToastUtils.showShort(myCalendar.selectedCalendar?.apiDate)

        }
    }

    override fun onDateSelectedListener(model: DateModel?, position: Int) {
        Toast.makeText(applicationContext, model?.apiDate, Toast.LENGTH_LONG).show();
    }
}