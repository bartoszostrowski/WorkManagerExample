package pl.bartoszostrowski.workmanagerexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import pl.bartoszostrowski.workmanagerexample.worker.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onOneTimeWorkerClicked(v: View) {
        Log.d(TAG, "onOneTimeWorkerClicked()")

        WorkManager.getInstance(applicationContext).enqueue(OneTimeWorkRequest.from(OneTimeWorker::class.java))
    }

    fun onRecurringWorkerClicked(v: View) {
        Log.d(TAG, "onRecurringWorkerClicked()")

        val recurringRequest = PeriodicWorkRequest.Builder(RecurringWorker::class.java, 15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("recurring", ExistingPeriodicWorkPolicy.REPLACE, recurringRequest)
    }

    fun onRetryingWorkerClicked(v: View) {
        Log.d(TAG, "onRetryingWorkerClicked()")

        val retryingRequest = OneTimeWorkRequest.Builder(RetryingWorker::class.java)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(retryingRequest)
    }

    fun onChainedWorkerClicked(v: View) {
        Log.d(TAG, "onChainedWorkerClicked()")

        val constraintsNetwork = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val constraintsBatteryNotLow = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val constraintsStorageNotLow = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        val blur: OneTimeWorkRequest = OneTimeWorkRequest.Builder(BlurWorker::class.java)
            .setConstraints(constraintsBatteryNotLow)
            .build()

        val noise: OneTimeWorkRequest = OneTimeWorkRequest.Builder(NoiseWorker::class.java)
            .setConstraints(constraintsBatteryNotLow)
            .build()

        val glow: OneTimeWorkRequest = OneTimeWorkRequest.Builder(GlowWorker::class.java)
            .setConstraints(constraintsBatteryNotLow)
            .build()

        val compress: OneTimeWorkRequest = OneTimeWorkRequest.Builder(CompressWorker::class.java)
            .setConstraints(constraintsStorageNotLow)
            .build()

        val upload: OneTimeWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraintsNetwork)
            .build()

        WorkManager.getInstance(applicationContext)
            .beginWith(listOf(blur, noise, glow))
            .then(compress)
            .then(upload)
            .enqueue()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
