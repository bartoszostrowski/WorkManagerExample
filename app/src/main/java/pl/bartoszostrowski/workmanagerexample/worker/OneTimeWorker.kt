package pl.bartoszostrowski.workmanagerexample.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class OneTimeWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork() : Start work")

        val time = System.currentTimeMillis()

        // Fake job for 5 seconds
        Thread.sleep(5000)

        Log.d(TAG, "doWork() : Work is done. Took = " + (System.currentTimeMillis() - time) + " ms")

        return Result.success()
    }

    companion object {
        private const val TAG = "OneTimeWorker"
    }
}