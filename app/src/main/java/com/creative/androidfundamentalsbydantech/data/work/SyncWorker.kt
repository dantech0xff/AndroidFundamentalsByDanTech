package com.creative.androidfundamentalsbydantech.data.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val target = inputData.getInt(KEY_STEPS, 5).coerceAtLeast(1)
        Log.d(TAG, "Start SyncWorker target=$target")
        try {
            repeat(target) { i ->
                delay(1000)
                setProgress(workDataOf(KEY_PROGRESS to ((i + 1) * 100 / target)))
            }
        } catch (t: Throwable) {
            Log.w(TAG, "SyncWorker failed", t)
            return Result.retry()
        }
        val output: Data = workDataOf(KEY_RESULT to "synced @ ${System.currentTimeMillis()}")
        return Result.success(output)
    }

    companion object {
        private const val TAG = "SyncWorker"
        const val KEY_STEPS = "steps"
        const val KEY_PROGRESS = "progress"
        const val KEY_RESULT = "result"
        const val UNIQUE_NAME = "demo-sync-worker"
    }
}
