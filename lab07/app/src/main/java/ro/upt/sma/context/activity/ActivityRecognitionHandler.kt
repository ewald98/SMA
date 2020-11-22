package ro.upt.sma.context.activity

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient
import kotlin.contracts.contract

class ActivityRecognitionHandler(context: Context) {

    private val client: ActivityRecognitionClient = ActivityRecognition.getClient(context)

    fun registerPendingIntent(): PendingIntent {

        // TODO 5: Create a pending intent for ActivityRecognitionService and register for updates to activity recognition client.
        val pendingIntent = PendingIntent.getService(
                client.applicationContext,
                0,
                Intent(client.applicationContext, ActivityRecognitionService::class.java),
                0
        )
        client.requestActivityUpdates(0, pendingIntent)

        return pendingIntent
    }

    fun unregisterPendingIntent(pendingIntent: PendingIntent) {
        client.removeActivityUpdates(pendingIntent)
    }

}
