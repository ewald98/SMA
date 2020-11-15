package ro.upt.sma.heart.sensor


import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.content.ContextCompat.getSystemService
import ro.upt.sma.heart.model.HeartSensorRepository
import java.util.*

class HeartSensorDataStore(context: Context) : HeartSensorRepository {

    // ("Get sensor manager service")
    private val sensorManager: SensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager

    // ("Get a handle for the sensor")
    private val heartRateSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    private val listeners = HashMap<HeartSensorRepository.HeartRateListener, SensorEventListener>()

    override fun registerHeartRateListener(listener: HeartSensorRepository.HeartRateListener) {
        if (listeners.containsKey(listener)) {
            return
        }

        val eventListener = object : SensorEventListener {

            var oldValue = 0
            var firstRegister = true

            override fun onSensorChanged(sensorEvent: SensorEvent) {

                if (firstRegister) {
                    oldValue = sensorEvent.values[0].toInt()
                    listener.onValueChanged(oldValue)
                    firstRegister = false
                    return
                }

                if (sensorEvent.values[0].toInt() != oldValue) {
                    oldValue = sensorEvent.values[0].toInt()
                    listener.onValueChanged(oldValue)
                }

            }

            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }

        // ("Register sensor event listener with the sensor manager API")
        sensorManager.registerListener(eventListener, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)

        listeners[listener] = eventListener
    }

    override fun unregisterHeartRateListener(listener: HeartSensorRepository.HeartRateListener) {
        if (listeners.containsKey(listener)) {
            val eventListener = listeners[listener]
            sensorManager.unregisterListener(eventListener)
            listeners.remove(listener)
        }
    }

}
