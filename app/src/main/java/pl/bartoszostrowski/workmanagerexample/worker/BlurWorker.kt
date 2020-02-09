package pl.bartoszostrowski.workmanagerexample.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        val input = "Image"

        Log.d(TAG, "doWork() : Start work. Input = $input")

        val time = System.currentTimeMillis()

        // Fake job for 1 second
        Thread.sleep(1000)

        val output = Data.Builder()
            .putString("value", "$input + BLUR")
            .build()

        Log.d(TAG, "doWork() : Work is done. Took = " + (System.currentTimeMillis() - time) + " ms. Output = " + output.getString("value"))

        return Result.success(output)
    }

    companion object {
        private const val TAG = "BlurWorker"
    }
}