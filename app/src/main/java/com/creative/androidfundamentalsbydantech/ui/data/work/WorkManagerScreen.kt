package com.creative.androidfundamentalsbydantech.ui.data.work

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.creative.androidfundamentalsbydantech.data.work.SyncWorker
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

@Composable
fun WorkManagerScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val wm = remember { WorkManager.getInstance(context) }

    var latestInfo by remember { mutableStateOf<WorkInfo?>(null) }

    LaunchedEffect(Unit) {
        wm.getWorkInfosForUniqueWorkFlow(SyncWorker.UNIQUE_NAME)
            .map { list -> list.maxByOrNull { it.nextScheduleTimeMillis } }
            .collect { latestInfo = it }
    }

    DemoScaffold(title = "WorkManager", onBack = onBack) {
        SectionHeader("One-time + Periodic (@HiltWorker)")
        Explanation(
            "Enqueue SyncWorker với constraint 'không yêu cầu network' để test offline. " +
                "Progress được worker set qua setProgress và UI observe qua Flow.",
        )

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        val req = OneTimeWorkRequestBuilder<SyncWorker>()
                            .setConstraints(
                                Constraints.Builder()
                                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                                    .build(),
                            )
                            .setInputData(workDataOf(SyncWorker.KEY_STEPS to 5))
                            .build()
                        wm.enqueueUniqueWork(SyncWorker.UNIQUE_NAME, ExistingWorkPolicy.REPLACE, req)
                    }) { Text("One-time") }

                    Button(onClick = {
                        val req = PeriodicWorkRequestBuilder<SyncWorker>(
                            repeatInterval = 15,
                            repeatIntervalTimeUnit = TimeUnit.MINUTES,
                        )
                            .setInputData(workDataOf(SyncWorker.KEY_STEPS to 3))
                            .build()
                        wm.enqueueUniquePeriodicWork(
                            SyncWorker.UNIQUE_NAME,
                            ExistingPeriodicWorkPolicy.UPDATE,
                            req,
                        )
                    }) { Text("Periodic 15m") }

                    OutlinedButton(onClick = {
                        wm.cancelUniqueWork(SyncWorker.UNIQUE_NAME)
                    }) { Text("Cancel") }
                }

                latestInfo?.let { info ->
                    Text("state = ${info.state}", style = MaterialTheme.typography.bodyLarge)
                    val progress = info.progress.getInt(SyncWorker.KEY_PROGRESS, 0)
                    if (info.state == WorkInfo.State.RUNNING) {
                        LinearProgressIndicator(
                            progress = { progress / 100f },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text("$progress%", style = MaterialTheme.typography.bodyLarge)
                    }
                    info.outputData.getString(SyncWorker.KEY_RESULT)?.let { result ->
                        Text(
                            "result: $result",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                if (latestInfo == null) {
                    Text("No work yet", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
