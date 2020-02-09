package pl.bartoszostrowski.workmanagerexample.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork() : Start work")

        val input = inputData.getString("value")

        val time = System.currentTimeMillis()

        // Fake job for 15 seconds
        Thread.sleep(15000)

        val output = Data.Builder()
            .putString("value", "$input + UPLOAD")
            .build()

        Log.d(TAG, "doWork() : Work is done. Took = " + (System.currentTimeMillis() - time) + " ms. Output = " + output.getString("value"))

        return Result.success(output)
    }

    companion object {
        private const val TAG = "UploadWorker"
    }
}