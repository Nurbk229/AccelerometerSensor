package com.nurbk.ps.accelerometersensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SensorEventListener {
    private val TAG = "MainActivity"
    private var sensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sensorService = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorList = sensorService.getSensorList(Sensor.TYPE_ALL)

        sensorList.forEach {
            Log.d(TAG, "Name Sensor: ${it.name}")
        }

        if (sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            sensor = sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            print("$TAG is Found ..")

        } else {
            print("$TAG This Sensor is not found")
        }

        sensorService.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if (sensorEvent?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {


            val x = sensorEvent.values[0]
            val y = sensorEvent.values[1]
            val z = sensorEvent.values[2]

            textView.text = "X: $x \n Y: $y \n Z: $z"


            if (z >= 9.8 || z <= -9.8) {
                imageViewX.visibility = View.GONE
                imageViewY.visibility = View.GONE
            } else if (y > 0 && x.toInt() == 0) {
                imageViewY.visibility = View.VISIBLE
                imageViewX.visibility = View.GONE
                imageViewY.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            } else if (y < 0 && x.toInt() == 0) {
                imageViewY.visibility = View.VISIBLE
                imageViewX.visibility = View.GONE
                imageViewY.setImageResource(R.drawable.ic_baseline_arrow_downward)
            } else if (x > 0 && y.toInt() == 0) {
                imageViewX.visibility = View.VISIBLE
                imageViewY.visibility = View.GONE
                imageViewX.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
            } else if (x < 0 && y.toInt() == 0) {
                imageViewX.visibility = View.VISIBLE
                imageViewY.visibility = View.GONE
                imageViewX.setImageResource(R.drawable.ic_baseline_arrow)
            } else if (x > 0 && y > 0) {
                imageViewX.visibility = View.VISIBLE
                imageViewY.visibility = View.VISIBLE
                imageViewX.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
                imageViewY.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            } else if (x < 0 && y > 0) {
                imageViewX.visibility = View.VISIBLE
                imageViewY.visibility = View.VISIBLE
                imageViewX.setImageResource(R.drawable.ic_baseline_arrow)
                imageViewY.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            } else if (y < 0 && x >0 ) {
                imageViewX.visibility = View.VISIBLE
                imageViewY.visibility = View.VISIBLE
                imageViewX.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
                imageViewY.setImageResource(R.drawable.ic_baseline_arrow_downward)
            } else if (y < 0 && x < 0) {
                imageViewX.visibility = View.VISIBLE
                imageViewY.visibility = View.VISIBLE
                imageViewX.setImageResource(R.drawable.ic_baseline_arrow)
                imageViewY.setImageResource(R.drawable.ic_baseline_arrow_downward)
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, i: Int) {

    }
}