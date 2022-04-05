package com.example.sensorsurvey

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sensorsurvey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , SensorEventListener {

    private lateinit var binding: ActivityMainBinding

    // Individual light and proximity sensors.
    private var mSensorProximity: Sensor? = null
    private var mSensorLight: Sensor? = null
    //SensorManager
    private var mSensorManager: SensorManager? = null

    private  var sensor_error:String = "error_no_sensor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        mSensorProximity =
            mSensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        mSensorLight =
            mSensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (mSensorLight == null){
            binding.labelLight.text = sensor_error
        }
        if (mSensorProximity == null){
            binding.labelLight.text = sensor_error
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorLight?.also {
            mSensorManager!!.registerListener(this , mSensorLight,
            SensorManager.SENSOR_DELAY_NORMAL)
        }

        mSensorProximity?.also {
            mSensorManager!!.registerListener(this , mSensorProximity,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
//        Both the light and proximity sensors only report one value, in values[0]
        var currentValue = event!!.values[0]

        var sensorType = event!!.sensor.type

        when(sensorType){
            // Event came from the light sensor.
            Sensor.TYPE_LIGHT ->
            {
                binding.labelLight.text = "Light Sensor = $currentValue"
            }
            Sensor.TYPE_PROXIMITY ->
            {
                binding.labelProximity.text = "Proximity Sensor = $currentValue"
            }
        }
    }
}