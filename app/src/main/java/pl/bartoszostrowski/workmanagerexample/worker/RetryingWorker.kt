package pl.bartoszostrowski.workmanagerexample.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class RetryingWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork() : Start work")

        if (runAttemptCount > 3) {
            return Result.failure()
        }

        val time = System.currentTimeMillis()

        // Fake job for 1 seconds
        Thread.sleep(1000)

        Log.d(TAG, "doWork() : Work is done. Took = " + (System.currentTimeMillis() - time) + " ms")

        return Result.retry()
    }

    companion object {
        private const val TAG = "RetryingWorker"
    }
}