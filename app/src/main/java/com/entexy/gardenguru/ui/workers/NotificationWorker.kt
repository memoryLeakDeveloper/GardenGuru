package com.entexy.gardenguru.ui.workers

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.notifcations.NotificationsPref
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchUserEventsDataSource
import com.entexy.gardenguru.data.prediction.PredictionRepositoryImpl
import com.entexy.gardenguru.domain.usecases.events.ExistNotCompleteEventForTodayUseCase
import com.entexy.gardenguru.domain.usecases.events.PredictEventsUseCase
import com.entexy.gardenguru.utils.PrefsKeys
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationWorker constructor(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        val notificationsPref = NotificationsPref(applicationContext.getSharedPreferences(PrefsKeys.PREFS, Context.MODE_PRIVATE))

        if (App.firestoreUserPlantRef != null && notificationsPref.get()) {
            val existNotCompleteEventForTodayUseCase = ExistNotCompleteEventForTodayUseCase(
                PredictionRepositoryImpl(
                    PlantCloudDataSource.Base(App.firestoreUserPlantRef!!),
                    FetchUserEventsDataSource.Base()
                ), PredictEventsUseCase()
            )
            if (existNotCompleteEventForTodayUseCase.perform()) {
                createNotification(
                    applicationContext.getString(R.string.notification_title),
                    applicationContext.getString(R.string.notification_text)
                )
            }
        }

        return Result.success()
    }

    private fun createNotification(title: String, text: String) {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        createNotificationChannel(notificationManager)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManagerCompat) {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Efko messaging"
                vibrationPattern = longArrayOf(0, 250, 250, 250)
            }
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val TAG = "notification-worker"
        fun startPeriodic(context: Context) {
            val calendar = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 1)
                set(Calendar.HOUR_OF_DAY, 12)
            }
            val initialDelay = (calendar.timeInMillis - System.currentTimeMillis()) / (1000 * 60)

            val permissionWorker = PeriodicWorkRequest.Builder(
                NotificationWorker::class.java,
                1,
                TimeUnit.DAYS,
                PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
                TimeUnit.MILLISECONDS
            ).setInitialDelay(initialDelay, TimeUnit.MINUTES)
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                permissionWorker.build()
            )
        }

        fun startSingle(context: Application) {
            val permissionWorker = OneTimeWorkRequest.Builder(
                NotificationWorker::class.java
            ).addTag(TAG)
            WorkManager.getInstance(context).enqueue(permissionWorker.build())
        }

        private const val NOTIFICATION_CHANNEL_ID = "garden-guru-def-channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Garden Guru Default"
        private const val NOTIFICATION_ID = 152
    }
}