package com.nurbk.ps.accelerometersensor

import android.app.PendingIntent.getActivity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
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


            val dm = DisplayMetrics()
            this.windowManager.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels
            val height = dm.heightPixels
            val dens = dm.densityDpi

            val hValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, infoXYZ.layoutParams.height.toFloat(),
                resources.displayMetrics
            )

            val wValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, infoXYZ.layoutParams.width.toFloat(),
                resources.displayMetrics
            )



            if ((infoXYZ.y + dens / 2 + hValue + y).toInt() <= height){
                infoXYZ.y =  if ((infoXYZ.y).toInt() >= 0) infoXYZ.y + y else 0.0.toFloat()
                if ((infoXYZ.y).toInt() >= 0)
                    infoXYZ.y = infoXYZ.y + y*2
            }


            if ((infoXYZ.x + wValue + x).toInt() <= width) {
                infoXYZ.x = if ((infoXYZ.x).toInt() >= 0) infoXYZ.x else 0.0.toFloat()
                if ((infoXYZ.x).toInt() >= 0)
                    infoXYZ.x = infoXYZ.x + x*2
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, i: Int) {

    }
}