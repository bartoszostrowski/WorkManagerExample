package pl.bartoszostrowski.workmanagerexample.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class CompressWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val TAG = "CompressWorker"

    override fun doWork(): Result {
        val input = inputData.getString("value")

        Log.d(TAG, "doWork() : Start work. Input = $input")

        val time = System.currentTimeMillis()

        // Fake job for 10 seconds
        Thread.sleep(10000)

        val output = Data.Builder()
            .putString("value", "$input + COMPRESS")
            .build()

        Log.d(TAG, "doWork() : Work is done. Took = " + (System.currentTimeMillis() - time) + " ms. Output = " + output.getString("value"))

        return Result.success(output)
    }
}